package com.example.tmoon.scs.Models;

/**
 * Created by tmoon on 3/10/17.
 */

public class Shooter {
    private String name;
    private int score;
    private int currentScore;

    public Shooter() {
        this.name="undefined";
        this.score = 0;
    }

    public Shooter(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
                ", score=" + score +
                '}';
    }
}
