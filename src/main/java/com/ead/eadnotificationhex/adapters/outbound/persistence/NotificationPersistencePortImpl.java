package com.ead.eadnotificationhex.adapters.outbound.persistence;

import com.ead.eadnotificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.NotificationStatus;
import com.ead.eadnotificationhex.core.domain.PageInfo;
import com.ead.eadnotificationhex.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJPARepository repository;

    private final ModelMapper mapper;

    public NotificationPersistencePortImpl(
            final NotificationJPARepository repository,
            final ModelMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        final var entity = repository.save(mapper.map(notificationDomain, NotificationEntity.class));

        return mapper.map(entity, NotificationDomain.class);
    }

    @Override
    public Collection<NotificationDomain> findAllByUserIdAndStatus(UUID userId, NotificationStatus status, PageInfo pageInfo) {
        final Pageable page = PageRequest.of(pageInfo.getPage(), pageInfo.getSize());

        return repository.findAllByUserIdAndStatus(userId, status, page)
                .stream()
                .map(e -> mapper.map(e, NotificationDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationDomain> findByIdAndUserId(UUID uuid, UUID userId) {

        Optional<NotificationEntity> notificationEntityOptional =  repository.findByIdAndUserId(uuid, userId);

        if(notificationEntityOptional.isEmpty()) return Optional.empty();
        return Optional.of(mapper.map(notificationEntityOptional.get(), NotificationDomain.class));

    }
}
