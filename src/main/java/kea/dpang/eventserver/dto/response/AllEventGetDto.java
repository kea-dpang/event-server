package kea.dpang.eventserver.dto.response;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
public class AllEventGetDto extends BasicGetDto{
    private LocalDateTime registration_date;
    private String type;
}
