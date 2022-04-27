package com.ead.eadnotificationhex.core.ports;

import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.PageInfo;

import java.util.Collection;
import java.util.UUID;

public interface NotificationServicePort {

    NotificationDomain saveNotification(NotificationDomain notificationDomain);

    Collection<NotificationDomain> findAllByUser(UUID userId, PageInfo pageable);

    NotificationDomain updateStatusBy(UUID userId, UUID notificationId, NotificationDomain notificationDTO);
}
