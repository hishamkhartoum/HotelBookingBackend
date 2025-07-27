package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking createBooking(Booking booking);
    Booking findBookingByReferenceNo(String bookingReference);
    Booking updateBooking(Booking booking);
}
