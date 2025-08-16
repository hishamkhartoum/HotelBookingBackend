package com.hadef.hotelbooking.mapper;

import com.hadef.hotelbooking.domain.dto.RegistrationRequestDto;
import com.hadef.hotelbooking.domain.dto.UpdateUserRequestDto;
import com.hadef.hotelbooking.domain.dto.UserDto;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.UserRole;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Test
    void toDto() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("hishamkhartoum@example.com")
                .firstName("Hisham")
                .lastName("Khartoum")
                .phoneNumber("123456789")
                .password("12345")
                .role(UserRole.CUSTOMER)
                .active(true)
                .build();
        UserDto dto = userMapper.toDto(user);
        assertEquals(dto.getId(),user.getId());
        assertEquals(dto.getEmail(),user.getEmail());
        assertEquals(dto.getFirstName(),user.getFirstName());
        assertEquals(dto.getLastName(),user.getLastName());
        assertEquals(dto.getPhoneNumber(),user.getPhoneNumber());
        assertEquals(dto.getRole(),user.getRole());
        assertEquals(dto.getRole(),user.getRole());
    }

    @Test
    void fromRegistrationRequestToEntity() {
        RegistrationRequestDto dto = RegistrationRequestDto.builder()
                .email("hishamkhartoum@example.com")
                .firstName("Hisham")
                .lastName("Khartoum")
                .phoneNumber("123456789")
                .password("12345")
                .build();
        User user = userMapper.fromRegistrationRequestToEntity(dto);

        assertEquals(dto.getEmail(),user.getEmail());
        assertEquals(dto.getFirstName(),user.getFirstName());
        assertEquals(dto.getLastName(),user.getLastName());
        assertEquals(dto.getPhoneNumber(),user.getPhoneNumber());
        assertEquals(dto.getPassword(),user.getPassword());
    }

    @Test
    void fromUpdateUserRequestDtoToEntity() {
        UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
                .email("hishamkhartoum@example.com")
                .firstName("Hisham")
                .lastName("Khartoum")
                .phoneNumber("123456789")
                .password("12345")
                .active(true)
                .build();
        User user = userMapper.fromUpdateUserRequestDtoToEntity(dto);

        assertEquals(dto.getEmail(),user.getEmail());
        assertEquals(dto.getFirstName(),user.getFirstName());
        assertEquals(dto.getLastName(),user.getLastName());
        assertEquals(dto.getPhoneNumber(),user.getPhoneNumber());
        assertEquals(dto.getPassword(),user.getPassword());
        assertEquals(dto.isActive(),user.isActive());
    }
}