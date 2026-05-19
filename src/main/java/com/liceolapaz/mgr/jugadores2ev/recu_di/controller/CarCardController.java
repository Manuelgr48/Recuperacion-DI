package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class CarCardController {
    @FXML private VBox cardRoot;
    @FXML private ImageView carImageView;
    @FXML private Label brandModelLabel;
    @FXML private Label specsLabel;
    @FXML private Label priceLabel;
    @FXML private Label statusBadge;
    @FXML private Button favoriteButton;

    public void setCarData(Car car, boolean isUserFav, boolean isGlobalFav, Consumer<Car> callback) {
        brandModelLabel.setText(car.getBrand() + " " + car.getModel());
        specsLabel.setText(car.getHorsepower() + " HP");
        priceLabel.setText("$" + car.getPrice());
        try { carImageView.setImage(new Image(car.getImageUrl(), true)); } catch (Exception e) {}

        if (isUserFav) {
            cardRoot.setStyle("-fx-background-color: #2f3542; -fx-border-color: #2ecc71; -fx-border-width: 3px; -fx-border-radius: 10px;");
            statusBadge.setText("YOUR FAVORITE");
            favoriteButton.setDisable(true);
        } else if (isGlobalFav) {
            cardRoot.setStyle("-fx-background-color: #2f3542; -fx-border-color: #f1c40f; -fx-border-width: 3px; -fx-border-radius: 10px;");
            statusBadge.setText("GLOBAL FAVORITE");
        }

        favoriteButton.setOnAction(e -> {
            e.consume();
            callback.accept(car);
        });
    }
}