package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.dto.ViewBookingDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookingMapper {

    Booking toEntity(BookingDto bookingDto);
    BookingDto toDto(Booking booking);
    ViewBookingDto toViewBookingDto(Booking booking);
}
