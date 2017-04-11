package com.example.tmoon.scs.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by tmoon on 3/10/17.
 */
@IgnoreExtraProperties
public class Shooter {
    private String name;
    private int total;
    private int currentScore;

    public Shooter() {
        this.name="undefined";
        this.total = 0;
    }

    public Shooter(String name) {
        this.name = name;
        this.total = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public String toString() {
        return "Shooter{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", currentScore=" + currentScore +
                '}';
    }
}
