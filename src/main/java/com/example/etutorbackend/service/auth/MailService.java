package com.example.etutorbackend.service.auth;

import com.example.etutorbackend.model.entity.ConfirmationToken;
import com.example.etutorbackend.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendConfirmationEmail(User user, ConfirmationToken confirmationToken) {

        String emailBuilder = "Hello, " + user.getFirstName() + " " + user.getLastName() +
                "\nTo confirm your account hit this endpoint --> " + "http://localhost:8081/api/v1/auth/confirmation?token=" + confirmationToken.getToken() +
                "\nSee you!";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("e_tutor.backend@gmail.com");
        simpleMailMessage.setSubject("Account confirmation");
        simpleMailMessage.setText(emailBuilder);
        simpleMailMessage.setTo(user.getEmail());

        javaMailSender.send(simpleMailMessage);
    }
}
