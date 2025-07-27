package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Override
    public List<Booking> getAllBookings() {
        return List.of();
    }

    @Override
    public Booking createBooking(Booking booking) {
        return null;
    }

    @Override
    public Booking findBookingByReferenceNo(String bookingReference) {
        return null;
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return null;
    }
}
