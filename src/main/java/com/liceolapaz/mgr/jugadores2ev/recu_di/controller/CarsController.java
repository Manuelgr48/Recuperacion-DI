package com.liceolapaz.mgr.jugadores2ev.recu_di.controller;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Car;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;
import com.liceolapaz.mgr.jugadores2ev.recu_di.service.CarService;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.SessionManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CarsController {

    @FXML private TilePane carsContainer;
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
            User currentUser = SessionManager.getCurrentUser();

            @Override
            protected Void call() {
                carList = carService.getAllCars();
                globalFavId = carService.getGloballyMostFavoritedCarId();
                return null;
            }

            @Override
            protected void succeeded() {
                for (Car car : carList) {
                    boolean isUserFav = (currentUser != null && currentUser.getFavoriteCarId() == car.getId());
                    boolean isGlobalFav = (globalFavId == car.getId());
                    addCarCard(car, isUserFav, isGlobalFav);
                }
            }
        };

        new Thread(loadTask).start();
    }

    private void addCarCard(Car car, boolean isUserFav, boolean isGlobalFav) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/liceolapaz/mgr/jugadores2ev/recu_di/car_card.fxml"));
            VBox cardNode = loader.load();
            CarCardController controller = loader.getController();

            controller.setCarData(car, isUserFav, isGlobalFav, selectedCar -> handleFavoriteSelection(selectedCar));

            carsContainer.getChildren().add(cardNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleFavoriteSelection(Car car) {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            Task<Boolean> updateTask = new Task<>() {
                @Override
                protected Boolean call() {
                    return carService.updateUserFavoriteCar(currentUser, car.getId());
                }
                @Override
                protected void succeeded() {
                    if (getValue()) {
                        loadCars();
                    }
                }
            };
            new Thread(updateTask).start();
        }
    }
}