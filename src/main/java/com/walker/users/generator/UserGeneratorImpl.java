package com.walker.users.generator;

import com.walker.passwords.generator.PasswordGenerator;
import com.walker.users.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGeneratorImpl implements UserGenerator{

    private final List<PasswordGenerator> passwordGenerators;
    private final Random random;

    public UserGeneratorImpl(List<PasswordGenerator> passwordGenerators, Random random) {
        this.passwordGenerators = passwordGenerators;
        this.random = random;
    }

    @Override
    public List<User> generate(int count, int maxPasswordLength) {
        List<User> users = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < count; i++) {
            String userName = "user" + counter++;
            PasswordGenerator passwordGenerator = getRandomPasswordGenerator();
            String password = passwordGenerator.generate(getRandomPasswordLength(maxPasswordLength));
            User user = new User(0, userName, password);
            users.add(user);
        }
        return users;
    }


    private PasswordGenerator getRandomPasswordGenerator() {
        int index = random.nextInt(passwordGenerators.size());
        return passwordGenerators.get(index);
    }

    private int getRandomPasswordLength(int maxPasswordLength) {
        return random.nextInt(maxPasswordLength) + 1;
    }
}
