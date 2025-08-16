package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.BookingStatus;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
import com.hadef.hotelbooking.domain.value.RoomType;
import com.hadef.hotelbooking.domain.value.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    private final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);


    @Test
    void toDto() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("hishamkhartoum@example.com")
                .firstName("Hisham")
                .lastName("Khartoum")
                .phoneNumber("123456789")
                .role(UserRole.CUSTOMER)
                .active(true)
                .build();

        Room room = Room.builder()
                .id(UUID.randomUUID())
                .roomNumber(101)
                .type(RoomType.SINGLE)
                .pricePerNight(BigDecimal.valueOf(50.0))
                .capacity(1)
                .description("Nice single room")
                .imageUrl("http://hadef.com/room.jpg")
                .build();

        Booking booking = Booking.builder()
                .id(UUID.randomUUID())
                .user(user)
                .room(room)
                .checkInDate(LocalDateTime.now())
                .checkOutDate(LocalDateTime.now().plusDays(2))
                .paymentStatus(PaymentStatus.PENDING)
                .totalPrice(BigDecimal.valueOf(100.00))
                .bookingStatus(BookingStatus.BOOKED)
                .bookingReference("ABC123")
                .build();

        BookingDto bookingDto = mapper.toDto(booking);

        // booking basic fields
        assertEquals(booking.getId(), bookingDto.getId());
        assertEquals(booking.getPaymentStatus(), bookingDto.getPaymentStatus());
        assertEquals(booking.getBookingStatus(), bookingDto.getBookingStatus());
        assertEquals(booking.getTotalPrice(), bookingDto.getTotalPrice());
        assertEquals(booking.getBookingReference(), bookingDto.getBookingReference());
        assertEquals(booking.getCheckInDate(), bookingDto.getCheckInDate());
        assertEquals(booking.getCheckOutDate(), bookingDto.getCheckOutDate());

        // user mapping
        assertEquals(booking.getUser().getEmail(), bookingDto.getUser().getEmail());
        assertEquals(booking.getUser().getFirstName(), bookingDto.getUser().getFirstName());
        assertEquals(booking.getUser().getLastName(), bookingDto.getUser().getLastName());
        assertEquals(booking.getUser().getPhoneNumber(), bookingDto.getUser().getPhoneNumber());
        assertEquals(booking.getUser().getRole(), bookingDto.getUser().getRole());

        // room mapping
        assertEquals(booking.getRoom().getRoomNumber(), bookingDto.getRoom().getRoomNumber());
        assertEquals(booking.getRoom().getType(), bookingDto.getRoom().getType());
        assertEquals(booking.getRoom().getPricePerNight(), bookingDto.getRoom().getPricePerNight());
        assertEquals(booking.getRoom().getCapacity(), bookingDto.getRoom().getCapacity());
        assertEquals(booking.getRoom().getDescription(), bookingDto.getRoom().getDescription());
        assertEquals(booking.getRoom().getImageUrl(), bookingDto.getRoom().getImageUrl());
    }

    @Test
    void toViewBookingDto() {
    }

    @Test
    void fromCreateBookingDtoToEntity() {
    }

    @Test
    void fromUpdateBookingDtoToEntity() {
    }
}