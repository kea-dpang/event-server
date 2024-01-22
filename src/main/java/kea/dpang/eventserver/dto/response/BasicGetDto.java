package kea.dpang.eventserver.dto.response;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
public class BasicGetDto {
    //이벤트 ID
    private Long id;
    //이벤트 이름
    private String event_name;
    //이벤트 상태
    private String event_status;
    //이벤트 시작 날짜
    private LocalDate start_date;
    //이벤트 종료 날짜
    private LocalDate end_date;
}
