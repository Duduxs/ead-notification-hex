package com.ead.eadnotificationhex.core.ports;

import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.NotificationStatus;
import com.ead.eadnotificationhex.core.domain.PageInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {

    NotificationDomain saveNotification(NotificationDomain notificationDomain);

    Collection<NotificationDomain> findAllByUserIdAndStatus(UUID userId, NotificationStatus status, PageInfo pageable);

    Optional<NotificationDomain> findByIdAndUserId(UUID uuid, UUID userId);

}
