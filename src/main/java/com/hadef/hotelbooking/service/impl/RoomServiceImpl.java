package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.value.RoomType;
import com.hadef.hotelbooking.service.RoomService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RoomServiceImpl implements RoomService {
    @Override
    public Room addRoom(Room room, MultipartFile imageFile) {
        return null;
    }

    @Override
    public Room updateRoom(Room room, MultipartFile imageFile) {
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return List.of();
    }

    @Override
    public Room getRoomById(UUID id) {
        return null;
    }

    @Override
    public void deleteRoom(UUID id) {

    }

    @Override
    public List<Room> getAvailableRooms(LocalDateTime checkInDate, LocalDateTime checkOutDate, RoomType roomType) {
        return List.of();
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return List.of();
    }

    @Override
    public List<Room> searchRoom(String input) {
        return List.of();
    }
}
