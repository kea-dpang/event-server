package kea.dpang.eventserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.eventserver.entity.EventTargetItemEntity;
import kea.dpang.eventserver.entity.ItemEventEntity;
import lombok.Builder;
import lombok.Getter;

/**
 * 상품 이벤트 대상 상품 DTO
 * @author Tomas
 */
@Getter
@Builder
public class TargetItemDto {
    /**
     * 식별자
     */
    private Long id;

    /**
     * 해당 상품을 대상으로 하는 상품 이벤트의 ID
     */
    private Long eventId;

    /**
     * 상품 이벤트 대상 상품 ID
     */
    private Long itemId;

    /**
     * 이벤트 대상 상품 객체를 build 하는 메서드
     * @param itemEvent 해당 상품을 대상으로 하는 상품 이벤트 객체
     * @return build한 EventTargetItemEntity
     */
    public EventTargetItemEntity toEventTargetItem(ItemEventEntity itemEvent){
        return EventTargetItemEntity.builder()
                .eventId(itemEvent)
                .itemId(this.itemId)
                .build();
    }
}
