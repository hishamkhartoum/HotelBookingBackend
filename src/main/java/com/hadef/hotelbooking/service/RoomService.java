package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.value.RoomType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RoomService {
    Room addRoom(Room room, MultipartFile imageFile) throws IOException;
    Room updateRoom(Room room, MultipartFile imageFile) throws IOException;
    List<Room> getAllRooms();
    Room getRoomById(UUID id);
    void deleteRoom(UUID id);
    List<Room> getAvailableRooms(LocalDateTime checkInDate,
                                 LocalDateTime checkOutDate,
                                 RoomType roomType);
    List<RoomType> getAllRoomTypes();
    List<Room> searchRoom(String input);
}
