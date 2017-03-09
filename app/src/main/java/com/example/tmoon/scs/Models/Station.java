package com.example.tmoon.scs.Models;

import com.example.tmoon.scs.Enums.Trap;

import java.util.Arrays;

/**
 * Created by tmoon on 3/9/17.
 */

public class Station {
    private Shot[] shots;
    private int currentShotNumber;
    private int stationNumber;
    private int stationHitTotal;

    public Station(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Shot[] getShots() {
        return shots;
    }

    public void setShots(Shot[] shots) {
        this.shots = shots;
    }

    public void setShots(Trap[] traps){
        if(this.shots.length == traps.length) {
            for (int i = 0; i < this.shots.length; i++) {
                this.shots[i].setTrap(traps[i]);
            }
        }
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public int getStationHitTotal() {
        return stationHitTotal;
    }

    public void setStationHitTotal(int stationHitTotal) {
        this.stationHitTotal = stationHitTotal;
    }

    public void markAsHit(){
        this.shots[currentShotNumber].setHit();
        // After recording a hit move the index to the next shot
        incrementShotNumber();

        // TODO: Replace this with updating the database and reading from it;
        // Add one to the stationHitTotal
        stationHitTotal++;
    }

    public void markAsMiss(){
        this.shots[currentShotNumber].setMiss();
        // After recording a miss move the index to the next shot
        incrementShotNumber();
    }

    //TODO: This may need to be private
    public void incrementShotNumber(){
        // If the currentShotNumber is less than the number of shots the increment it by one. Otherwise
        // reset to the first shot at index 0;
        if(this.currentShotNumber < shots.length){
            this.currentShotNumber++;
        }else{
            this.currentShotNumber = 0;
        }
    }
    @Override
    public String toString() {
        return "Station{" +
                "shots=" + Arrays.toString(shots) +
                ", stationNumber=" + stationNumber +
                ", hitNumber=" + stationHitTotal +
                '}';
    }
}
