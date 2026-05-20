package com.liceolapaz.mgr.jugadores2ev.recu_di;

import com.liceolapaz.mgr.jugadores2ev.recu_di.util.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(View.LOGIN.getFileName()));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Car Management System - Login");

        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/liceolapaz/mgr/jugadores2ev/recu_di/carro-deportivo.png")));;
        } catch (Exception e) {
            System.err.println("Icon not found. Please place icon.png in the resources root folder.");
        }

        stage.setScene(scene);

        stage.setMinWidth(900);
        stage.setMinHeight(650);
        stage.setMaximized(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}