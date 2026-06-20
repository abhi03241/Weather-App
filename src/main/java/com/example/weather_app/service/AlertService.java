package com.example.weather_app.service;

import com.example.weather_app.model.Alert;
import com.example.weather_app.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String yourEmail;


    public void saveAlert(String email, double tempThreshold, String alertType, String city) {
        Alert alert = new Alert();
        alert.setEmail(email);
        alert.setTempThreshold(tempThreshold);
        alert.setAlertType(alertType);
        alert.setCity(city);

        alertRepository.save(alert);
    }

    public void sendAlertEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(yourEmail);
        mailSender.send(message);

        System.out.println("mail sent!");
    }
}