package kea.dpang.eventserver.repository;

import kea.dpang.eventserver.entity.EventTargetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventTargetItemRepository extends JpaRepository<EventTargetItem,Long> {
}
