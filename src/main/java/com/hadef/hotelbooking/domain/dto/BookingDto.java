package com.hadef.hotelbooking.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.BookingStatus;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto {
    @NotNull(message = "Booking id is required")
    private UUID id;

    @NotNull(message = "Booking user is required")
    private UserDto user;

    @NotNull(message = "Booking room is required")
    private RoomDto room;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Check-in date is required")
    private LocalDateTime checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDateTime checkOutDate;

    @NotNull(message = "Total price is required")
    private BigDecimal totalPrice;

    @NotBlank(message = "Booking reference is required")
    private String bookingReference;

    @NotNull(message = "Booking status is required")
    private BookingStatus bookingStatus;

}
