package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.exception.NotFoundException;
import com.hadef.hotelbooking.repository.BookingRepository;
import com.hadef.hotelbooking.repository.UserRepository;
import com.hadef.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public User getOwnAccountDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("getOwnAccountDetails(): email = {}", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    @Override
    public User getCurrentLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("getCurrentLoggedInUser(): email = {}", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    @Override
    public User updateOwnAccount(User user) {
        User currentLoggedInUser = getCurrentLoggedInUser();
        log.info("updateOwnAccount = {}", currentLoggedInUser);
        if(user.getEmail() != null) currentLoggedInUser.setEmail(user.getEmail());
        if(user.getFirstName() != null) currentLoggedInUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null) currentLoggedInUser.setLastName(user.getLastName());
        if(user.getPhoneNumber() != null) currentLoggedInUser.setPhoneNumber(user.getPhoneNumber());
        if(user.getPassword() != null && !user.getPassword().isEmpty())
            currentLoggedInUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(currentLoggedInUser);
    }

    @Override
    public void deleteOwnAccount() {
        User currentLoggedInUser = getCurrentLoggedInUser();
        userRepository.delete(currentLoggedInUser);
    }

    @Override
    public List<Booking> getMyBookingHistory() {
        User currentLoggedInUser = getCurrentLoggedInUser();
        return bookingRepository
                .findByUserId(currentLoggedInUser.getId());
    }
}
