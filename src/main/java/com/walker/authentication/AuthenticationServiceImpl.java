package com.walker.authentication;

import com.walker.users.model.User;
import com.walker.users.repository.UserRepository;

import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String user_name, String password) {
        List<User> users = userRepository.getAll();

        for (User user : users) {
            if (user.user_name().equals(user_name) && user.password().equals(password)) {
            return true; // Authentication successful
            }
        }
        return false; // Authentication failed
    }
}
