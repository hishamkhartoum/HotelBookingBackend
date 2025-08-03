package com.hadef.hotelbooking.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequestDto {
//    @NotNull(message = "User id is required")
//    private UUID id;
    @NotBlank(message = "User email is required")
    private String email;
    @NotBlank(message = "Password should not be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain uppercase, lowercase, number, and special character"
    )
    private String password;
    @NotBlank(message = "User first name is required")
    private String firstName;
    @NotBlank(message = "User last name is required")
    private String lastName;
    @NotBlank(message = "User phone number is required")
    private String phoneNumber;
//    @NotNull(message = "User role is required")
//    private UserRole role;
    @NotNull(message = "User active is required")
    private boolean activate;
}
