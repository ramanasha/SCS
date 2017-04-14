package com.example.tmoon.scs.Models;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmoon on 4/13/17.
 */

public class TableGroup {
    Map<Integer, TextView> textViews;

    public TableGroup() {
    }

    public TableGroup(Map<Integer, TextView> textViews) {
        this.textViews = textViews;
    }
    public TableGroup(TextView[] textViews){
        this.textViews = new HashMap<>();
        for(int i = 0; i < textViews.length; i++){
            if(textViews[i] != null)
                this.textViews.put(i,textViews[i]);
        }
    }

    public Map<Integer, TextView> getTextViews() {
        return textViews;
    }

    public void setTextViews(Map<Integer, TextView> textViews) {
        this.textViews = textViews;
    }

    public TextView getSingleTextView(Integer key){
        return this.textViews.get(key);
    }

    public void setSingleTextView(Integer key, TextView textView){
        this.textViews.put(key,textView);
    }

    public void setText(Shooter shooter) {
        try {
            textViews.get(0).setText(shooter.getName()); // Set the name label to the shooters name
            textViews.get(1).setText(String.valueOf(shooter.getCurrentScore()));
            textViews.get(2).setText(String.valueOf(shooter.getShotsTaken()));
            textViews.get(3).setText(String.format("%.2f", ((double) shooter.getCurrentScore() / (double) shooter.getShotsTaken())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
