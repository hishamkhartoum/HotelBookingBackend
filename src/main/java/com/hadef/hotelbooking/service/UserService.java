package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getOwnAccountDetails();
    User getCurrentLoggedInUser();
    User updateOwnAccount(User user);
    void deleteOwnAccount();
    // getMyBookingHistory();
}
