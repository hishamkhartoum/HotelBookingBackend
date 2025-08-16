package com.hadef.hotelbooking.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hadef.hotelbooking.domain.value.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @NotNull(message = "User id is required")
    private UUID id;
    @NotBlank(message = "User email is required")
    private String email;
    @NotBlank(message = "User first name is required")
    private String firstName;
    @NotBlank(message = "User last name is required")
    private String lastName;
    @NotBlank(message = "User phone number is required")
    private String phoneNumber;
    @NotNull(message = "User role is required")
    private UserRole role;
    @NotNull(message = "User active is required")
    private boolean active;
}
