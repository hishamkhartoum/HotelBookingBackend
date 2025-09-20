package com.hadef.hotelbooking.controller;

import com.hadef.hotelbooking.domain.Response;
import com.hadef.hotelbooking.domain.dto.BookingDto;
import com.hadef.hotelbooking.domain.dto.UpdateUserRequestDto;
import com.hadef.hotelbooking.domain.dto.UserDto;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.mapper.BookingMapper;
import com.hadef.hotelbooking.mapper.UserMapper;
import com.hadef.hotelbooking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BookingMapper bookingMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        List<UserDto> allUsers = userService.getAllUsers().stream()
                .map(userMapper::toDto).toList();
        Response response = Response.builder()
                .status(200)
                .message("Success")
                .users(allUsers)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateUser(
            @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto){
        User user = userMapper.fromUpdateUserRequestDtoToEntity(updateUserRequestDto);
        User ownAccount = userService.updateOwnAccount(user);
        UserDto dto = userMapper.toDto(ownAccount);
        Response response = Response.builder()
                .status(200)
                .message("Success")
                .user(dto)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteUser(){
        userService.deleteOwnAccount();
        Response response = Response.builder()
                .status(200)
                .message("Success")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account")
    public ResponseEntity<Response> getAccount(){
        User ownAccountDetails = userService.getOwnAccountDetails();
        UserDto dto = userMapper.toDto(ownAccountDetails);
        Response response = Response.builder()
                .status(200)
                .message("Success")
                .user(dto)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings")
    public ResponseEntity<Response> getMyBookingHistory(){
        List<BookingDto> bookingDtoList = userService.getMyBookingHistory().stream().map(bookingMapper::toDto)
                .toList();
        Response response = Response.builder()
                .status(200)
                .message("Success")
                .bookings(bookingDtoList)
                .build();
        return ResponseEntity.ok(response);
    }
}
