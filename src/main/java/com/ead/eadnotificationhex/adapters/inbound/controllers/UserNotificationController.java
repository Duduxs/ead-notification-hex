package com.ead.eadnotificationhex.adapters.inbound.controllers;

import com.ead.eadnotificationhex.adapters.dtos.NotificationDTO;
import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.PageInfo;
import com.ead.eadnotificationhex.core.ports.NotificationServicePort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationServicePort service;

    private final ModelMapper mapper;

    public UserNotificationController(
            final NotificationServicePort service,
            final ModelMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/users/{userId}/notifications")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<Page<NotificationDomain>> findAllNotificationsByUser(
            @PathVariable UUID userId,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = ASC)
            Pageable pageable
    ) {

        var pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize());

        List<NotificationDomain> notificationList = new ArrayList<>(service.findAllByUser(userId, pageInfo));

        return ResponseEntity.ok(new PageImpl<>(notificationList, pageable, notificationList.size()));

    }

    @PatchMapping("/users/{userId}/notifications/{notificationId}")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<NotificationDomain> updateStatus(
            @PathVariable UUID userId,
            @PathVariable UUID notificationId,
            @RequestBody @Valid NotificationDTO notificationDTO
    ) {
        final var entity = mapper.map(notificationDTO, NotificationDomain.class);

        final var result = service.updateStatusBy(userId, notificationId, entity);

        return ResponseEntity.ok(result);
    }

}
