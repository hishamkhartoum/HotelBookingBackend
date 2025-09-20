package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.value.RoomType;
import com.hadef.hotelbooking.exception.ImageAlreadyExistException;
import com.hadef.hotelbooking.exception.InvalidBookingStateAndDateException;
import com.hadef.hotelbooking.exception.NotFoundException;
import com.hadef.hotelbooking.repository.RoomRepository;
import com.hadef.hotelbooking.service.FileService;
import com.hadef.hotelbooking.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Override
    public Room addRoom(Room room, MultipartFile imageFile) throws IOException {
        if(Files.exists(Path.of(
                path+ File.separator+imageFile.getOriginalFilename()))){
            throw new ImageAlreadyExistException(
                    "Image already exists! with name " + imageFile.getOriginalFilename()
            );
        }
        String uploadedImage = fileService.uploadImage(path, imageFile);
        room.setImageUrl(uploadedImage);
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room room, MultipartFile imageFile) throws IOException {
        Room existingRoom = roomRepository.findById(room.getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Room not found! with id " + room.getId()
                ));
        if(existingRoom.getImageUrl()!=null){
            String uploadedImage = fileService.updateImage(path, imageFile,existingRoom.getImageUrl());
            existingRoom.setImageUrl(uploadedImage);
        }
        if(room.getRoomNumber()!=null &&
                room.getRoomNumber() > 0){
            existingRoom.setRoomNumber(room.getRoomNumber());
        }
        if(room.getPricePerNight()!=null &&
                room.getPricePerNight().compareTo(BigDecimal.ZERO) >= 0){
            existingRoom.setPricePerNight(room.getPricePerNight());
        }
        if(room.getCapacity()!=null && room.getCapacity()>0){
            existingRoom.setCapacity(room.getCapacity());
        }
        if(room.getType() != null){
            existingRoom.setType(room.getType());
        }
        if(room.getDescription()!=null){
            existingRoom.setDescription(room.getDescription());
        }
        return roomRepository.save(existingRoom);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Room getRoomById(UUID id) {
        return roomRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Room with id " + id + " not found")
        );
    }

    @Override
    public void deleteRoom(UUID id) {
        roomRepository.findById(id).ifPresent(room -> {
/*
            if(bookingService.isRoomBooked(room.getId())){
                throw new InvalidBookingStateAndDateException(
                        "Room with id "+room.getId()+" has been booked!");
            }
*/
            try {
                fileService.deleteImage(path,room.getImageUrl());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            roomRepository.delete(room);
        });
    }

    @Override
    public List<Room> getAvailableRooms(
            LocalDate checkInDate, LocalDate checkOutDate, RoomType roomType) {
        if(checkInDate==null || checkOutDate==null){
            throw new InvalidBookingStateAndDateException("checkInDate or checkOutDate is null");
        }
        if (checkInDate.isBefore(LocalDate.now())){
            throw new InvalidBookingStateAndDateException("check in date cannot be before today ");
        }

        if (checkOutDate.isBefore(checkInDate)){
            throw new InvalidBookingStateAndDateException("check out date cannot be before check in date ");
        }

        if (checkInDate.isEqual(checkOutDate)){
            throw new InvalidBookingStateAndDateException("check in date cannot be equal to check out date ");
        }
        return roomRepository.findAvailableRooms(checkInDate, checkOutDate, roomType);
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return Arrays.asList(RoomType.values());
    }

    @Override
    public List<Room> searchRoom(String input) {
        return roomRepository.searchRooms(input);
    }
}
