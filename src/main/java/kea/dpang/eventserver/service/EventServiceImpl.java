package kea.dpang.eventserver.service;

import kea.dpang.eventserver.dto.ItemEventDto;
import kea.dpang.eventserver.dto.SellerEventDto;
import kea.dpang.eventserver.dto.EventDto;
import kea.dpang.eventserver.dto.TargetItemDto;
import kea.dpang.eventserver.dto.request.RequestItemEventDto;
import kea.dpang.eventserver.dto.request.RequestSellerEventDto;
import kea.dpang.eventserver.entity.*;
import kea.dpang.eventserver.exception.EventNotFoundException;
import kea.dpang.eventserver.exception.EventTargetItemNotFoundException;
import kea.dpang.eventserver.repository.EventRepository;
import kea.dpang.eventserver.repository.EventTargetItemRepository;
import kea.dpang.eventserver.repository.ItemEventRepository;
import kea.dpang.eventserver.repository.SellerEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final EventTargetItemRepository eventTargetItemRepository;
    private final ItemEventRepository itemEventRepository;
    private final SellerEventRepository sellerEventRepository;

    // 현재 날짜과 비교해 이벤트의 상태를 업데이트한다.
    public void updateEventStatus(){
        LocalDate current = LocalDate.now();
        eventRepository.findAll().forEach(event->{
            if(current.isBefore(event.getStartDate())){
                event.updateStatus(Status.WAITING);
            }
            else if(current.isAfter(event.getStartDate()) || current.isBefore(event.getEndDate())){
                event.updateStatus(Status.PROCEEDING);
            }
            else {
                event.updateStatus(Status.END);
            }
        });
    }

    @Override
    public Page<ItemEventDto> getItemEventList(Pageable pageable){
        Page<ItemEventEntity> eventPage = itemEventRepository.findAll(pageable);

        return eventPage.map(ItemEventEntity::toItemEventDto);
    }

    @Override
    public Page<SellerEventDto> getSellerEventList (Pageable pageable){
        Page<SellerEventEntity> eventPage = sellerEventRepository.findAll(pageable);

        return eventPage.map(SellerEventEntity::toSellerEventDto);
    }

    @Override
    public Page<EventDto> getEventList(Pageable pageable) {
        Page<EventEntity> eventEntities = eventRepository.findAll(pageable);
        return eventEntities.map(EventEntity::toEventDto);
    }

    @Override
    public ItemEventDto getItemEvent(Long id) {
        ItemEventEntity itemEvent = itemEventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return itemEvent.toItemEventDto();
    }

    @Override
    public SellerEventDto getSellerEvent(Long id) {
        SellerEventEntity sellerEvent = sellerEventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return sellerEvent.toSellerEventDto();
    }

    @Override
    public void createItemEvent(RequestItemEventDto itemEvent) {
        ItemEventEntity itemEventEntity = itemEvent.toItemEventEntity();
        itemEventRepository.save(itemEventEntity);
        eventTargetItemRepository.saveAll(itemEvent.toEventTargetItems(itemEventEntity));
        updateEventStatus();
    }

    @Override
    public void createSellerEvent(RequestSellerEventDto sellerEvent) {
        sellerEventRepository.save(sellerEvent.toSellerEventEntity());
        updateEventStatus();
    }

    @Override
    public void updateItemEvent(Long id, ItemEventDto itemEvent) {
        ItemEventEntity itemEventEntity = itemEventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        itemEventEntity.updateItemEvent(itemEvent);

        itemEvent.getTargetItems().stream()
                .map(TargetItemDto::getItemId)
                .forEach(itemId -> {
                    eventTargetItemRepository.findEventTargetItemByItemAndEvent(itemId, itemEventEntity)
                            .orElseThrow(() -> new EventTargetItemNotFoundException(itemId))
                            .update(itemId);
                });
        updateEventStatus();
    }

    @Override
    public void updateSellerEvent(Long id, SellerEventDto sellerEvent) {
        SellerEventEntity targetSellerEvent = sellerEventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        targetSellerEvent.updateSellerEvent(sellerEvent);
        updateEventStatus();
    }

    @Override
    public void deleteEvent(List<Long> ids) {
        // 요청받은 ID 리스트를 순회하면서 각각의 Event를 삭제한다.
        for (Long id:ids){

            // 데이터베이스에서 ID에 해당하는 Evnet 객체를 조회한다.
            EventEntity event = eventRepository.findById(id)
                    .orElseThrow(() -> new EventNotFoundException(id));

            // 조회한 Event를 데이터베이스에서 삭제한다.
            eventRepository.delete(event);
        }
    }

}
