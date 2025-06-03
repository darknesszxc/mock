package com.example.mock.worker;
import com.example.mock.model.User;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class DataBaseWorker {
    private final String url = "jdbc:postgresql://192.168.100.6:5432/mydb";
    private final String user = "user";
    private final String password = "password";

    public User getUserByLogin(String login) {
        String query = "SELECT u.login, u.password, u.date, uc.email " +
                "FROM users u JOIN users_contacts uc ON u.login = uc.login " +
                "WHERE u.login = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getDate("date"),
                            rs.getString("email")
                    );
                } else {
                    throw new RuntimeException("Пользователь не найден: " + login);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SELECT запроса: " + e.getMessage(), e);
        }
    }

    public int insertUser(User userObj) {
        String insert = "INSERT INTO users (login, password, date) VALUES (?, ?, ?);" +
                "INSERT INTO users_contacts (login, email) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, userObj.getLogin());
            stmt.setString(2, userObj.getPassword());
            stmt.setDate(3, userObj.getDate());
            stmt.setString(4, userObj.getLogin());
            stmt.setString(5, userObj.getEmail());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке: " + e.getMessage(), e);
        }
    }
}
