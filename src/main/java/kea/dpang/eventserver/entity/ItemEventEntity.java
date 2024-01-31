package kea.dpang.eventserver.entity;

import jakarta.persistence.*;
import kea.dpang.eventserver.client.ItemServiceClient;
import kea.dpang.eventserver.dto.request.RequestItemEventDto;
import kea.dpang.eventserver.dto.response.ResponseItemEventDto;
import kea.dpang.eventserver.dto.response.ResponseItemEventListDto;
import kea.dpang.eventserver.dto.response.ResponseTargetItemDto;
import kea.dpang.eventserver.repository.EventTargetItemRepository;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 이벤트 테이블의 Entity
 * EventEntity와 상속 관계 매핑이 되어 있다.
 */
@Entity
@DiscriminatorValue(Types.ITEM)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ItemEventEntity extends EventEntity {

    /**
     * 이벤트 대상 상픔과의 매핑
     * 상품 이벤트가 삭제되면 연결된 이벤트 대상 상품도 삭제
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventTargetItemEntity> targetItems;

    public EventTargetItemEntity toEventTargetItem(Long itemId) {
        return EventTargetItemEntity.builder()
                .eventId(this)
                .itemId(itemId)
                .build();
    }

    /**
     * 상품 이벤트 수정 메서드
     *
     * @param itemEvent 상품 이벤트의 부모 클래스
     */
    public void updateItemEvent(RequestItemEventDto itemEvent) {
        updateEvent(
                itemEvent.getEventName(),
                itemEvent.getImagePath(),
                itemEvent.getDiscountRate(),
                itemEvent.getStartDate()
                , itemEvent.getEndDate()
        );
    }

    public List<Long> getTargetItemIds() {
        return this.targetItems.stream().map(EventTargetItemEntity::getItem).toList();
    }

    public ResponseItemEventDto toResponseItemEventDto(ItemServiceClient itemServiceClient, EventTargetItemRepository eventTargetItemRepository) {

        List<ResponseTargetItemDto> responseTargetItemDtos = this.targetItems.stream().map(
                        targetItem -> {
                            try {
                                return targetItem.toResponseTargetItemDto(itemServiceClient.getItemName(targetItem.getItem()).getBody().getData());
                            } catch (ResponseStatusException e) {
                                if (e.getMessage().contains("상품을 찾을 수 없음"))
                                    eventTargetItemRepository.deleteById(targetItem.getId());
                                return null;
                            }
                        })
                .toList();


        return ResponseItemEventDto.builder()
                .id(this.getId())
                .eventName(this.getEventName())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .imagePath(this.getImagePath())
                .discountRate(this.getDiscountRate())
                .eventStatus(this.getEventStatus())
                .targetItems(responseTargetItemDtos)
                .build();
    }

    public ResponseItemEventListDto toResponseItemEventListDto() {
        return ResponseItemEventListDto.builder()
                .id(this.getId())
                .eventName(this.getEventName())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .imagePath(this.getImagePath())
                .discountRate(this.getDiscountRate())
                .eventStatus(this.getEventStatus())
                .targetItems(this.getTargetItemIds())
                .build();
    }
}
