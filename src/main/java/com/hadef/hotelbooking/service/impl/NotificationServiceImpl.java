package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Notification;
import com.hadef.hotelbooking.repository.NotificationRepository;
import com.hadef.hotelbooking.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(Notification notification) {
        log.info("Sending mail email...");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notification.getRecipient());
        simpleMailMessage.setSubject(notification.getSubject());
        simpleMailMessage.setText(notification.getBody());

        javaMailSender.send(simpleMailMessage);
        notificationRepository.save(notification);
    }

    @Override
    public void sendSMS() {

    }

    @Override
    public void sendWhatsApp() {

    }
}
