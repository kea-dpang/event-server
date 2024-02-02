package kea.dpang.eventserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.eventserver.entity.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 이벤트 DTO
 * @author Tomas
 */
@Builder
@Getter
public class EventDto {
    /**
     * 이벤트 ID
     */
    @Schema(example = "이벤트 ID")
    private Long id;

    /**
     * 이벤트 이름
     */
    @Schema(example = "이벤트 이름")
    private String eventName;

    /**
     * 이벤트 상태
     */
    private Status eventStatus;

    /**
     * 이벤트 시작 날짜
     */
    private LocalDate startDate;

    /**
     * 이벤트 종료 날짜
     */
    private LocalDate endDate;

    /**
     * 이벤트 생성 날짜
     */
    private LocalDateTime registrationDate;

    /**
     * 이벤트 이미지 경로
     */
    private String imagePath;

    /**
     * 이벤트 타입
     */
    @Schema(example = "이벤트 타입")
    private String type;
}