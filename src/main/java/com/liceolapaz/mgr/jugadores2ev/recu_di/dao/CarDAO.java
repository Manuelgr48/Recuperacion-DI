package com.liceolapaz.mgr.jugadores2ev.recu_di.dao;

import com.liceolapaz.mgr.jugadores2ev.recu_di.model.Car;
import com.liceolapaz.mgr.jugadores2ev.recu_di.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("horsepower"),
                        rs.getDouble("price"),
                        rs.getString("image_url")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public int getMostFavoritedCarId() {
        String sql = "SELECT favorite_car_id, COUNT(*) as count FROM users WHERE favorite_car_id IS NOT NULL GROUP BY favorite_car_id ORDER BY count DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("favorite_car_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}