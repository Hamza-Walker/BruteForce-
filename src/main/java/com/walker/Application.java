package com.walker;



import com.walker.logger.ConsoleLogger;
import com.walker.logger.Logger;
import com.walker.passwords.generator.PasswordGenerator;
import com.walker.passwords.generator.PasswordGeneratorImpl;
import com.walker.passwords.model.AsciiTableRange;
import com.walker.users.generator.UserGenerator;
import com.walker.users.generator.UserGeneratorImpl;
import com.walker.users.model.User;
import com.walker.users.repository.UserRepository;
import com.walker.users.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Random;

public class Application {


    private static final AsciiTableRange lowercaseChars = new AsciiTableRange(97, 122);
    private static final AsciiTableRange uppercaseChars = new AsciiTableRange(65, 90);
    private static final AsciiTableRange numbers = new AsciiTableRange(48, 57);

    private static Logger logger = new ConsoleLogger();
    public static void main(String[] args) {
        // Specify the path of the database file
        String dbFile = "src/main/resources/Users.db";

        // Create an instance of the user repository implementation
        UserRepository userRepository = new UserRepositoryImpl(dbFile, logger);

        // Clear the existing users from the database
        userRepository.deleteAll();

        // Create the password generators
        List<PasswordGenerator> passwordGenerators = createPasswordGenerators();

        // Create the user generator with the password generators and a random number generator
        UserGenerator userGenerator = new UserGeneratorImpl(passwordGenerators, new Random());

        // Specify the number of users to generate and the maximum password length
        int userCount = 10;
        int maxPwLength = 4;

        // Generate users and add them to the database
        addUsersToDb(userCount, maxPwLength, userGenerator, userRepository);

        // Log the successful initialization of the database
        logger.logInfo(String.format("Database initialized with %d users; maximum password length: %d%n", userCount, maxPwLength));

        // Additional code for authentication service or other operations can be added here
    }

    private static void addUsersToDb(int count, int maxPwLength, UserGenerator userGenerator, UserRepository userRepository) {
        List<User> users = userGenerator.generate(count, maxPwLength);
        for (User user : users) {
            userRepository.add(user.user_name(), user.password());
        }
    }


    private static List<PasswordGenerator> createPasswordGenerators() {
        var lowercasePwGen = new PasswordGeneratorImpl(List.of(lowercaseChars), new Random());
        var uppercasePwGen = new PasswordGeneratorImpl(List.of(lowercaseChars, uppercaseChars), new Random());
        var numbersPwGen = new PasswordGeneratorImpl(List.of(lowercaseChars, uppercaseChars, numbers), new Random());

        return List.of(lowercasePwGen, uppercasePwGen, numbersPwGen);
    }


}
