package com.ead.eadnotificationhex.adapters.outbound.persistence;

import com.ead.eadnotificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.eadnotificationhex.core.domain.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationJPARepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findAllByUserIdAndStatus(UUID userId, NotificationStatus status, Pageable pageable);

    Optional<NotificationEntity> findByIdAndUserId(UUID uuid, UUID userId);
}
