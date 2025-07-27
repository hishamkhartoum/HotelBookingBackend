package com.hadef.hotelbooking.domain.entity;

import com.hadef.hotelbooking.domain.value.BookingStatus;
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
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(cascade = CascadeType.REMOVE )
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus  paymentStatus;
    @Column(nullable = false)
    private LocalDateTime checkInDate;
    @Column(nullable = false)
    private LocalDateTime checkOutDate;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private String bookingReference;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
