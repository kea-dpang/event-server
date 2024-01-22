package kea.dpang.eventserver.service;

import kea.dpang.eventserver.dto.request.ItemEventCreateDto;
import kea.dpang.eventserver.dto.request.SellerEventCreateDto;
import kea.dpang.eventserver.dto.response.AllEventGetDto;
import kea.dpang.eventserver.dto.response.ItemEventGetDto;
import kea.dpang.eventserver.dto.response.SellerEventGetDto;
import kea.dpang.eventserver.entity.EventEntity;
import kea.dpang.eventserver.entity.ItemEventEntity;
import kea.dpang.eventserver.entity.SellerEventEntity;
import kea.dpang.eventserver.repository.EventRepository;
import kea.dpang.eventserver.repository.EventTargetItemRepository;
import kea.dpang.eventserver.repository.ItemEventRepository;
import kea.dpang.eventserver.repository.SellerEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final EventTargetItemRepository eventTargetItemRepository;
    private final ItemEventRepository itemEventRepository;
    private final SellerEventRepository sellerEventRepository;

    @Override
    public List<ItemEventGetDto> getItemEvents() {
        List<ItemEventEntity> itemEventEntities = itemEventRepository.findAll();
        List<ItemEventGetDto> itemEventList = new ArrayList<>();
        for(ItemEventEntity itemEventEntity:itemEventEntities){
            itemEventList.add(ItemEventGetDto
                    .builder()
                            .id(itemEventEntity.getId())
                            .event_name(itemEventEntity.getEvent_name())
                            .event_status(itemEventEntity.getEvent_status())
                            .start_date(itemEventEntity.getStart_date())
                            .end_date(itemEventEntity.getEnd_date())
                            .image_path(itemEventEntity.getImage_path())
                            .build());
        }
        return itemEventList;
    }

    @Override
    public List<SellerEventGetDto> getSellerEvents() {
        List<SellerEventEntity> sellerEventEntities = sellerEventRepository.findAll();
        List<SellerEventGetDto> sellerEventList = new ArrayList<>();
        for(SellerEventEntity sellerEventEntity:sellerEventEntities){
            sellerEventList.add(SellerEventGetDto
                    .builder()
                    .id(sellerEventEntity.getId())
                    .event_name(sellerEventEntity.getEvent_name())
                    .event_status(sellerEventEntity.getEvent_status())
                    .start_date(sellerEventEntity.getStart_date())
                    .end_date(sellerEventEntity.getEnd_date())
                    .image_path(sellerEventEntity.getImage_path())
                    .seller_id(sellerEventEntity.getSeller_id())
                    .build());
        }
        return sellerEventList;
    }

    @Override
    public List<AllEventGetDto> getAllEvent() {
        List<EventEntity> eventEntities = eventRepository.findAll();
        List<AllEventGetDto> allEventList = new ArrayList<>();
        for(EventEntity eventEntity:eventEntities){
            allEventList.add(AllEventGetDto.builder()
                            .id(eventEntity.getId())
                            .event_status(eventEntity.getEvent_status())
                            .event_name(eventEntity.getEvent_name())
                            .registration_date(eventEntity.getRegistration_date())
                            .start_date(eventEntity.getStart_date())
                            .end_date(eventEntity.getEnd_date())
                    //type 추가 필요
                    .build());
        }
        return allEventList;
    }

    @Override
    public ItemEventCreateDto getItemEventDetail(Long id) {
        ItemEventEntity itemEvent = itemEventRepository.findById(id).get();
        return ItemEventCreateDto.builder()
                .event_name(itemEvent.getEvent_name())
                .image_path(itemEvent.getImage_path())
                .start_date(itemEvent.getStart_date())
                .end_date(itemEvent.getEnd_date())
                .discount_rate(itemEvent.getDiscount_rate())
                .item_id(itemEvent.getItem_id())
                .build();
    }

    @Override
    public SellerEventCreateDto getSellerEventDetail(Long id) {
        SellerEventEntity sellerEvent = sellerEventRepository.findById(id).get();
        return SellerEventCreateDto.builder()
                .event_name(sellerEvent.getEvent_name())
                .image_path(sellerEvent.getImage_path())
                .start_date(sellerEvent.getStart_date())
                .end_date(sellerEvent.getEnd_date())
                .discount_rate(sellerEvent.getDiscount_rate())
                .seller_id(sellerEvent.getSeller_id())
                .build();
    }

    @Override
    public void createItemEvent(ItemEventCreateDto itemEvent) {
        itemEventRepository.save(ItemEventEntity.builder()
                        .event_name(itemEvent.getEvent_name())
                        .discount_rate(itemEvent.getDiscount_rate())
                        .image_path(itemEvent.getImage_path())
                        .start_date(itemEvent.getStart_date())
                        .end_date(itemEvent.getEnd_date())
                .build());
    }

    @Override
    public void createSellerEvent(SellerEventCreateDto sellerEvent) {
        sellerEventRepository.save(SellerEventEntity.builder()
                .event_name(sellerEvent.getEvent_name())
                .discount_rate(sellerEvent.getDiscount_rate())
                .image_path(sellerEvent.getImage_path())
                .start_date(sellerEvent.getStart_date())
                .end_date(sellerEvent.getEnd_date())
                .build());
    }

    @Override
    public void updateItemEvent(Long id, ItemEventCreateDto itemEvent) {
        ItemEventEntity targetItemEvent = itemEventRepository.findById(id).get();
        targetItemEvent.updateItemEvent(
                itemEvent.getEvent_name(),
                itemEvent.getImage_path(),
                null,
                itemEvent.getDiscount_rate(),
                itemEvent.getStart_date(),
                itemEvent.getEnd_date()
        );
    }

    @Override
    public void updateSellerEvent(Long id, SellerEventCreateDto sellerEvent) {
        SellerEventEntity targetSellerEvent = sellerEventRepository.findById(id).get();
        targetSellerEvent.updateSellerEvent(
                sellerEvent.getEvent_name(),
                sellerEvent.getImage_path(),
                null,
                sellerEvent.getDiscount_rate(),
                sellerEvent.getStart_date(),
                sellerEvent.getEnd_date(),
                sellerEvent.getSeller_id()
        );
    }

    @Override
    public void deleteEvent(List<Long> ids) {
        eventRepository.deleteAllById(ids);
    }
}
