package kea.dpang.eventserver.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class DeleteEventBySellersDto {
    private List<Long> sellerIds;
}
