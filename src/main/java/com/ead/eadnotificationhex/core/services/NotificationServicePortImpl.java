package com.ead.eadnotificationhex.core.services;

import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.PageInfo;
import com.ead.eadnotificationhex.core.ports.NotificationPersistencePort;
import com.ead.eadnotificationhex.core.ports.NotificationServicePort;

import java.util.Collection;
import java.util.UUID;

import static com.ead.eadnotificationhex.core.domain.NotificationStatus.CREATED;

public class NotificationServicePortImpl implements NotificationServicePort {

    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        return notificationPersistencePort.saveNotification(notificationDomain);
    }

    @Override
    public Collection<NotificationDomain> findAllByUser(UUID userId, PageInfo pageable) {
        return notificationPersistencePort.findAllByUserIdAndStatus(userId, CREATED, pageable);
    }

    @Override
    public NotificationDomain updateStatusBy(UUID userId, UUID notificationId, NotificationDomain notificationDTO) {
        final var optionalEntity  = notificationPersistencePort.findByIdAndUserId(notificationId, userId);

        if(optionalEntity.isEmpty()) return null;

        final var entity = optionalEntity.get();

        entity.setStatus(notificationDTO.getStatus());

        return saveNotification(entity);
    }
}
