package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.service.UserService;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField checkPasswordField;
    @FXML private Label errorLabel;
    @FXML private Button registerButton;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        Platform.runLater(() -> usernameField.requestFocus());
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String checkPassword = checkPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || checkPassword.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        if (!password.equals(checkPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        setInputsDisabled(true);
        errorLabel.setStyle("-fx-text-fill: #bdc3c7;");
        errorLabel.setText("Registering user in background");

        Task<String> registerTask = new Task<>() {
            @Override
            protected String call() {
                return userService.registerUser(username, email, password, checkPassword);
            }
        };

        registerTask.setOnSucceeded(e -> {
            setInputsDisabled(false);
            String errorMessage = registerTask.getValue();

            if (errorMessage == null) {
                errorLabel.setStyle("-fx-text-fill: #2ecc71;");
                errorLabel.setText("Registration successful!");
                goToLogin(event);
            } else {
                errorLabel.setStyle("-fx-text-fill: #e74c3c;");
                errorLabel.setText(errorMessage);
            }
        });

        registerTask.setOnFailed(e -> {
            setInputsDisabled(false);
            errorLabel.setStyle("-fx-text-fill: #e74c3c;");
            errorLabel.setText("DB connection error during registration.");
            if (registerTask.getException() != null) {
                registerTask.getException().printStackTrace();
            }
        });

        new Thread(registerTask).start();
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + View.LOGIN.getFileName()));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Car Management System - Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading login view.");
        }
    }

    private void setInputsDisabled(boolean disabled) {
        usernameField.setDisable(disabled);
        emailField.setDisable(disabled);
        passwordField.setDisable(disabled);
        checkPasswordField.setDisable(disabled);
        registerButton.setDisable(disabled);
    }
}