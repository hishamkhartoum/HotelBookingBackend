package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getOwnAccountDetails() {
        return null;
    }

    @Override
    public User getCurrentLoggedInUser() {
        return null;
    }

    @Override
    public User updateOwnAccount(User user) {
        return null;
    }

    @Override
    public void deleteOwnAccount() {

    }
}
