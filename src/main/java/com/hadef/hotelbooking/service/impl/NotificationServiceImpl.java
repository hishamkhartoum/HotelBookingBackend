package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.service.BookingService;

import java.util.List;

public class NotificationServiceImpl implements BookingService {
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
