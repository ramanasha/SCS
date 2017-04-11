package com.example.tmoon.scs.Models;

import com.example.tmoon.scs.Enums.Trap;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("TESTING WITH SHOOTERTOKEN: " + shooterToken);
        int stationNumber=0;
        int shotNumber = 0;
        // Split the shooterToken into the station number and the shot number
        // Eg. "12" should be stationNumber = 1 and shotNumber 2
        if(shooterToken.length() != 0 && shooterToken != null) {
            if (shooterToken.length() == 3) {
                // This means that the stationNumber is the first two numbers
                stationNumber = Integer.parseInt(shooterToken.substring(0, 2)) + 1; // TODO:HACK Need to determine why the station is zero indexed
                System.out.println("Setting stationNumber to: " + stationNumber);

                shotNumber = Integer.parseInt(shooterToken.substring(2, 3));
                System.out.println("Setting shotNumber to: " + shotNumber);
            } else {
                // This means that the stationNumber is the first number
                stationNumber = Integer.parseInt(shooterToken.substring(0, shooterToken.length() - 1)) + 1; // TODO:HACK Need to determine why the station is zero indexed
                System.out.println("Setting stationNumber to: " + stationNumber);

                shotNumber = Integer.parseInt(shooterToken.substring(1, shooterToken.length()));
                System.out.println("Setting shotNumber to: " + shotNumber);
            }
        }else{
            System.out.println("PARSE ERROR 123455sfjdfsjkdfsjkldfs");
        }

        // The label for the HashMap key is "Station" + the stationNumber
        // Eg. Station1
        String stationKey = "Station" + stationNumber;

        String shotKey = "Shot" + shotNumber;

        Station correctStation = this.station.get(stationKey);

        Shot correctShot = correctStation.getSingleShot(shotKey);

        return correctShot.getTrap();
    }

    public String calculateBestTrap(StatisticsShooter statShooter){

        StringBuilder sb = new StringBuilder();

        int[] numbers = new int[8];

        // For each loop through the statShooter HashMap
        for(Map.Entry<String,Integer> entry : statShooter.getScores().entrySet()){
            try {
                String currentTrap = calculateBestTrap(entry.getKey().toString());
                //TODO: Figure out a better way of doing this
                switch(currentTrap){
                    case "A":
                        System.out.println('A');
                        numbers[0]++;
                        break;
                    case "B":
                        System.out.println('B');
                        numbers[1]++;
                        break;
                    case "C":
                        System.out.println('C');
                        numbers[2]++;
                        break;
                    case "D":
                        System.out.println('D');
                        numbers[3]++;
                        break;
                    case "E":
                        System.out.println('E');
                        numbers[4]++;
                        break;
                    case "F":
                        System.out.println('F');
                        numbers[5]++;
                        break;
                    case "G":
                        System.out.println('G');
                        numbers[6]++;
                        break;
                    default:
                        System.out.println('H');
                        numbers[7]++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return largest(numbers);
    }

    private String largest(int[] input) {
        int largest = input[0];
        int index = 0;

        for (int i = 0; i < input.length; i++) {
            System.out.println(input[i]);
            if (input[i] > largest) {
                largest = input[i];
                index = i;
            }

        }
        char temp = (char)(index + 65);
        System.out.println("Largest is: " + temp);
        return String.valueOf(temp);
    }

    // TODO: Remove the reduency between this and the calculateBestTrap method
    public String calculateWorstTrap(StatisticsShooter statShooter){

        StringBuilder sb = new StringBuilder();

        int[] numbers = new int[8];

        // For each loop through the statShooter HashMap
        for(Map.Entry<String,Integer> entry : statShooter.getScores().entrySet()){
            try {
                String currentTrap = calculateBestTrap(entry.getKey().toString());
                //TODO: Figure out a better way of doing this
                switch(currentTrap){
                    case "A":
                        System.out.println('A');
                        numbers[0]++;
                        break;
                    case "B":
                        System.out.println('B');
                        numbers[1]++;
                        break;
                    case "C":
                        System.out.println('C');
                        numbers[2]++;
                        break;
                    case "D":
                        System.out.println('D');
                        numbers[3]++;
                        break;
                    case "E":
                        System.out.println('E');
                        numbers[4]++;
                        break;
                    case "F":
                        System.out.println('F');
                        numbers[5]++;
                        break;
                    case "G":
                        System.out.println('G');
                        numbers[6]++;
                        break;
                    default:
                        System.out.println('H');
                        numbers[7]++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return smallest(numbers);
    }

    private String smallest(int[] input) {
        int smallest = input[0];
        int index = 0;

        for (int i = 0; i < input.length; i++) {
            System.out.println(input[i]);
            if (input[i] < smallest) {
                smallest = input[i];
                index = i;
            }

        }
        char temp = (char)(index + 65);
        System.out.println("Smallest is: " + temp);
        return String.valueOf(temp);
    }
    @Override
    public String toString() {
        return "Course{" +
                "station=" + station +
                '}';
    }
}
