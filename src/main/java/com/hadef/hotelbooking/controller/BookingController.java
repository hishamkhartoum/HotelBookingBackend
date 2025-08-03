package com.hadef.hotelbooking.controller;

import com.hadef.hotelbooking.domain.Response;
import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.dto.CreateBookingDto;
import com.hadef.hotelbooking.domain.dto.UpdateBookingDto;
import com.hadef.hotelbooking.domain.dto.ViewBookingDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.mapper.BookingMapper;
import com.hadef.hotelbooking.service.BookingService;
import com.hadef.hotelbooking.service.RoomService;
import jakarta.validation.Valid;
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
    private final RoomService roomService;

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings(){
        List<ViewBookingDto> bookingDtoList = bookingService.getAllBookings().stream()
                .map(bookingMapper::toViewBookingDto).toList();
        Response response = Response.builder()
                .viewBookings(bookingDtoList)
                .status(200)
                .message("success")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    public ResponseEntity<Response> createBooking(@RequestBody CreateBookingDto createBookingDto){
        Room room = roomService.getRoomById(createBookingDto.getRoomId());
        Booking booking = bookingService.createBooking(
                bookingMapper.fromCreateBookingDtoToEntity(createBookingDto),room);
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
    public ResponseEntity<Response> updateBooking(@Valid @RequestBody UpdateBookingDto updateBookingDto){
        Room room = roomService.getRoomById(updateBookingDto.getRoomId());
        Booking booking = bookingMapper.fromUpdateBookingDtoToEntity(updateBookingDto);
        BookingDto dto = bookingMapper.toDto(bookingService.updateBooking(booking,room));
        Response response = Response.builder()
                .message("Booking updated successfully")
                .booking(dto)
                .status(200)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
