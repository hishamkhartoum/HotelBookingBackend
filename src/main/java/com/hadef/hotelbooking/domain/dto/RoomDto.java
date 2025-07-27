package com.hadef.hotelbooking.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hadef.hotelbooking.domain.value.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto {
    @NotNull(message = "Room id is required")
    private UUID id;
    @NotNull(message = "Room number is required")
    private Integer roomNumber;
    @NotNull(message = "Room type is required")
    private RoomType type;
    @NotNull(message = "Room price per night is required")
    private BigDecimal pricePerNight;
    @NotNull(message = "Room capacity is required")
    private Integer capacity;
    @NotBlank(message = "Room description is required")
    private String description;
    @NotBlank(message = "Room image is required")
    private String imageUrl;
}
