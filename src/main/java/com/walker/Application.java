package com.walker;

import com.walker.logger.ConsoleLogger;
import com.walker.logger.Logger;
import com.walker.users.model.User;
import com.walker.users.repository.UserRepository;
import com.walker.users.repository.UserRepositoryImpl;

public class Application {

    public static void main(String[] args) {
        Logger logger = new ConsoleLogger();
        String dbFile = "src/main/resources/Users.db";

        UserRepository userRepository = new UserRepositoryImpl(dbFile, logger);
        userRepository.add("john", "password");

        // Update the user's password
        userRepository.update(1, "john", "newpassword"); // Update user with ID 1

        // Get the updated user from the repository
        User user = userRepository.get(1); // Retrieve updated user with ID 1
        System.out.println(user);
    }
}
