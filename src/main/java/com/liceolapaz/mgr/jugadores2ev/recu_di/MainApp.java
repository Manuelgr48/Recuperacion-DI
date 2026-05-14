package com.liceolapaz.mgr.jugadores2ev.recu_di;

import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(View.LOGIN.getFileName()));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Car Management System - Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}