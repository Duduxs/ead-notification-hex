package com.ead.eadnotificationhex.adapters.configs;

import com.ead.eadnotificationhex.EadNotificationHexApplication;
import com.ead.eadnotificationhex.core.ports.NotificationPersistencePort;
import com.ead.eadnotificationhex.core.services.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EadNotificationHexApplication.class)
public class BeanConfiguration {

    @Bean
    NotificationServicePortImpl produceNotificationServicePortImpl(NotificationPersistencePort persistence) {
        return new NotificationServicePortImpl(persistence);
    }

    @Bean
    public ModelMapper produceModelMapper() {
        return new ModelMapper();
    }

}
