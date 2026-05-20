package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Role;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contentArea;
    @FXML private Button usersNavButton;

    @FXML
    public void initialize() {

        handleCarsNav();
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            usersNavButton.setVisible(false);
            usersNavButton.setManaged(false);
        }
    }

    @FXML
    private void handleCarsNav() {
        loadView(View.CARS.getFileName());
    }

    @FXML
    private void handleUsersNav() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
            loadView(View.USERS.getFileName());
        } else {
            System.err.println("Security Block: Unauthorized access attempt to Users view.");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            SessionManager.logout();
            Stage stage = (Stage) contentArea.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + View.LOGIN.getFileName()));

            stage.getScene().setRoot(root);
            stage.setTitle("Car Management System - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + fxmlFile));
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}