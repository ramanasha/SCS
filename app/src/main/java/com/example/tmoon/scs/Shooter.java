package com.example.tmoon.scs;

/**
 * Created by tmoon on 3/4/17.
 */

public class Shooter {
    String name;
    int[] hits;

    public Shooter(String name) {
        this.name = name;
        this.hits = new int[10];
    }

    public String getName() {
        return name;
    }

    public int[] getHits() {
        return hits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHits(int[] hits) {
        this.hits = hits;
    }

    /*
     * Add one to the hits array at the parameter index
     */
    public int incrementHits(int index){
        this.hits[index]++;
        return this.hits[index];
    }
}
