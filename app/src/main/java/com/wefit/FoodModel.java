package com.wefit;

public class FoodModel {
    private String day;
    private int calAmount;

    public FoodModel(String day, int calAmount) {
        this.day = day;
        this.calAmount = calAmount;
    }

    public FoodModel() {
    }

    public String getDay() {
        return day;
    }

    public int getCalAmount() {
        return calAmount;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setCalAmount(int calAmount) {
        this.calAmount = calAmount;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "day='" + day + '\'' +
                ", calAmount=" + calAmount +
                '}';
    }
}
