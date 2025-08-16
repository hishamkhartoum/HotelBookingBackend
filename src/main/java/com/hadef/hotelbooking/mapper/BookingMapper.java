package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.dto.CreateBookingDto;
import com.hadef.hotelbooking.domain.dto.UpdateBookingDto;
import com.hadef.hotelbooking.domain.dto.ViewBookingDto;
import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookingMapper {

    BookingDto toDto(Booking booking);
    ViewBookingDto toViewBookingDto(Booking booking);

    @Mapping(source = "roomId", target = "room", qualifiedByName = "getRoom")
    Booking fromCreateBookingDtoToEntity(CreateBookingDto createBookingDto);
    @Mapping(source = "roomId", target = "room", qualifiedByName = "getRoom")
    Booking fromUpdateBookingDtoToEntity(UpdateBookingDto updateBookingDto);

    @Named("getRoom")
    default Room getRoom(UUID roomId) {
        if(null == roomId){
            return null;
        }
        return Room.builder()
                .id(roomId)
                .build();
    }
}
