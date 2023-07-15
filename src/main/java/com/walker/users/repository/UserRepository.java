package com.walker.users.repository;

import com.walker.users.model.User;

import java.util.List;

public interface UserRepository {

    void add(String userName, String password);
    void update (Integer id, String userName, String password);
    void delete (int id);
    void deleteAll();
    User get(int id);

    List<User> getAll();

}
