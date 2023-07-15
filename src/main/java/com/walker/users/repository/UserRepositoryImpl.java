package com.walker.users.repository;

import com.walker.logger.Logger;
import com.walker.users.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final String dbFile;
    private final Logger logger;

    public UserRepositoryImpl(String dbFile, Logger logger) {
        this.dbFile = dbFile;
        this.logger = logger;
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbFile;
            conn = DriverManager.getConnection(url);

            logger.logInfo("Connection to SQLite has been established.");

            return conn;
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
        return null;
    }

    @Override
    public void add(String userName, String password) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                logger.logInfo("Adding user: " + userName);
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, userName);
                statement.setString(2, password);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    logger.logInfo("User added successfully.");
                } else {
                    logger.logError("Failed to add user.");
                }
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }


    @Override
    public void update(Integer id, String user_Name, String password) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, user_Name);
                statement.setString(2, password);
                statement.setInt(3, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    logger.logInfo("User updated successfully.");
                } else {
                    logger.logError("Failed to update user.");
                }
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    logger.logInfo("User deleted successfully.");
                } else {
                    logger.logError("Failed to delete user.");
                }
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                String sql = "DELETE FROM users";
                Statement statement = conn.createStatement();
                int rowsDeleted = statement.executeUpdate(sql);
                if (rowsDeleted > 0) {
                    logger.logInfo("All users deleted successfully.");
                } else {
                    logger.logError("Failed to delete users.");
                }
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
    }

    @Override
    public User get(int id) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String userName = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    return new User(userId, userName, password);
                } else {
                    logger.logInfo("User not found.");
                }
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String userName = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    users.add(new User(userId, userName, password));
                }
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }
        return users;
    }
}
