package com.liceolapaz.mgr.jugadores2ev.recu_di.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class AppShell {
    private static StackPane contentArea;

    public static void setContentArea(StackPane area) {
        contentArea = area;
    }

    public static void loadView(View view) {
        try {
            FXMLLoader loader = new FXMLLoader(AppShell.class.getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/" + view.getFileName()));
            Parent root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println("Error loading view: " + view.getFileName());
            e.printStackTrace();
        }
    }
}