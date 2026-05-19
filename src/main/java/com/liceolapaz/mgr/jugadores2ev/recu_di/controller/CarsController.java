package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Car;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;
import com.liceolapaz.mgr.jugadores2ev.recu_di.service.CarService;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CarsController {

    @FXML private TilePane carsContainer;
    @FXML private VBox detailPanel;
    @FXML private ImageView detailImageView;
    @FXML private Label detailBrandModelLabel;
    @FXML private Label detailSpecsLabel;
    @FXML private Label detailPriceLabel;

    private final CarService carService = new CarService();

    @FXML
    public void initialize() {
        loadCars();
    }

    private void loadCars() {
        carsContainer.getChildren().clear();
        Task<Void> loadTask = new Task<>() {
            List<Car> carList;
            int globalFavId;
            @Override protected Void call() {
                carList = carService.getAllCars();
                globalFavId = carService.getGloballyMostFavoritedCarId();
                return null;
            }
            @Override protected void succeeded() {
                User current = SessionManager.getCurrentUser();
                for (Car car : carList) {
                    addCarCard(car, (current != null && current.getFavoriteCarId() == car.getId()), (globalFavId == car.getId()));
                }
            }
        };
        new Thread(loadTask).start();
    }

    private void addCarCard(Car car, boolean isUserFav, boolean isGlobalFav) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/car_card.fxml"));
            VBox card = loader.load();
            CarCardController controller = loader.getController();

            controller.setCarData(car, isUserFav, isGlobalFav, selected -> {
                carService.updateUserFavoriteCar(SessionManager.getCurrentUser(), selected.getId());
                loadCars();
            });


            card.setOnMouseClicked(event -> showCarDetails(car));
            card.setStyle(card.getStyle() + "-fx-cursor: hand;");

            carsContainer.getChildren().add(card);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void showCarDetails(Car car) {
        detailBrandModelLabel.setText(car.getBrand() + " " + car.getModel());
        detailSpecsLabel.setText("Power: " + car.getHorsepower() + " HP");
        detailPriceLabel.setText(String.format("Price: $%,.2f", car.getPrice()));
        try {
            detailImageView.setImage(new Image(car.getImageUrl(), true));
        } catch (Exception e) {}

        detailPanel.setVisible(true);
        detailPanel.setManaged(true);
    }

    @FXML
    private void closeDetails() {
        detailPanel.setVisible(false);
        detailPanel.setManaged(false);
    }
}