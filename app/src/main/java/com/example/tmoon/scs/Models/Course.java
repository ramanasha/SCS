package com.example.tmoon.scs.Models;

import com.example.tmoon.scs.Enums.Trap;

import java.util.HashMap;

/**
 * Created by tmoon on 3/14/17.
 */

public class Course {
    HashMap<String, Station> station;

    public Course(){
        this.station = new HashMap<String,Station>();
    }
    public Course(HashMap<String, Station> station) {
        this.station = station;
    }

    public HashMap<String, Station> getStation() {
        return station;
    }

    public void setStation(HashMap<String, Station> station) {
        this.station = station;
    }

    public String calculateBestTrap(String shooterToken){
        int stationNumber, shotNumber;
        // Split the shooterToken into the station number and the shot number
        // Eg. "12" should be stationNumber = 1 and shotNumber 2
        if(shooterToken.length() == 3){
            // This means that the stationNumber is the first two numbers
            stationNumber = Integer.parseInt(shooterToken.substring(0,2));
            System.out.println("Setting stationNumber to: " + stationNumber);

            shotNumber = Integer.parseInt(shooterToken.substring(2,3));
            System.out.println("Setting shotNumber to: " + shotNumber);
        }else{
            // This means that the stationNumber is the first number
            stationNumber = Integer.parseInt(shooterToken.substring(0,shooterToken.length()-1));
            System.out.println("Setting stationNumber to: " + stationNumber);

            shotNumber = Integer.parseInt(shooterToken.substring(1,shooterToken.length()));
            System.out.println("Setting shotNumber to: " + shotNumber);
        }

        // The label for the HashMap key is "Station" + the stationNumber
        // Eg. Station1
        String stationKey = "Station" + stationNumber;

        String shotKey = "Shot" + shotNumber;

        Station correctStation = this.station.get(stationKey);

        Shot correctShot = correctStation.getSingleShot(shotKey);

        return correctShot.getTrap();
    }

    @Override
    public String toString() {
        return "Course{" +
                "station=" + station +
                '}';
    }
}
