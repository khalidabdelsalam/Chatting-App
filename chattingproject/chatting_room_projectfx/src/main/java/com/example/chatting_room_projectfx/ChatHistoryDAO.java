package com.example.chatting_room_projectfx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ChatHistoryDAO {
    private final Connection connection;

    public ChatHistoryDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveMessage(String sender, String message) {
        String sql = "INSERT INTO chat_history (sender_username, message) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sender);
            statement.setString(2, message);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getChatHistory(String username) {
        // Implement the method to fetch chat history from the database if needed.
        return null;
    }
}
