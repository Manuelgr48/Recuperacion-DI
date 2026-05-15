package com.liceolapaz.mgr.jugadores2ev.recu_di.model;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int horsepower;
    private double price;
    private String imageUrl;

    public Car(int id, String brand, String model, int horsepower, double price, String imageUrl) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.horsepower = horsepower;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getHorsepower() { return horsepower; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
