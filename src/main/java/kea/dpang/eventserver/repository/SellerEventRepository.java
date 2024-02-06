package kea.dpang.eventserver.repository;

import kea.dpang.eventserver.entity.SellerEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerEventRepository extends JpaRepository<SellerEventEntity, Long> {
    SellerEventEntity findBySellerId(Long sellerId);
}
