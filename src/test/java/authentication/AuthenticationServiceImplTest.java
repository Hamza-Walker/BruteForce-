package authentication;


import com.walker.authentication.AuthenticationService;
import com.walker.authentication.AuthenticationServiceImpl;
import com.walker.users.model.User;
import com.walker.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationServiceImplTest {
    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        authenticationService = new AuthenticationServiceImpl(userRepository);
    }

    @Test
    public void testAuthenticate_ValidCredentials_ReturnsTrue() {
        // Create a list of users by copying the generated users and passwords from the database
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "o0Di"));
        users.add(new User(2, "user2", "4"));
        users.add(new User(3, "user3", "u"));
        users.add(new User(4, "user4", "IjQ"));
        users.add(new User(5, "user5", "ry"));
        users.add(new User(6, "user6", "K59"));
        users.add(new User(7, "user7", "nD"));
        users.add(new User(8, "user8", "7"));
        users.add(new User(9, "user9", "d"));
        users.add(new User(10, "user10", "lsg"));

        // Mock the UserRepository behavior
        Mockito.when(userRepository.getAll()).thenReturn(users);

        // Test authentication with valid credentials
        boolean result = authenticationService.authenticate("user1", "o0Di");
        Assertions.assertTrue(result);
    }

    @Test
    public void testAuthenticate_InvalidCredentials_ReturnsFalse() {
        // Create an empty list of users
        List<User> users = new ArrayList<>();

        // Mock the UserRepository behavior
        Mockito.when(userRepository.getAll()).thenReturn(users);

        // Test authentication with invalid credentials
        boolean result = authenticationService.authenticate("user1", "invalid");
        Assertions.assertFalse(result);
    }
}
