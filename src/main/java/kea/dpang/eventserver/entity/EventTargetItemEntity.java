package kea.dpang.eventserver.entity;

import jakarta.persistence.*;
import kea.dpang.eventserver.dto.request.RequestTargetItemDto;
import kea.dpang.eventserver.dto.response.ResponseTargetItemDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 대상 상품 테이블의 Entity
 * 상품 이벤트의 대상이 되는 상품과 이벤트를 저장한다.
 * @author Tomas
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "event_target_item")
public class EventTargetItemEntity {

    /**
     * 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이벤트 ID 외래키
     * 상품이 등록된 이벤트의 ID
     */
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private ItemEventEntity event;

    /**
     * 상품 ID 외래키
     */
    @Column(name = "item_id")
    private Long item;


    /**
     * 이벤트 대상 상품 변경
     * @param item_id 이벤트 대상 상품의 ID
     */
    public void update(Long item_id){
        this.item = item_id;
    }

    @Builder
    public EventTargetItemEntity(ItemEventEntity eventId, Long itemId) {
        this.event = eventId;
        this.item = itemId;
    }

    public RequestTargetItemDto toTargetItemDto(){
        return RequestTargetItemDto.builder()
                .itemId(this.item)
                .build();
    }

    public ResponseTargetItemDto toResponseTargetItemDto(String name){
        return ResponseTargetItemDto.builder()
                .itemName(name)
                .itemId(this.item)
                .build();
    }
}