package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.CreateRoomDto;
import com.hadef.hotelbooking.domain.dto.RoomDto;
import com.hadef.hotelbooking.domain.dto.UpdateRoomDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.value.BookingStatus;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
import com.hadef.hotelbooking.domain.value.RoomType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {

    private RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @Test
    void toEntity() {
        RoomDto roomDto = RoomDto.builder()
                .id(UUID.randomUUID())
                .roomNumber((int)(Math.random() * 1000))
                .capacity(3)
                .description("Room Description")
                .imageUrl("Room Image")
                .pricePerNight(BigDecimal.valueOf(300))
                .type(RoomType.DOUBLE)
                .build();
        Room room = roomMapper.toEntity(roomDto);
        assertEquals(room.getId(),roomDto.getId());
        assertEquals(room.getRoomNumber(),roomDto.getRoomNumber());
        assertEquals(room.getCapacity(),roomDto.getCapacity());
        assertEquals(room.getDescription(),roomDto.getDescription());
        assertEquals(room.getImageUrl(),roomDto.getImageUrl());
        assertEquals(room.getPricePerNight(),roomDto.getPricePerNight());
        assertEquals(room.getType(),roomDto.getType());
    }

    @Test
    void toDto() {
        Booking booking1 = Booking.builder()
                .id(UUID.randomUUID())
                .checkInDate(LocalDateTime.now())
                .checkOutDate(LocalDateTime.now().plusDays(2))
                .paymentStatus(PaymentStatus.PENDING)
                .totalPrice(BigDecimal.valueOf(100.00))
                .bookingStatus(BookingStatus.BOOKED)
                .bookingReference("ABC123")
                .build();
        Booking booking2 = Booking.builder()
                .id(UUID.randomUUID())
                .checkInDate(LocalDateTime.now().plusDays(3))
                .checkOutDate(LocalDateTime.now().plusDays(5))
                .paymentStatus(PaymentStatus.PENDING)
                .totalPrice(BigDecimal.valueOf(100.00))
                .bookingStatus(BookingStatus.BOOKED)
                .bookingReference("ABC456")
                .build();
        List<Booking> bookings = List.of(booking1,booking2);

        Room room = Room.builder()
                .id(UUID.randomUUID())
                .roomNumber((int)(Math.random() * 1000))
                .capacity(3)
                .description("Room Description")
                .imageUrl("Room Image")
                .pricePerNight(BigDecimal.valueOf(300))
                .type(RoomType.DOUBLE)
                .booking(bookings)
                .build();
        RoomDto roomDto = roomMapper.toDto(room);

        assertEquals(room.getId(),roomDto.getId());
        assertEquals(room.getRoomNumber(),roomDto.getRoomNumber());
        assertEquals(room.getCapacity(),roomDto.getCapacity());
        assertEquals(room.getDescription(),roomDto.getDescription());
        assertEquals(room.getImageUrl(),roomDto.getImageUrl());
        assertEquals(room.getPricePerNight(),roomDto.getPricePerNight());
        assertEquals(room.getType(),roomDto.getType());
    }

    @Test
    void fromCreateRoomDtoToEntity() {
        CreateRoomDto roomDto = CreateRoomDto.builder()
                .roomNumber((int)(Math.random() * 1000))
                .capacity(3)
                .description("Room Description")
                .pricePerNight(BigDecimal.valueOf(300))
                .type(RoomType.DOUBLE)
                .build();
        Room room = roomMapper.fromCreateRoomDtoToEntity(roomDto);
        assertEquals(room.getRoomNumber(),roomDto.getRoomNumber());
        assertEquals(room.getCapacity(),roomDto.getCapacity());
        assertEquals(room.getDescription(),roomDto.getDescription());
        assertEquals(room.getPricePerNight(),roomDto.getPricePerNight());
        assertEquals(room.getType(),roomDto.getType());
    }

    @Test
    void fromUpdateRoomDtoToEntity() {
        UpdateRoomDto roomDto = UpdateRoomDto.builder()
                .roomNumber((int)(Math.random() * 1000))
                .capacity(3)
                .description("Room Description")
                .pricePerNight(BigDecimal.valueOf(300))
                .type(RoomType.DOUBLE)
                .build();

        Room room = roomMapper.fromUpdateRoomDtoToEntity(roomDto);
        assertEquals(room.getId(),roomDto.getId());
        assertEquals(room.getRoomNumber(),roomDto.getRoomNumber());
        assertEquals(room.getCapacity(),roomDto.getCapacity());
        assertEquals(room.getDescription(),roomDto.getDescription());
        assertEquals(room.getPricePerNight(),roomDto.getPricePerNight());
        assertEquals(room.getType(),roomDto.getType());
    }
}