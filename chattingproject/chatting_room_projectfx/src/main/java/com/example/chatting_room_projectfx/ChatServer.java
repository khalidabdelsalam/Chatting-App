package com.example.chatting_room_projectfx;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static final int PORT = 12346;
    private static ConcurrentHashMap<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat server started...");

        // Start the chat server in a separate thread
        new Thread(ChatServer::startServer).start();

        // Start the GUI to display user statuses
        SwingUtilities.invokeLater(() -> {
            UserStatusGUI userStatusGUI = new UserStatusGUI();
            userStatusGUI.setVisible(true);
        });
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private String username;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Initial setup: get username
                out.println(":");
                username = in.readLine();
                synchronized (clientWriters) {
                    clientWriters.put(username, out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received from " + username + ": " + message);
                    // Broadcast message to all clients
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters.values()) {
                            if (writer != out) {
                                writer.println(username + ": " + message);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(username);
                }
            }
        }
    }

    private static class UserStatusGUI extends JFrame {
        private final JTable userTable;
        private final DefaultTableModel tableModel;

        public UserStatusGUI() {
            setTitle("User Status");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Initialize the table model and JTable
            tableModel = new DefaultTableModel(new String[]{"Username", "Status"}, 0);
            userTable = new JTable(tableModel);
            userTable.setRowHeight(30);

            // Custom cell renderer to color rows based on status
            userTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String status = (String) table.getValueAt(row, 1);
                    if ("inactive".equalsIgnoreCase(status)) {
                        cell.setBackground(Color.RED);
                        cell.setForeground(Color.white);
                    } else {
                        cell.setBackground(Color.GREEN);                        cell.setForeground(Color.white);

                    }
                    return cell;
                }
            });

            // Initialize the refresh button


            // Add components to the frame
            add(new JScrollPane(userTable), BorderLayout.CENTER);

            // Initial load of user statuses
            refreshUserStatus();
        }

        public void refreshUserStatus() {
            // Clear existing rows
            tableModel.setRowCount(0);

            // Fetch data from the database
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT username, status FROM users")) {

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String status = resultSet.getString("status");
                    tableModel.addRow(new Object[]{username, status});
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
