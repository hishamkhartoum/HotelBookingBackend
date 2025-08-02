package com.hadef.hotelbooking.domain.dto;

import com.hadef.hotelbooking.domain.value.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomDto {
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
}
