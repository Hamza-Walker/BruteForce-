package users;

import com.walker.passwords.generator.PasswordGenerator;
import com.walker.users.generator.UserGenerator;
import com.walker.users.generator.UserGeneratorImpl;
import com.walker.users.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGeneratorImplTest {
    private UserGenerator userGenerator;
    private List<PasswordGenerator> passwordGenerators;

    @BeforeEach
    public void setup() {
        // Create mock PasswordGenerator instances
        PasswordGenerator passwordGenerator1 = Mockito.mock(PasswordGenerator.class);
        PasswordGenerator passwordGenerator2 = Mockito.mock(PasswordGenerator.class);

        // Add the mock PasswordGenerator instances to the list
        passwordGenerators = new ArrayList<>();
        passwordGenerators.add(passwordGenerator1);
        passwordGenerators.add(passwordGenerator2);

        Random random = new Random(); // You can provide a seed value for consistent test results

        userGenerator = new UserGeneratorImpl(passwordGenerators, random);
    }

    @Test
    public void testGenerateUsersCount() {
        // Specify the desired number of users to generate
        int count = 5;
        int maxPasswordLength = 10;

        // Mock the password generator behavior
        Mockito.when(passwordGenerators.get(0).generate(Mockito.anyInt())).thenReturn("password1");
        Mockito.when(passwordGenerators.get(1).generate(Mockito.anyInt())).thenReturn("password2");

        // Generate the users
        List<User> users = userGenerator.generate(count, maxPasswordLength);

        // Ensure that the correct number of users is generated
        Assertions.assertEquals(count, users.size());
    }

    @Test
    public void testGenerateUsersPasswordLength() {
        // Specify the desired number of users to generate
        int count = 5;
        int maxPasswordLength = 10;

        // Generate the users
        List<User> users = userGenerator.generate(count, maxPasswordLength);

        // Ensure that each generated user's password length is within the specified range
        for (User user : users) {
            String password = user.password();
            Assertions.assertTrue(password.length() >= 1 && password.length() <= maxPasswordLength);
        }
    }

    @Test
    public void testGenerateUsersUserNameFormat() {
        // Specify the desired number of users to generate
        int count = 5;
        int maxPasswordLength = 10;

        // Mock the password generator behavior
        Mockito.when(passwordGenerators.get(0).generate(Mockito.anyInt())).thenReturn("password1");
        Mockito.when(passwordGenerators.get(1).generate(Mockito.anyInt())).thenReturn("password2");

        // Generate the users
        List<User> users = userGenerator.generate(count, maxPasswordLength);

        // Ensure that each generated user's username has the expected format (e.g., user1, user2, etc.)
        for (int i = 0; i < users.size(); i++) {
            String expectedUserName = "user" + (i + 1);
            String actualUserName = users.get(i).user_name();
            Assertions.assertEquals(expectedUserName, actualUserName);
        }
    }
}
