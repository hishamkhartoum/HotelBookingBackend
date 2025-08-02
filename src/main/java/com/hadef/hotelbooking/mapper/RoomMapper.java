package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.CreateRoomDto;
import com.hadef.hotelbooking.domain.dto.RoomDto;
import com.hadef.hotelbooking.domain.dto.UpdateRoomDto;
import com.hadef.hotelbooking.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room toEntity(RoomDto roomDto);
    RoomDto toDto(Room room);
    Room fromCreateRoomDtoToEntity(CreateRoomDto createRoomDto);
    Room fromUpdateRoomDtoToEntity(UpdateRoomDto updateRoomDto);
}
