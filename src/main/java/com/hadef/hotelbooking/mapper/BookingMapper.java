package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.dto.CreateBookingDto;
import com.hadef.hotelbooking.domain.dto.UpdateBookingDto;
import com.hadef.hotelbooking.domain.dto.ViewBookingDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookingMapper {

    BookingDto toDto(Booking booking);
    ViewBookingDto toViewBookingDto(Booking booking);
    Booking fromCreateBookingDtoToEntity(CreateBookingDto createBookingDto);
    Booking fromUpdateBookingDtoToEntity(UpdateBookingDto updateBookingDto);
}
