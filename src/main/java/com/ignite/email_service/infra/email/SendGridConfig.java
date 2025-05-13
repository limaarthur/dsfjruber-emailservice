package com.ignite.email_service.infra.email;

import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(System.getProperty("SENDGRID_API_KEY"));
    }
}
