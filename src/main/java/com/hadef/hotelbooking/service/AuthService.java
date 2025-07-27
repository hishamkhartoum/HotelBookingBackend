package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.User;

public interface AuthService {
    void register(User user);
    User login(String email, String password);
}
