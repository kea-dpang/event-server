package kea.dpang.eventserver.repository;

import kea.dpang.eventserver.entity.EventTargetItemEntity;
import kea.dpang.eventserver.entity.ItemEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventTargetItemRepository extends JpaRepository<EventTargetItemEntity,Long> {
    Optional<EventTargetItemEntity> findEventTargetItemByItemAndEvent (Long itemId, ItemEventEntity itemEvent);
}
