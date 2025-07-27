package com.hadef.hotelbooking.domain.entity;

import com.hadef.hotelbooking.domain.value.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private Integer roomNumber;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Column(nullable = false)
    private BigDecimal pricePerNight;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false,columnDefinition = "text")
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;
}
