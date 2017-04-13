package com.example.tmoon.scs.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmoon on 3/10/17.
 */
@IgnoreExtraProperties
public class Shooter {
    private String name;
    private int shotsTaken;
    private int currentScore;
    Map<String,Integer> scores;

    public Shooter() {
        this.name = "Test";
        this.shotsTaken = 0;
        this.currentScore = 0;
        this.scores = new HashMap<>();
    }

    public Shooter(String name, int shotsTaken, int currentScore, Map<String, Integer> scores) {
        this.name = name;
        this.shotsTaken = shotsTaken;
        this.currentScore = currentScore;
        this.scores = scores;
    }

    public Shooter(String name){
        this.name = name;
        this.shotsTaken = 0;
        this.currentScore = 0;
        this.scores = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShotsTaken() {
        return shotsTaken;
    }

    public void setShotsTaken(int shotsTaken) {
        this.shotsTaken = shotsTaken;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Integer getSingleScore(String id){

        Integer data = new Integer(0);

        try{
            data = scores.get(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public void setSingleScore(String id, Integer score){
        try {
            this.scores.put(id, score);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Shooter{" +
                "name='" + name + '\'' +
                ", shotsTaken=" + shotsTaken +
                ", currentScore=" + currentScore +
                ", scores=" + scores +
                '}';
    }
}
