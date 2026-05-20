package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;
import com.liceolapaz.mgr.jugadores2ev.recu_di.service.UserService;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    @FXML private Button registerButton;

    private final UserService userService = new UserService();

    @FXML
    private void handleRegister() {
        String user = usernameField.getText();
        String email = emailField.getText();
        String pass = passwordField.getText();
        String checkPass = confirmPasswordField.getText();

        errorLabel.setStyle("-fx-text-fill: #3498db;");
        errorLabel.setText("Creating account...");
        registerButton.setDisable(true);

        Task<String> registerTask = new Task<>() {
            @Override
            protected String call() {
                return userService.registerUser(user, email, pass, checkPass);
            }
        };

        registerTask.setOnSucceeded(e -> {
            String result = registerTask.getValue();
            if (result == null) {
                errorLabel.setStyle("-fx-text-fill: #2ecc71;");
                errorLabel.setText("Success! Logging in...");

                Optional<User> loggedInUser = userService.login(user, pass);
                if (loggedInUser.isPresent()) {
                    SessionManager.setCurrentUser(loggedInUser.get());
                    try {
                        Stage stage = (Stage) registerButton.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + View.MAIN.getFileName()));
                        stage.getScene().setRoot(root);
                        stage.setTitle("Car Management System - Dashboard");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                errorLabel.setStyle("-fx-text-fill: #e74c3c;");
                errorLabel.setText(result);
                registerButton.setDisable(false);
            }
        });

        new Thread(registerTask).start();
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + View.LOGIN.getFileName()));
            stage.getScene().setRoot(root);
            stage.setTitle("Car Management System - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}