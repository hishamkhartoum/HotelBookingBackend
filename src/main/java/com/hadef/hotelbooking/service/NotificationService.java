package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.Notification;

public interface NotificationService {
    void sendEmail(Notification notification);
    void sendSMS();
    void sendWhatsApp();
}
