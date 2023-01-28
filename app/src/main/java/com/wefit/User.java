package com.wefit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User{
    private int userWeight;
    private int userHeight;
    private int userAge;
    private int userGoal;
    private String userGender;
    private String userFitness;
    private String userName;

    public User() {
        userWeight = 0;
        userHeight = 0;
        userAge = 0;
        userGender = null;
        userFitness = null;
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

    public User(String userName, int userWeight, int userHeight, int userAge, String userGender, String userFitness) {
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserGoal() {
        return userGoal;
    }

    public void setUserGoal(int userGoal) {
        this.userGoal = userGoal;
    }
}

