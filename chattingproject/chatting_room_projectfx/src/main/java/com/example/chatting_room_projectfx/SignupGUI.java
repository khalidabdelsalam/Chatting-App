    package com.example.chatting_room_projectfx;
    
    import javafx.application.Application;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.PasswordField;
    import javafx.scene.control.TextField;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.HBox;
    import javafx.stage.Stage;
    
    public class SignupGUI extends Application {
    
        private TextField usernameField;
        private PasswordField passwordField;
        private PasswordField confirmPasswordField;
        private Button signupButton;
        private Button cancelButton;
        private Button loginButton;
        private SignupController controller;
        private Stage primaryStage; // Keep a reference to the Stage
    
        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage; // Initialize the Stage reference
            primaryStage.setTitle("Sign Up");
    
            // Create a GridPane for the layout
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);
    
            // Background color for GridPane
            gridPane.setStyle("-fx-background-color: white;");
    
            // Username Label
            Label usernameLabel = new Label("Username:");
            usernameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0066cc;");
            gridPane.add(usernameLabel, 0, 0);
    
            // Username Field
            usernameField = new TextField();
            usernameField.setPrefWidth(200);
            gridPane.add(usernameField, 1, 0);
    
            // Password Label
            Label passwordLabel = new Label("Password:");
            passwordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0066cc;");
            gridPane.add(passwordLabel, 0, 1);
    
            // Password Field
            passwordField = new PasswordField();
            passwordField.setPrefWidth(200);
            gridPane.add(passwordField, 1, 1);
    
            // Confirm Password Label
            Label confirmPasswordLabel = new Label("Confirm Password:");
            confirmPasswordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0066cc;");
            gridPane.add(confirmPasswordLabel, 0, 2);
    
            // Confirm Password Field
            confirmPasswordField = new PasswordField();
            confirmPasswordField.setPrefWidth(200);
            gridPane.add(confirmPasswordField, 1, 2);
    
            // Sign Up Button
            signupButton = new Button("Sign Up");
            signupButton.setStyle("-fx-background-color: #303441; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
            signupButton.setOnAction(e -> controller.signup(usernameField.getText(), passwordField.getText(), confirmPasswordField.getText()));
            gridPane.add(signupButton, 0, 3, 2, 1);
            GridPane.setHalignment(signupButton, javafx.geometry.HPos.CENTER);
    
            // Cancel Button
            cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
            cancelButton.setOnAction(e -> primaryStage.close());
            gridPane.add(cancelButton, 0, 4);
            GridPane.setHalignment(cancelButton, javafx.geometry.HPos.CENTER);
    
            // Login Button
            loginButton = new Button("Login");
            loginButton.setStyle("-fx-background-color: #5b738f; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
            loginButton.setOnAction(e -> {
                primaryStage.close();
                LoginGUI loginGUI = new LoginGUI();
                Stage loginStage = new Stage();
                loginGUI.start(loginStage); // Show the Login GUI
                new LoginController(loginGUI); // Initialize LoginController after showing the stage
            });
            gridPane.add(loginButton, 1, 4);
            GridPane.setHalignment(loginButton, javafx.geometry.HPos.CENTER);
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(signupButton, cancelButton, loginButton);

            // Add margin to the HBox
            HBox.setMargin(signupButton, new Insets(0, 0, 0, 0)); // No margin on left
            HBox.setMargin(cancelButton, new Insets(0, 0, 0, 0)); // No margin on left
            HBox.setMargin(loginButton, new Insets(0, 0, 0, 0)); // No margin on left

            // Add the HBox to the GridPane with top margin
            gridPane.add(buttonBox, 0, 3, 2, 1);
            GridPane.setHalignment(buttonBox, javafx.geometry.HPos.CENTER);
            GridPane.setMargin(buttonBox, new Insets(20, 0, 0, 0)); // Top margin of 20

            Scene scene = new Scene(gridPane, 500, 400);
            primaryStage.setScene(scene);
            primaryStage.show();


        }
    
        public void setController(SignupController controller) {
            this.controller = controller;
        }
    
        // Method to close the stage
        public void close() {
            if (primaryStage != null) {
                primaryStage.close();
            }
        }
    
        public static void main(String[] args) {
            launch(args);
        }
    }
