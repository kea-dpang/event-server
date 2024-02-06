package kea.dpang.eventserver.service;

import kea.dpang.eventserver.base.SuccessResponse;
import kea.dpang.eventserver.client.ItemServiceClient;
import kea.dpang.eventserver.client.SellerSerivceClient;
import kea.dpang.eventserver.client.dto.RequestItemServiceDto;
import kea.dpang.eventserver.dto.SellerEventDto;
import kea.dpang.eventserver.dto.EventDto;
import kea.dpang.eventserver.dto.request.RequestItemEventDto;
import kea.dpang.eventserver.dto.request.RequestSellerEventDto;
import kea.dpang.eventserver.dto.response.ResponseItemEventDto;
import kea.dpang.eventserver.dto.response.ResponseItemEventListDto;
import kea.dpang.eventserver.dto.response.ResponseSellerEventDto;
import kea.dpang.eventserver.entity.*;
import kea.dpang.eventserver.exception.EventNotFoundException;
import kea.dpang.eventserver.repository.EventRepository;
import kea.dpang.eventserver.repository.EventTargetItemRepository;
import kea.dpang.eventserver.repository.ItemEventRepository;
import kea.dpang.eventserver.repository.SellerEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final ItemServiceClient itemServiceClient;
    private final SellerSerivceClient sellerSerivceClient;

    private final EventRepository eventRepository;
    private final EventTargetItemRepository eventTargetItemRepository;
    private final ItemEventRepository itemEventRepository;
    private final SellerEventRepository sellerEventRepository;

    // 현재 날짜과 비교해 이벤트의 상태를 업데이트한다.
    public void updateEventStatus() {
        LocalDate current = LocalDate.now();
        eventRepository.findAll().forEach(event -> {
            if (current.isBefore(event.getStartDate())) {
                event.updateStatus(Status.WAITING);
                itemServiceClient.deleteEvent(event.getId());
            } else if ((current.isEqual(event.getStartDate()) || current.isAfter(event.getStartDate()))
                    && current.isBefore(event.getEndDate())) {
                event.updateStatus(Status.PROCEEDING);
            } else {
                event.updateStatus(Status.END);
                itemServiceClient.deleteEvent(event.getId());
            }
        });
    }

    @Override
    public Page<ResponseItemEventListDto> getItemEventList(Pageable pageable) {

        Page<ItemEventEntity> eventPage = itemEventRepository.findAll(pageable);
        log.info("상품 이벤트 목록 조회 완료: {}", eventPage);

        Page<ResponseItemEventListDto> responseDto = eventPage.map(ItemEventEntity::toResponseItemEventListDto);
        log.info("상품 이벤트 response DTO 빌드 완료: {}", responseDto);

        return responseDto;
    }

    @Override
    public Page<SellerEventDto> getSellerEventList(Pageable pageable) {

        Page<SellerEventEntity> eventPage = sellerEventRepository.findAll(pageable);
        log.info("판매처 이벤트 목록 조회 완료: {}", eventPage);

        Page<SellerEventDto> responseDto = eventPage.map(SellerEventEntity::toSellerEventDto);
        log.info("판매처 이벤트 response DTO 빌드 완료: {}", responseDto);

        return responseDto;
    }

    @Override
    public Page<EventDto> getEventList(Pageable pageable) {

        Page<EventEntity> eventPage = eventRepository.findAll(pageable);
        log.info("전체 이벤트 목록 조회 완료: {}", eventPage);

        Page<EventDto> responseDto = eventPage.map(EventEntity::toEventDto);
        log.info("전체 이벤트 response DTO 빌드 완료: {}", responseDto);

        return responseDto;
    }

    @Override
    public ResponseItemEventDto getItemEvent(Long id) {

        log.info("{}번 상품 이벤트 조회 시작", id);
        ItemEventEntity itemEvent = itemEventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{}번 상품 이벤트가 없습니다.", id);
                    return new EventNotFoundException(id);
                });
        log.info("{}번 상품 이벤트 조회 완료: {}", id, itemEvent);

        ResponseItemEventDto responseDto = itemEvent.toResponseItemEventDto(itemServiceClient, eventTargetItemRepository);
        log.info("{}번 상품 이벤트 responseDto 빌드 완료: {}", id, responseDto);

        return responseDto;
    }

    @Override
    public ResponseSellerEventDto getSellerEvent(Long id) {

        log.info("{}번 판매처 이벤트 조회 시작", id);
        SellerEventEntity sellerEvent = sellerEventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{}번 판매처 이벤트가 없습니다.", id);
                    return new EventNotFoundException(id);
                });
        log.info("{}번 판매처 이벤트 조회 완료: {}", id, sellerEvent);

        try {
            Long sellerId = sellerEvent.getSellerId();

            ResponseEntity<SuccessResponse<String>> sellerName = sellerSerivceClient.getSellerName(sellerId);
            log.info("{}번 판매처 정보 조회 완료: {}", sellerId, sellerName);

            ResponseSellerEventDto responseDto = sellerEvent.toResponseSellerEventDto(sellerName.getBody().getData());
            log.info("{}번 판매처 이벤트 responseDto 빌드 완료: {}", id, responseDto);

            return responseDto;
        } catch (Exception e) {
            if (e.getMessage().contains("해당 ID를 가진 판매처가 존재하지 않습니다.")) {
                log.error("판매처 서버로부터 판매처 정보를 가져오지 못했습니다.");
                sellerEventRepository.deleteById(sellerEvent.getId());
            }
            throw e;
        }
    }

    @Override
    public List<Long> getSellerEventsBySeller (List<Long> sellerIds){
        return sellerIds.stream()
                .map(sellerId->sellerEventRepository.findBySellerId(sellerId).getId())
                .collect(Collectors.toList());
    }

    @Override
    public void createItemEvent(RequestItemEventDto itemEvent) {

        log.info("===상품 이벤트 생성 시작===");

        ItemEventEntity itemEventEntity = itemEvent.toItemEventEntity();
        log.info("상품 이벤트 객체 생성 완료: {}", itemEventEntity);

        ItemEventEntity save = itemEventRepository.save(itemEventEntity);
        log.info("상품 이벤트 객체 저장 완료");

        List<EventTargetItemEntity> eventTargetItems = itemEvent.toEventTargetItems(itemEventEntity);
        log.info("상품 이벤트 객체에서 상품 목록 추출 완료: {}", eventTargetItems);

        List<EventTargetItemEntity> eventTargetItemEntities = eventTargetItemRepository.saveAll(eventTargetItems);
        log.info("상품 이벤트 대상 상품 목록 객체 저장 완료");

        itemServiceClient.registerItemsToEvent(RequestItemServiceDto.builder()
                        .itemIds(itemEvent.getTargetItems())
                        .eventId(save.getId())
                        .discountRate((int) itemEvent.getDiscountRate())
                        .build());

        updateEventStatus();
        log.info("전체 이벤트 상태 동기화: 현재시간({})", LocalDateTime.now());

        log.info("===상품 이벤트 생성 종료===");

    }

    @Override
    public void createSellerEvent(RequestSellerEventDto sellerEvent) {
        log.info("===판매처 이벤트 생성 시작===");
        SellerEventEntity sellerEventEntity = sellerEvent.toSellerEventEntity();
        log.info("판매처 이벤트 객체 생성 완료: {}", sellerEventEntity);
        SellerEventEntity save = sellerEventRepository.save(sellerEventEntity);
        log.info("판매처 이벤트 객체 저장 완료");

        itemServiceClient.registerItemsToEvent(RequestItemServiceDto.builder()
                .sellerId(sellerEvent.getSellerId())
                .eventId(save.getId())
                .discountRate((int) sellerEvent.getDiscountRate())
                .build());

        updateEventStatus();
        log.info("전체 이벤트 상태 동기화: 현재시간({})", LocalDateTime.now());
        log.info("===판매처 이벤트 생성 종료===");
    }

    @Override
    public void updateItemEvent(Long id, RequestItemEventDto itemEvent) {

        log.info("===상품 이벤트 수정 시작===");

        ItemEventEntity itemEventEntity = itemEventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{}번 상품 이벤트가 없습니다.", id);
                    return new EventNotFoundException(id);
                });
        log.info("{}번 상품 이벤트 객체 검색 완료", id);

        itemEventEntity.updateItemEvent(itemEvent);
        log.info("{}번 상품 이벤트 객체 수정 완료", id);

        List<EventTargetItemEntity> eventTargetItems = itemEvent.getTargetItems().stream()
                .map(itemEventEntity::toEventTargetItem)
                .collect(Collectors.toList());
        log.info("{}번 상품 이벤트 대상 상품 목록 추출 완료", id);

        eventTargetItemRepository.deleteAllByEvent(itemEventEntity);
        eventTargetItemRepository.saveAll(eventTargetItems);
        log.info("{}번 상품 이벤트 대상 상품 수정 완료", id);

        updateEventStatus();
        log.info("전체 이벤트 상태 동기화: 현재시간({})", LocalDateTime.now());

        RequestItemServiceDto requestItemServiceDto = RequestItemServiceDto.builder()
                .eventId(id)
                .itemIds(itemEvent.getTargetItems())
                .discountRate((int)itemEvent.getDiscountRate())
                .build();
        itemServiceClient.registerItemsToEvent(requestItemServiceDto);
        log.info("{}번 상품 이벤트 대상 상품들을 상품 서버에 전송 완료",id);

        log.info("===상품 이벤트 수정 완료===");
    }

    @Override
    public void updateSellerEvent(Long id, SellerEventDto sellerEvent) {

        log.info("===판매처 이벤트 수정 시작===");

        SellerEventEntity targetSellerEvent = sellerEventRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("{}번 판매처 이벤트가 없습니다.", id);
                    return new EventNotFoundException(id);
                });
        log.info("{}번 판매처 이벤트 검색 완료", id);

        targetSellerEvent.updateSellerEvent(sellerEvent);
        log.info("{}번 판매처 이벤트 수정 완료", id);

        updateEventStatus();
        log.info("전체 이벤트 상태 동기화: 현재시간({})", LocalDateTime.now());

        RequestItemServiceDto requestItemServiceDto = RequestItemServiceDto.builder()
                .eventId(id)
                .sellerId(sellerEvent.getSellerId())
                .discountRate((int)sellerEvent.getDiscountRate())
                .build();
        itemServiceClient.registerItemsToEvent(requestItemServiceDto);
        log.info("{}번 판매처 이벤트 대상 판매처를 상품 서버에 전송",id);

        log.info("===판매처 이벤트 수정 시작===");

    }

    @Override
    public void deleteEvent(List<Long> ids) {

        log.info("===이벤트 삭제 시작===");

        // 요청받은 ID 리스트를 순회하면서 각각의 Event를 삭제한다.
        for (Long id : ids) {

            // 데이터베이스에서 ID에 해당하는 Evnet 객체를 조회한다.
            EventEntity event = eventRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("{}번 이벤트가 없습니다.", id);
                        return new EventNotFoundException(id);
                    });
            log.info("{}번 이벤트 검색 완료", id);

            // 조회한 Event를 데이터베이스에서 삭제한다.
            eventRepository.delete(event);
            log.info("{}번 이벤트 삭제 완료", id);

            itemServiceClient.deleteEvent(id);
            log.info("{}번 이벤트 삭제 정보를 상품 서버에 전송",id);
        }

        log.info("===이벤트 삭제 완료===");
    }

}
