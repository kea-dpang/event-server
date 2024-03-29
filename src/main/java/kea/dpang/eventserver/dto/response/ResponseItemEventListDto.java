package kea.dpang.eventserver.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.eventserver.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class ResponseItemEventListDto {
    /**
     * 이벤트 ID
     */
    @Schema(example = "이벤트 ID")
    private Long id;

    /**
     * 상품 이벤트 이름
     */
    @Schema(example = "이벤트 이름")
    private String eventName;

    /**
     * 상품 이벤트 시작 날짜
     */
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
    private List<Long> targetItems;

    /**
     * 상품 이벤트 상태
     */
    private Status eventStatus;
}
