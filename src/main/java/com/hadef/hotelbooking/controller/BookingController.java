package com.hadef.hotelbooking.controller;

import com.hadef.hotelbooking.domain.Response;
import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.dto.ViewBookingDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.mapper.BookingMapper;
import com.hadef.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings(){
        List<ViewBookingDto> bookingDtoList = bookingService.getAllBookings().stream()
                .map(bookingMapper::toViewBookingDto).toList();
        Response response = Response.builder()
                .viewBookings(bookingDtoList)
                .status(200)
                .message("All bookings successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    public ResponseEntity<Response> createBooking(@RequestBody BookingDto bookingDto){
        Booking booking = bookingService.createBooking(bookingMapper.toEntity(bookingDto));
        BookingDto responseBookingDto = bookingMapper.toDto(booking);
        Response response = Response.builder()
                .status(200)
                .message("Booking created successfully")
                .booking(responseBookingDto)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<Response> findBookingByReferenceNo(
            @PathVariable String reference){
        BookingDto bookingDto = bookingMapper.toDto(
                bookingService.findBookingByReferenceNo(reference));
        Response response = Response.builder()
                .booking(bookingDto)
                .status(200)
                .message("Booking found successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateBooking(@RequestBody BookingDto bookingDto){
        BookingDto dto = bookingMapper.toDto(
                bookingService.updateBooking(
                        bookingMapper.toEntity(bookingDto)));
        Response response = Response.builder()
                .message("Booking updated successfully")
                .booking(dto)
                .status(200)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
