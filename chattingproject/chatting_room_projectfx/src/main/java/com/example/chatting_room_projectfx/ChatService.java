package com.example.chatting_room_projectfx;

import java.util.List;

public class ChatService {
    private final ChatHistoryDAO chatHistoryDAO;

    public ChatService(ChatHistoryDAO chatHistoryDAO) {
        this.chatHistoryDAO = chatHistoryDAO;
    }

    public void sendMessage(String sender, String message) {
        chatHistoryDAO.saveMessage(sender, message);
    }

    public List<String> getChatHistory(String username) {
        return chatHistoryDAO.getChatHistory(username);
    }
}
