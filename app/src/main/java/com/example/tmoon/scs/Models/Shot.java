package com.example.tmoon.scs.Models;

/**
 * Created by tmoon on 3/9/17.
 */

public class Shot {
    private char tower;
    private boolean hit;
    private boolean miss;

    public Shot() {
    }

    public Shot(char tower) {
        this.tower = tower;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isMiss() {
        return miss;
    }

    public void setMiss(boolean miss) {
        this.miss = miss;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "tower=" + tower +
                ", hit=" + hit +
                ", miss=" + miss +
                '}';
    }
}
