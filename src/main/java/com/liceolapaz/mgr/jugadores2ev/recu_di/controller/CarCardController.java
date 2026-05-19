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

    private Car car;
    private Consumer<Car> onFavoriteCallback;

    public void setCarData(Car car, boolean isUserFavorite, boolean isGlobalFavorite, Consumer<Car> callback) {
        this.car = car;
        this.onFavoriteCallback = callback;

        brandModelLabel.setText(car.getBrand() + " " + car.getModel());
        specsLabel.setText(car.getHorsepower() + " HP");
        priceLabel.setText("$" + car.getPrice());

        try {
            carImageView.setImage(new Image(car.getImageUrl(), true));
        } catch (Exception e) {
            System.err.println("Could not load image: " + car.getImageUrl());
        }
        if (isUserFavorite) {
            cardRoot.setStyle("-fx-background-color: #2f3542; -fx-border-color: #2ecc71; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            statusBadge.setText("YOUR FAVORITE");
            statusBadge.setTextFill(javafx.scene.paint.Color.web("#2ecc71"));
            favoriteButton.setDisable(true);
            favoriteButton.setText("Already Favorite");
        } else if (isGlobalFavorite) {
            cardRoot.setStyle("-fx-background-color: #2f3542; -fx-border-color: #f1c40f; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            statusBadge.setText("GLOBAL FAVORITE");
            statusBadge.setTextFill(javafx.scene.paint.Color.web("#f1c40f"));
        } else {
            statusBadge.setText("");
        }
    }

    @FXML
    private void handleFavoriteClick() {
        if (onFavoriteCallback != null) {
            onFavoriteCallback.accept(car);
        }
    }
}
