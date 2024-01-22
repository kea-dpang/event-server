package kea.dpang.eventserver.repository;

import kea.dpang.eventserver.entity.ItemEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEventRepository extends JpaRepository<ItemEventEntity, Long> { }
