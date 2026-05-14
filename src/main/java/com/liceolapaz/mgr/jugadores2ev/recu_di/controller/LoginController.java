package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;
import com.liceolapaz.mgr.jugadores2ev.recu_di.service.UserService;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager;
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
import java.util.Optional;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        Platform.runLater(() -> usernameField.requestFocus());
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        setInputsDisabled(true);
        errorLabel.setStyle("-fx-text-fill: #bdc3c7;");
        errorLabel.setText("Verifying credentials");

        Task<Optional<User>> loginTask = new Task<>() {
            @Override
            protected Optional<User> call() {
                return userService.login(username, password);
            }
        };

        loginTask.setOnSucceeded(e -> {
            setInputsDisabled(false);
            Optional<User> userOpt = loginTask.getValue();

            if (userOpt.isPresent()) {
                errorLabel.setStyle("-fx-text-fill: #2ecc71;");
                errorLabel.setText("Login successful");
                SessionManager.setCurrentUser(userOpt.get());
                openMainView(event);
            } else {
                errorLabel.setStyle("-fx-text-fill: #e74c3c;");
                errorLabel.setText("Invalid username or password.");
            }
        });

        loginTask.setOnFailed(e -> {
            setInputsDisabled(false);
            errorLabel.setStyle("-fx-text-fill: #e74c3c;");
            errorLabel.setText("DB connection error.");
            if (loginTask.getException() != null) {
                loginTask.getException().printStackTrace();
            }
        });

        new Thread(loginTask).start();
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        switchScene(event, View.REGISTER.getFileName(), "Register Account");
    }

    private void openMainView(ActionEvent event) {
        switchScene(event, View.MAIN.getFileName(), "Car Management System");
    }

    private void switchScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + fxmlFile));
            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading view: " + e.getMessage());
        }
    }

    private void setInputsDisabled(boolean disabled) {
        usernameField.setDisable(disabled);
        passwordField.setDisable(disabled);
        loginButton.setDisable(disabled);
    }
}