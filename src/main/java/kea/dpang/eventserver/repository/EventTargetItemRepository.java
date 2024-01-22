package kea.dpang.eventserver.repository;

import kea.dpang.eventserver.entity.EventTargetItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTargetItemRepository extends JpaRepository<String, EventTargetItem> {
}
