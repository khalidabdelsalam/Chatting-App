package com.example.chatting_room_projectfx;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginController {
    private final UserService userService = new UserService();
    private final LoginGUI loginGUI;
    private final UserDAO userDAO;

    public LoginController(LoginGUI loginGUI) {
        this.loginGUI = loginGUI;
        this.loginGUI.setController(this);
        this.userDAO = new UserDAO(DatabaseConnection.getConnection());
    }

    private void updateUserStatus(String username, String status) {
        String query = "UPDATE users SET status = ? WHERE username = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String password) {
        if (userService.loginUser(username, password)) {
            updateUserStatus(username, "active");

            showAlert(AlertType.INFORMATION, "Login successful!");

            try {
                userDAO.setUserActive(username);
                // Open chat room GUI
                loginGUI.getStage().close();

                // Start Chat Room
                ChatRoomGUI chatRoomGUI = new ChatRoomGUI(username);
                new ChatRoomController(chatRoomGUI);
                chatRoomGUI.start(new Stage()); // Assuming getStage() method is implemented in ChatRoomGUI to get Stage reference

                ChatRoomGUI2 chatRoomGUI2 = new ChatRoomGUI2("server");
                chatRoomGUI2.start(new Stage()); // Assuming getStage() method is implemented in ChatRoomGUI2 to get Stage reference

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(AlertType.ERROR, "Invalid username or password!");
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
