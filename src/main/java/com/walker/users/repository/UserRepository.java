package com.walker.users.repository;

import com.walker.users.model.User;

import java.util.List;

public interface UserRepository {

    void add(String user_name, String password);
    void update (Integer id, String user_name, String password);
    void delete (int id);
    void deleteAll();
    User get(int id);

    List<User> getAll();

}
