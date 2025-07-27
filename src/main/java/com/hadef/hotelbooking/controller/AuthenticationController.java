package com.hadef.hotelbooking.controller;

import com.hadef.hotelbooking.domain.Response;
import com.hadef.hotelbooking.domain.dto.LoginRequest;
import com.hadef.hotelbooking.domain.dto.RegistrationRequest;
import com.hadef.hotelbooking.domain.dto.UserDto;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.mapper.UserMapper;
import com.hadef.hotelbooking.service.AuthService;
import com.hadef.hotelbooking.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<Response> register(
            @Valid @RequestBody RegistrationRequest registrationRequest) {
        User user = userMapper.fromRegistrationRequestToEntity(registrationRequest);
        authService.register(user);
        Response response = Response.builder()
                .status(200)
                .message("User registered successfully!")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Response> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        User loggedInUser = authService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword());
        UserDto userDto = userMapper.toDto(loggedInUser);
        Response response = Response.builder()
                .status(200)
                .message("User logged in successfully!")
                .role(userDto.getRole())
                .token(jwtUtils.generateToken(userDto.getEmail()))
                .isActive(userDto.isActivate())
                .expirationTime("6 month")
                .build();
        return ResponseEntity.ok(response);
    }
}
