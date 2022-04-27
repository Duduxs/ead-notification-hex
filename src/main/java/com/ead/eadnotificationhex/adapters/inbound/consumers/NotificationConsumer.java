package com.ead.eadnotificationhex.adapters.inbound.consumers;

import com.ead.eadnotificationhex.adapters.dtos.NotificationCommandDTO;
import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.ports.NotificationServicePort;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.ead.eadnotificationhex.core.domain.NotificationStatus.CREATED;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
public class NotificationConsumer {

    private final NotificationServicePort service;

    public NotificationConsumer(final NotificationServicePort service) {
        this.service = service;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}"),
                    exchange = @Exchange(
                            value = "${ead.broker.exchange.notificationCommandExchange}",
                            type = TOPIC, ignoreDeclarationExceptions = "true"
                    ),
                    key = "${ead.broker.key.notificationCommandKey}"
            )
    )
    public void listen(@Payload NotificationCommandDTO dto) {

        var model = new NotificationDomain();
        BeanUtils.copyProperties(dto, model);

        model.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        model.setStatus(CREATED);

        service.saveNotification(model);
    }

}
