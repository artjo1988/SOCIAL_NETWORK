package ru.itpark.service;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private ExecutorService executorService;

    public EmailServiceImpl(){
        executorService = Executors.newFixedThreadPool(10);
    }

    @SneakyThrows
    public String sendMail(String text, String subject, String email) {
        executorService.submit(() -> {
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                message.setContent(text, "text/html");
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
                messageHelper.setTo(email);
                messageHelper.setSubject(subject);
                messageHelper.setText(text, true);
            }
            catch(Exception e){
                throw new IllegalArgumentException(e);
            }
            javaMailSender.send(message);
        });
        return "Отправка сообщения прошла успешно";
    }
}
