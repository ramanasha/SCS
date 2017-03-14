package com.example.tmoon.scs.Models;

import java.util.HashMap;

/**
 * Created by tmoon on 3/13/17.
 */

public class Station {
    HashMap<String,Shot> shots;

    public Station(){
        shots = new HashMap<String, Shot>();
    }

    public Station(HashMap<String, Shot> shots) {
        this.shots = shots;
    }

    public HashMap<String, Shot> getShots() {
        return shots;
    }

    public void setShots(HashMap<String, Shot> shots) {
        this.shots = shots;
    }

    public Shot getSingleShot(String key){
        return this.shots.get(key);
    }

    @Override
    public String toString() {
        return "Station{" +
                "shots=" + shots +
                '}';
    }
}
