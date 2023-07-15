package com.walker.users.generator;

import com.walker.users.model.User;

import java.util.List;

public interface UserGenerator {
    List<User> generate ( int count, int maxPasswordLength);
}
