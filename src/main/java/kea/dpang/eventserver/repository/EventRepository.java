package kea.dpang.eventserver.repository;


import kea.dpang.eventserver.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EventRepository extends JpaRepository<EventEntity,Long>{ }
