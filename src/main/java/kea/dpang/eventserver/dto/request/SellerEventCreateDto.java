package kea.dpang.eventserver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class SellerEventCreateDto extends BasicCreateDto{
    private Long seller_id;
}
