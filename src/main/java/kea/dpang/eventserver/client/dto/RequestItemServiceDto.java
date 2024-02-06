package kea.dpang.eventserver.client.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RequestItemServiceDto {
    private List<Long> itemIds;
    private Long sellerId;
    private Long eventId;
    private int discountRate;
}