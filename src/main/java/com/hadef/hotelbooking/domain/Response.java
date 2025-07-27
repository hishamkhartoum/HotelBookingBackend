package com.hadef.hotelbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hadef.hotelbooking.domain.dto.*;
import com.hadef.hotelbooking.domain.value.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private int status;
    private String message;

    private String token;
    private UserRole role;
    private boolean isActive;
    private String expirationTime;

    private UserDto user;
    private List<UserDto> users;

    private BookingDto booking;
    private List<BookingDto> bookings;

    private RoomDto room;
    private List<RoomDto> rooms;

    private PaymentDto payment;
    private List<PaymentDto> payments;

    private NotificationDto notification;
    private List<NotificationDto> notifications;

    private final LocalDateTime timestamp = LocalDateTime.now();
}
