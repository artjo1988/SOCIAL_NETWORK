package ru.itpark.service;


public interface EmailService {
    String sendMail(String text, String subject, String email);
}
