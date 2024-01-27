package kea.dpang.eventserver.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.eventserver.dto.TargetItemDto;
import kea.dpang.eventserver.entity.EventTargetItemEntity;
import kea.dpang.eventserver.entity.ItemEventEntity;
import kea.dpang.eventserver.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 이벤트 DTO
 * @author Tomas
 */
@Builder
@Getter
public class RequestItemEventDto {
    /**
     * 상품 이벤트 이름
     */
    @Schema(example = "이벤트 이름")
    private String eventName;

    /**
     * 상품 이벤트 시작 날짜
     */
    @Schema()
    private LocalDate startDate;

    /**
     * 상품 이벤트 종료 날짜
     */
    private LocalDate endDate;

    /**
     * 상품 이벤트 이미지 경로
     */
    @Schema(example = "상품 이미지 경로")
    private String imagePath;

    /**
     * 상품 이벤트 할인율
     */
    @Schema(example = "20")
    private double discountRate;

    /**
     * 상품 이벤트 대상 상품 목록
     */
    private List<TargetItemDto> targetItems;

    /**
     * 상품 이벤트 객체를 build 하는 메서드
     * @return build한 ItemEventEntity
     */
    public ItemEventEntity toItemEventEntity(){
        return ItemEventEntity.builder()
                .eventName(this.eventName)
                .discountRate(this.discountRate)
                .imagePath(this.imagePath)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }

    /**
     * 상품 이벤트 대상 상품을 build 하는 메서드
     * @param itemEvent 이벤트 대상 상품들과 연결할 상품 이벤트 객체
     * @return EventTargetItemEntity 리스트
     */
    public List<EventTargetItemEntity> toEventTargetItems(ItemEventEntity itemEvent) {
        return this.targetItems.stream()
                .map(targetItem -> targetItem.toEventTargetItem(itemEvent))
                .collect(Collectors.toList());
    }

}
