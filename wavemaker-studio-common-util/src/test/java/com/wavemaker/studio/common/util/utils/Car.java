package com.wavemaker.studio.common.util.utils;

/**
 * Created by prithvi on 9/10/15.
 */
public class Car {
    String brand;
    String model;
    int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        return !(model != null ? !model.equals(car.model) : car.model != null);

    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }
}
