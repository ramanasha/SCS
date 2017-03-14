package com.example.tmoon.scs.Models;

/**
 * Created by tmoon on 3/13/17.
 */

public class Shot {
    private int ID;
    private String type;
    private String trap;

    public Shot(){
        this.ID = 1;
        this.type = "Single";
        this.trap = "A";
    }
    public Shot(int ID, String Type, String Trap) {
        this.ID = ID;
        this.type = Type;
        this.trap = Trap;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrap() {
        return trap;
    }

    public void setTrap(String trap) {
        this.trap = trap;
    }


    @Override
    public String toString() {
        return "Shot{" +
                "ID=" + ID +
                ", type='" + type + '\'' +
                ", trap='" + trap + '\'' +
                '}';
    }
}
