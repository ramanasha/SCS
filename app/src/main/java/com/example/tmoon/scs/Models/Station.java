package com.example.tmoon.scs.Models;

import java.util.Arrays;

/**
 * Created by tmoon on 3/9/17.
 */

public class Station {
    private Shot[] shots;
    private int stationNumber;
    private int hitNumber;

    public Station(int stationNumber, char[] towers) {
        if(towers.length < 5){
            System.out.println("ERROR - towers array is too short");
        }

        this.shots = new Shot[5];

        for(int i = 0; i < 5; i++){
            this.shots[i] = new Shot(towers[i]);
        }

        this.stationNumber = stationNumber;
    }

    public Shot[] getShots() {
        return shots;
    }

    public void setShots(Shot[] shots) {
        this.shots = shots;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public int getHitNumber() {
        return hitNumber;
    }

    public void setHitNumber(int hitNumber) {
        this.hitNumber = hitNumber;
    }

    @Override
    public String toString() {
        return "Station{" +
                "shots=" + Arrays.toString(shots) +
                ", stationNumber=" + stationNumber +
                ", hitNumber=" + hitNumber +
                '}';
    }
}
