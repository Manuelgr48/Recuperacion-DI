package com.liceolapaz.mgr.jugadores2ev.recu_di.service;

import com.liceolapaz.mgr.jugadores2ev.recu_di.dao.CarDAO;
import com.liceolapaz.mgr.jugadores2ev.recu_di.dao.UserDAO;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Car;
import com.liceolapaz.mgr.jugadores2ev.recu_di.model.User;

import java.util.List;

public class CarService {
    private final CarDAO carDAO = new CarDAO();
    private final UserDAO userDAO = new UserDAO();

    public List<Car> getAllCars() {
        return carDAO.findAll();
    }

    public int getGloballyMostFavoritedCarId() {
        return carDAO.getMostFavoritedCarId();
    }

    public boolean updateUserFavoriteCar(User user, int carId) {
        user.setFavoriteCarId(carId);
        return userDAO.update(user);
    }
}