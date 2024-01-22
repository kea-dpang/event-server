package kea.dpang.eventserver.dto.request;

import kea.dpang.eventserver.entity.EventTargetItem;
import kea.dpang.eventserver.entity.ItemEventEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
public class ItemEventCreateDto extends BasicCreateDto{
    private List<EventTargetItem> item_id;
}
