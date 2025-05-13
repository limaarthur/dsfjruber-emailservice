package com.ignite.email_service.core;

public interface EmailSenderUseCase {
    void sendEmail(String to, String subject, String body); // Define o comportamento da aplicação as regras de negócio
}
