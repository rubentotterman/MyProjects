package database;

import config.DatabaseConfiguration;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDbOperations {

    private final String SELECT_USER_QUERY = "select username, name, password from user where username = ? and password = ?";
    private final String INSERT_USER_QUERY = "insert into user (username, name, password) values (?,?,?)";

    public User insertUser(User user) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(INSERT_USER_QUERY)) {
            int index = 0;
            statement.setString(++index, user.getUsername());
            statement.setString(++index, user.getName());
            statement.setString(++index, user.getPassword());
            statement.executeUpdate();
        }
        return user;
    }

    public User login(String username, String password) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_USER_QUERY)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return getUser(results);
            }
            return null;
        }
    }

    private User getUser(ResultSet results) throws SQLException {
        User user = new User();
        user.setUsername(results.getString("username"));
        user.setName(results.getString("name"));
        user.setPassword(results.getString("password"));
        return user;
    }
}
