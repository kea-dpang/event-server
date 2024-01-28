package kea.dpang.eventserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.eventserver.entity.SellerEventEntity;
import kea.dpang.eventserver.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 판매처 이벤트 DTO
 * @author Tomas
 */
@Builder
@Getter
public class SellerEventDto {
    /**
     * 판매처 이벤트 할인율
     */
    @Schema(example = "20")
    private double discountRate;

    /**
     * 판매처 이벤트 ID
     */
    @Schema(example = "이벤트 ID")
    private Long id;

    /**
     * 판매처 이벤트 이름
     */
    @Schema(example = "이벤트 이름")
    private String eventName;

    /**
     * 판매처 이벤트 상태
     */
    @Schema(nullable = true)
    private Status eventStatus;

    /**
     * 판매처 이벤트 시작 날짜
     */
    private LocalDate startDate;

    /**
     * 판매처 이벤트 종료 날짜
     */
    private LocalDate endDate;

    /**
     * 판매처 이벤트 사진 경로
     */
    @Schema(example = "이벤트 사진 경로")
    private String imagePath;

    /**
     * 판매처 이벤트 대상 판매처 ID
     */
    @Schema(example = "이벤트 대상 판매처 ID")
    private Long sellerId;

    /**
     * 판매처 이벤트 객체 build 하는 메서드
     * @return build한 SellerEventEntity
     */
    public SellerEventEntity toSellerEventEntity(){
        return SellerEventEntity.builder()
                .eventName(this.eventName)
                .discountRate(this.discountRate)
                .imagePath(this.imagePath)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .sellerId(this.sellerId)
                .build();
    }
}
