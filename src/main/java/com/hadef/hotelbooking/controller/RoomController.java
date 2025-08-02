package com.hadef.hotelbooking.controller;

import com.hadef.hotelbooking.domain.Response;
import com.hadef.hotelbooking.domain.dto.CreateRoomDto;
import com.hadef.hotelbooking.domain.dto.RoomDto;
import com.hadef.hotelbooking.domain.dto.UpdateRoomDto;
import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.value.RoomType;
import com.hadef.hotelbooking.mapper.RoomMapper;
import com.hadef.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(@RequestPart CreateRoomDto createRoomDto,
                                            @RequestPart MultipartFile image) throws IOException {
        Room addedRoom = roomService.addRoom(
                roomMapper.fromCreateRoomDtoToEntity(createRoomDto), image
        );
        RoomDto dto = roomMapper.toDto(addedRoom);
        Response response = Response.builder()
                .room(dto)
                .status(201)
                .message("Room added successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@RequestPart UpdateRoomDto updateRoomDto,@RequestPart MultipartFile image) throws IOException {
        Room updatedRoom = roomService.updateRoom(
                roomMapper.fromUpdateRoomDtoToEntity(updateRoomDto), image
        );
        RoomDto dto = roomMapper.toDto(updatedRoom);
        Response response = Response.builder()
                .room(dto)
                .status(200)
                .message("Room updated successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms() {
        List<RoomDto> roomsDtoList = roomService.getAllRooms().stream().map(
                roomMapper::toDto
        ).toList();
        Response response = Response.builder()
                .rooms(roomsDtoList)
                .status(200)
                .message("success")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getRoomById(@PathVariable UUID id) {
        RoomDto dto = roomMapper.toDto(roomService.getRoomById(id));
        Response response = Response.builder()
                .room(dto)
                .status(200)
                .message("success")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable UUID id) {
        roomService.deleteRoom(id);
        Response response = Response.builder()
                .status(204)
                .message("success")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<Response> getAvailableRooms(
            @RequestParam LocalDateTime checkInDate,
            @RequestParam LocalDateTime checkOutDate,
            @RequestParam(required = false) RoomType roomType){
        List<RoomDto> roomDtoList = roomService.getAvailableRooms(checkInDate, checkOutDate, roomType).stream()
                .map(roomMapper::toDto).toList();
        Response response = Response.builder()
                .status(200)
                .message("success")
                .rooms(roomDtoList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<List<RoomType>> getRoomTypes() {
        return ResponseEntity.ok(roomService.getAllRoomTypes());
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchRoom(@RequestParam String input){
        List<RoomDto> roomDtoList = roomService.searchRoom(input).stream()
                .map(roomMapper::toDto).toList();
        Response response = Response.builder()
                .status(200)
                .message("success")
                .rooms(roomDtoList)
                .build();
        return ResponseEntity.ok(response);
    }

}
