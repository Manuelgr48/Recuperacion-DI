package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.util.AppShell;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainController {


    @FXML
    private void handleLogout() {
        try {
            com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager.logout();

            Stage stage = (Stage) contentArea.getScene().getWindow();
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + com.liceolapaz.mgr.jugadores2ev.recu_di.util.View.LOGIN.getFileName()));
            javafx.scene.Scene scene = new javafx.scene.Scene(root, 1000, 700);
            stage.setTitle("Car Management System - Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        AppShell.setContentArea(contentArea);
        AppShell.loadView(View.CARS);
    }

    @FXML
    private void handleCarsNav() {
        AppShell.loadView(View.CARS);
    }

    @FXML
    private void handleUsersNav() {
        AppShell.loadView(View.USERS);
    }

}