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

        String dbFile = "src/main/resources/Users.db";

        UserRepository userRepository = new UserRepositoryImpl(dbFile, logger);
        userRepository.deleteAll();

        List<PasswordGenerator> passwordGenerators = createPasswordGenerators();
        UserGenerator userGenerator = new UserGeneratorImpl(passwordGenerators, new Random());
        int userCount = 10;
        int maxPwLength = 4;

        addUsersToDb(userCount, maxPwLength, userGenerator, userRepository);

        logger.logInfo(String.format("Database initialized with %d users; maximum password length: %d%n", userCount, maxPwLength));

        // AuthenticationService authenticationService = null;
        //breakUsers(userCount, maxPwLength, authenticationService);

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
