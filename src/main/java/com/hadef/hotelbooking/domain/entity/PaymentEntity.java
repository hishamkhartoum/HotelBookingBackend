package com.hadef.hotelbooking.domain.entity;

import com.hadef.hotelbooking.domain.value.PaymentGateway;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String transactionId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentGateway paymentGateway;
    private LocalDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String bookingReference;
    private String failureReason;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
}
