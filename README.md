# Chatting-App
Chatting Desktop Application Using Java

Features:
Peer-to-peer messaging via TCP sockets for instant communication between multiple clients.
Multi-client support: Multiple users can connect to the server and exchange messages.
User-friendly GUI: Developed with Swing (or JavaFX), providing a basic chat interface.
Graceful connection management — handles client connect/disconnect events.



Build & Run:

to Compile project
javac -d out/ src/**/*.java

Start the server
java -cp out server.Server

Launch clients
java -cp out client.ChatClient

