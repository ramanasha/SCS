package com.example.tmoon.scs.Models;

import java.util.HashMap;

/**
 * Created by tmoon on 3/14/17.
 */

public class StatisticsShooter {
    HashMap<String, Integer> scores;

    public StatisticsShooter() {
    }

    public StatisticsShooter(HashMap<String, Integer> scores) {
        this.scores = scores;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "StatisticsShooter{" +
                "scores=" + scores +
                '}';
    }
}
