package com.example.tmoon.scs.DAO;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by tmoon on 3/9/17.
 */

public class FirebaseDAO {
    private FirebaseDatabase firebaseDatabase;
    private String roundReference;

    public FirebaseDAO(){
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public String getRoundReference() {
        return roundReference;
    }

    public void setRoundReference(String roundReference) {
        this.roundReference = "Round " + roundReference;
    }

    public void updateHits(String shooterName, String stationNumber, String hitValue){
        DatabaseReference hitReference = firebaseDatabase.getReference(roundReference);
        hitReference.child(shooterName)
                .child("Station " + stationNumber)
                .child("Hits")
                .setValue(hitValue);
    }

    public void updateTotal(String shooterName, String totalHitNumber){
        DatabaseReference totalReference = firebaseDatabase.getReference(roundReference);
        totalReference
                .child(shooterName)
                .child("Total")
                .setValue(totalHitNumber);
    }

    public void testReadingData(){
        DatabaseReference readReference = firebaseDatabase.getReference(roundReference);
        ValueEventListener hitListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.child("Dad").child("Station 0").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        readReference.addListenerForSingleValueEvent(hitListener);
    }
}
