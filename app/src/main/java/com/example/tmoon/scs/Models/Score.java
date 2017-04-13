package com.example.tmoon.scs.Models;

import java.util.Map;

/**
 * Created by tmoon on 4/12/17.
 */

public class Score {
    Map<String,Integer> scores;
    int id;

    public Score() {
    }

    public Score(Map<String, Integer> scores, int id) {
        this.scores = scores;
        this.id = id;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public void addScore(String name, Integer score){
        scores.put(name,score);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scores=" + scores +
                ", id=" + id +
                '}';
    }
}
