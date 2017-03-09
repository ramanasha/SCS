package com.example.tmoon.scs.Models;

import com.example.tmoon.scs.Enums.Trap;

/**
 * Created by tmoon on 3/9/17.
 */

public class Shot {
    private Trap trap;
    private boolean hit;
    private boolean miss;

    public Shot() {
    }

    public Shot(Trap trap) {
        this.trap = trap;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setHit() { this.hit = true; }

    public boolean isMiss() {
        return miss;
    }

    public void setMiss(boolean miss) {
        this.miss = miss;
    }

    public void setMiss() { this.miss = true; }

    public Trap getTrap() {
        return trap;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "trap=" + trap +
                ", hit=" + hit +
                ", miss=" + miss +
                '}';
    }
}
