package com.hadef.hotelbooking.repository;

import com.hadef.hotelbooking.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
