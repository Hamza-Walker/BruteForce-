package users;

import com.walker.logger.ConsoleLogger;
import com.walker.logger.Logger;
import com.walker.users.model.User;
import com.walker.users.repository.UserRepository;
import com.walker.users.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.List;

public class UserRepositoryImplTest {
    private UserRepository userRepository;
    String dbFilePath = "src/main/Users.db";
    private Logger logger;


    @BeforeEach
    public void setup() {
        // Initialize the UserRepositoryImpl with the SQLite database file for testing
        userRepository = new UserRepositoryImpl(dbFilePath, new ConsoleLogger());
        logger = new ConsoleLogger();

        // Create the users table in the database
        try (Connection conn = getConnection()) {
            if (conn != null) {
                String createTableSql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
                logger.logInfo("Creating users table: " + createTableSql);
                Statement statement = conn.createStatement();
                statement.execute(createTableSql);
                logger.logInfo("Users table created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws SQLException {
        try {
            String tempFilePath = System.getProperty("java.io.tmpdir") + "Users.db";
            File tempFile = new File(tempFilePath);
            tempFile.deleteOnExit();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dbFilePath);
            if (inputStream != null) {
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String url = "jdbc:sqlite:" + tempFile.getAbsolutePath();
                System.out.println("Database connection URL: " + url);
                return DriverManager.getConnection(url);
            } else {
                throw new FileNotFoundException("Database file not found: " + dbFilePath);
            }
        } catch (IOException e) {
            throw new SQLException("Error creating temporary database file: " + e.getMessage());
        }
    }

    @Test
    public void testAddUser() {
        // Create a new database file
        createDatabase();

        // Add a new user
        userRepository.add("john", "password");

        // ... rest of the test ...
    }

    private void createDatabase() {
        try {
            // Check if the database file already exists, and delete it if it does
            File dbFile = new File(dbFilePath);
            if (dbFile.exists()) {
                dbFile.delete();
            }

            // Create the database file and establish a connection
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);

            // Define the schema and create the users table
            String createTableSql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
            PreparedStatement statement = connection.prepareStatement(createTableSql);
            statement.execute();

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateUser() {
        // Add a new user
        userRepository.add("john", "password");

        // Update the user's password
        userRepository.update(1, "john", "newpassword");

        // Get the updated user from the repository
        User user = userRepository.get(1);

        // Verify that the user's password was updated successfully
        Assertions.assertNotNull(user);
        Assertions.assertEquals("newpassword", user.password());
    }

    @Test
    public void testDeleteUser() {
        // Add a new user
        userRepository.add("john", "password");

        // Delete the user
        userRepository.delete(1);

        // Try to get the deleted user from the repository
        User user = userRepository.get(1);

        // Verify that the user was deleted successfully
        Assertions.assertNull(user);
    }

    @Test
    public void testGetAllUsers() {
        // Add two users
        userRepository.add("john", "password");
        userRepository.add("jane", "password");

        // Get all users from the repository
        List<User> users = userRepository.getAll();

        // Verify that both users were retrieved successfully
        Assertions.assertEquals(2, users.size());
        Assertions.assertEquals("john", users.get(0).userName());
        Assertions.assertEquals("jane", users.get(1).userName());
    }
}
