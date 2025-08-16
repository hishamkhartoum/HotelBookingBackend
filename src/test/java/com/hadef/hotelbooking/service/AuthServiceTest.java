package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.UserRole;
import com.hadef.hotelbooking.exception.InvalidCredentialException;
import com.hadef.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_NewUser_Success() {
        // Arrange
        User user = User.builder()
                .firstName("Hisham")
                .lastName("Khartoum")
                .email("hishamkhartoum@gmail.com")
                .password("12345")
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("12345")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        authService.register(user);

        // Assert
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(UserRole.CUSTOMER, user.getRole());

        verify(userRepository).existsByEmail(user.getEmail());
        verify(passwordEncoder).encode("12345");
        verify(userRepository).save(user);
    }

    @Test
    void register_EmailAlreadyExists_ThrowsException() {
        // Arrange
        User user = User.builder()
                .email("duplicate@gmail.com")
                .password("12345")
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Act + Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> authService.register(user));

        assertEquals("email duplicate@gmail.com already exists for user", exception.getMessage());

        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void login_UserFoundAndPasswordMatches_ReturnsUser() {
        // Arrange
        String email = "hishamkhartoum@gmail.com";
        String rawPassword = "12345";
        String encodedPassword = "encoded123";

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Act
        User result = authService.login(email, rawPassword);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        // Arrange
        String email = "notfound@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act + Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> authService.login(email, "12345"));

        assertEquals("email " + email + " not found", exception.getMessage());

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void login_PasswordMismatch_ThrowsInvalidCredentialException() {
        // Arrange
        String email = "hishamkhartoum@gmail.com";
        String rawPassword = "wrongPassword";
        String encodedPassword = "encoded123";

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Act + Assert
        InvalidCredentialException exception = assertThrows(InvalidCredentialException.class,
                () -> authService.login(email, rawPassword));

        assertEquals("Invalid password", exception.getMessage());

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }
}