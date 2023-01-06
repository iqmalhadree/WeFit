package com.wefit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User{
    private int userWeight;
    private int userHeight;
    private int userAge;
    private String userGender;
    private String userFitness;

    public User() {
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserFitness() {
        return userFitness;
    }

    public void setUserFitness(String userFitness) {
        this.userFitness = userFitness;
    }

    @Override
    public String toString() {
        return "User{" +
                "userWeight=" + userWeight +
                ", userHeight=" + userHeight +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                ", userFitness='" + userFitness + '\'' +
                '}';
    }

    public User(int userWeight, int userHeight, int userAge, String userGender, String userFitness) {
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userFitness = userFitness;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setGender(String userGender) {
        this.userGender = userGender;
    }
}

