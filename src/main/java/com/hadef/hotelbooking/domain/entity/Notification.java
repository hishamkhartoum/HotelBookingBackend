package com.hadef.hotelbooking.domain.entity;

import com.hadef.hotelbooking.domain.value.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String body;
    private String bookingReference;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
