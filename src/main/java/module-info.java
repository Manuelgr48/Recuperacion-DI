module com.liceolapaz.mgr.jugadores2ev.recu_di {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.liceolapaz.mgr.jugadores2ev.recu_di to javafx.fxml;
    exports com.liceolapaz.mgr.jugadores2ev.recu_di;
    exports com.liceolapaz.mgr.jugadores2ev.recu_di.controller;
    opens com.liceolapaz.mgr.jugadores2ev.recu_di.controller to javafx.fxml;
}