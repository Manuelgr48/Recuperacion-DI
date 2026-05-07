package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.util.AppShell;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainController {

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