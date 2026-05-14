package com.liceolapaz.mgr.jugadores2ev.recu_di.util;

public enum View {
    MAIN("main_view.fxml"),
    CARS("cars_view.fxml"),
    USERS("users_view.fxml"),
    LOGIN("login_view.fxml"),
    REGISTER("register_view.fxml");

    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}