package kea.dpang.eventserver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
public class BasicCreateDto {
    private String event_name;
    private LocalDate start_date;
    private LocalDate end_date;
    private String image_path;
    private double discount_rate;
}
