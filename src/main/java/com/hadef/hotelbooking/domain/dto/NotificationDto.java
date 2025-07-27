package com.hadef.hotelbooking.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hadef.hotelbooking.domain.value.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDto {
    @NotNull(message = "Notification id is required")
    private UUID id;
    @NotBlank(message = "Notification subject is required")
    private String subject;
    @NotBlank(message = "Notification recipient is required")
    private String recipient;
    @NotBlank(message = "Notification body is required")
    private String body;
    @NotBlank(message = "Notification booking reference is required")
    private String bookingReference;
    @NotNull(message = "Notification type is required")
    private NotificationType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
