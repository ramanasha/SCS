package com.example.tmoon.scs;

import java.util.Arrays;

/**
 * Created by tmoon on 3/6/17.
 */

public class ShooterArray {
    private Shooter[] shooters;
    int shooterIndex;

    public ShooterArray(int numberOfShooters) {
        this.shooters = new Shooter[numberOfShooters];
        this.shooterIndex = 0;
    }

    public Shooter[] getShooterArray() {
        return shooters;
    }

    public void setShooterArray(Shooter[] shooters) {
        this.shooters = shooters;
    }

    public Shooter getShooter(int index){
        return this.shooters[index];
    }

    public void setShooter(int index, Shooter shooter){
        this.shooters[index] = shooter;
    }

    public void setShooter(int index, String shootersName){
        this.shooters[index] = new Shooter(shootersName);
    }

    public String getShooterName(){
        return this.shooters[shooterIndex].getName();
    }

    public String getShooterName(int index){
        return this.shooters[index].getName();
    }

    public String getHitNumber(int stationNumber){
        return Integer.toString(this.shooters[shooterIndex].getHits()[stationNumber]);
    }

    public String getTotalHitNumber(){
        return Integer.toString(this.shooters[shooterIndex].totalHits());
    }

    public int getShooterIndex(){
        return this.shooterIndex;
    }

    public void setShooterIndex(int newIndex){
        this.shooterIndex = newIndex;
    }

    public void incrementHitNumber(int stationNumber){
        System.out.println("Incrementing Hit for Station Number: " + stationNumber + " for shooter " + this.shooters[shooterIndex].getName());
        this.shooters[shooterIndex].incrementHits(stationNumber);
    }
    /*
     * Increments the shooterIndex variable if it is less than the size of the shooters array
     * and resets it to zero if its not
     */
    public void incrementIndex(){
        if(shooterIndex < shooters.length-1){
            System.out.println("Incrementing counter to " + shooterIndex);
            shooterIndex++;
        }else{
            System.out.println("Returning counter to 0");
            shooterIndex = 0;
        }
    }


    @Override
    public String toString() {
        return "ShooterArray{" +
                "shooters=" + Arrays.toString(shooters) +
                '}';
    }
}
