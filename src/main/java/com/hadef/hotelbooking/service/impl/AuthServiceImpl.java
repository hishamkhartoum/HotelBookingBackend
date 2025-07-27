package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.UserRole;
import com.hadef.hotelbooking.exception.InvalidCredentialException;
import com.hadef.hotelbooking.repository.UserRepository;
import com.hadef.hotelbooking.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {
        String email = user.getEmail();
        log.info("registering email : {}", email);
        if(userRepository.existsByEmail(email)){
            throw new IllegalStateException("email "+email+" already exists for user");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        log.info("login user : {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalStateException("email "+email+" not found"));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialException("Invalid password");
        }

        return user;
    }
}
