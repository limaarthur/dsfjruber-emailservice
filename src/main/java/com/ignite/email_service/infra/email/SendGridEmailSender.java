package com.ignite.email_service.infra.email;

import com.ignite.email_service.adapters.EmailSenderGateway;
import com.ignite.email_service.core.exceptions.EmailSendingException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendGridEmailSender implements EmailSenderGateway {

    private final SendGrid sendGrid;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public SendGridEmailSender(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        Email from = new Email(fromEmail); // Origem do e-mail (quem envia o e-mail)
        Email toEmail = new Email(to); // Destinatário quem irá receber o e-mail
        Content content = new Content("text/plain", body); // Mmensagem do e-mail (corpo do e-mail)
        Mail mail = new Mail(from, subject, toEmail, content);
        // Configura e cria o e-mail que será enviado com as informações de remetente do e-mail (from),  assunto do e-mail (subject), estinatário do e-mail (toEmail) e conteúdo ou corpo do e-mail (content).

        Request request = new Request(); // configura a requisição HTTP que será feita para a API do SendGrid
        try {
            request.setMethod(Method.POST); // Define que a requisição será POST
            request.setEndpoint("mail/send"); // Define o endpoint da API para envio de e-mail
            request.setBody(mail.build()); // Define o corpo da requisição (conteúdo do e-mail)

            Response response = sendGrid.api(request); // Envia a requisição para a API do SendGrid

            if (response.getStatusCode() >= 400) {
                throw new EmailSendingException("Falha ao enviar e-mail: " + response.getBody());
            }

        } catch (IOException exception) {
            throw new EmailSendingException("Erro ao enviar e-mail com SendGrid", exception);
        }
    }
}
