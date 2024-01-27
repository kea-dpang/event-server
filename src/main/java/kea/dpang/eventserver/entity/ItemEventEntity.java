package kea.dpang.eventserver.entity;

import jakarta.persistence.*;
import kea.dpang.eventserver.dto.ItemEventDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 상품 이벤트 테이블의 Entity
 * EventEntity와 상속 관계 매핑이 되어 있다.
 */
@Entity
@DiscriminatorValue(Types.ITEM)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ItemEventEntity extends EventEntity{

    /**
     * 이벤트 대상 상픔과의 매핑
     * 상품 이벤트가 삭제되면 연결된 이벤트 대상 상품도 삭제
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventTargetItemEntity> targetItems;

    /**
     * 상품 이벤트 DTO를 build 하는 메서드
     * @return build된 상품 이벤트 DTO
     */
    public ItemEventDto toItemEventDto(){
        return ItemEventDto.builder()
                .id(this.getId())
                .eventName(this.getEventName())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .imagePath(this.getImagePath())
                .discountRate(this.getDiscountRate())
                .eventStatus(this.getEventStatus())
                .build();
    }

    /**
     * 상품 이벤트 수정 메서드
     * @param itemEvent 상품 이벤트의 부모 클래스
     */
    public void updateItemEvent(ItemEventDto itemEvent) {
        updateEvent(
                itemEvent.getEventName(),
                itemEvent.getImagePath(),
                itemEvent.getDiscountRate(),
                itemEvent.getStartDate()
                ,itemEvent.getEndDate()
        );
    }
}
