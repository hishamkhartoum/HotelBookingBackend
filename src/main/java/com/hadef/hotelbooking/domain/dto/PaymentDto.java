package com.hadef.hotelbooking.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.PaymentGateway;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDto {

    @NotNull(message = "Payment id is required")
    private UUID id;
    @NotNull(message = "Payment booking is required")
    private BookingDto booking;
    @NotBlank(message = "Payment transaction id is required")
    private String transactionId;
    @NotNull(message = "Payment amount is required")
    private BigDecimal amount;
    @NotNull(message = "Payment gateway is required")
    private PaymentGateway paymentGateway;
    @NotNull(message = "Payment date is required")
    private LocalDateTime paymentDate;
    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
    @NotBlank(message = "Payment booking reference is required")
    private String bookingReference;
    private String failureReason;
    @NotBlank(message = "Payment user is required")
    private User user;
}
