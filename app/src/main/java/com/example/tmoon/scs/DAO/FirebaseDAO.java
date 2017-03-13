package com.example.tmoon.scs.DAO;

import android.support.annotation.NonNull;

import com.example.tmoon.scs.CallbackInterfaces.SimpleCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tmoon on 3/9/17.
 */

public class FirebaseDAO {
    private FirebaseDatabase firebaseDatabase;
    //TODO: Take this out
    private String roundReference;

    public FirebaseDAO(){
        firebaseDatabase = FirebaseDatabase.getInstance();
    }


// Shooter Index
    public int incrementShooterIndex(String reference, String indexName, int currentValue){
        int maxAllowed = 0; // Largest allowed value
        int resetValue = 0; // Value to reset to after the max value is reached

        // Set the maxAllowed and resetValue based on the value being incremented
        switch(indexName){
            case "ShooterIndex":
                maxAllowed = 3;
                resetValue = 0;
                break;
            case "ShotNumber":
                maxAllowed = 5;
                resetValue = 1;
                break;
            case "StationNumber":
                maxAllowed = 14;
                resetValue = 1;
        }


        if(currentValue < maxAllowed){
             currentValue++;
        }else{
            currentValue = resetValue;
        }

        DatabaseReference isiRef = firebaseDatabase.getReference(reference);
        isiRef.child(indexName).setValue(currentValue);
        System.out.println("Setting " + indexName + " to " + currentValue);

        return currentValue;
    }
    public int resetValue(String reference, String indexName){
        // Set the maxAllowed and resetValue based on the value being incremented
        int value;
        switch(indexName){
            case "ShooterIndex":
                value = 0;
                break;
            case "ShotNumber":
                value = 1;
                break;
            case "StationNumber":
                value = 1;
                break;
            default:
                value = 0;
        }
        DatabaseReference isiRef = firebaseDatabase.getReference(reference);
        isiRef.child(indexName).setValue(value);
        System.out.println("Resetting " + indexName + " to " + value);

        return value;
    }

    public void saveAShot(String reference, String shooterName, String id, int result){
        DatabaseReference shotRef = firebaseDatabase.getReference(reference);
        shotRef.child(shooterName).child(id).setValue(result);
    }

    /*
    * Use a callback listener to handle returning data from an async thread
    */
    public void getIndexValue(String reference, final String indexName, @NonNull final SimpleCallback<Integer> finishedCallback){
        DatabaseReference courseRef = firebaseDatabase.getReference(reference).child(indexName);
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This will simple call the callback interface SimpleCallback.java
                try {
                    finishedCallback.callback(dataSnapshot.getValue(Integer.class));
                    System.out.println("Returning " + dataSnapshot.getValue(Integer.class) + " " + indexName);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: Add onCancelled stuff
            }
        });
    }
    /*
   * Use a callback listener to handle returning data from an async thread
   */
    public void getTotalValue(String reference, final String indexName, @NonNull final SimpleCallback<Integer> finishedCallback){
        DatabaseReference courseRef = firebaseDatabase.getReference(reference).child(indexName).child("Total");
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This will simple call the callback interface SimpleCallback.java
                try {
                    finishedCallback.callback(dataSnapshot.getValue(Integer.class));
                    System.out.println("Returning " + dataSnapshot.getValue(Integer.class) + " " + indexName);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: Add onCancelled stuff
            }
        });
    }
    //TODO: Below this is just for reference and should be removed

    public void getTrap(String reference, int stationNumber, int shotNumber , @NonNull final SimpleCallback<String> finishedCallback){
        DatabaseReference trapRef = firebaseDatabase.getReference("Courses").child("Station"+stationNumber).child("Shot"+shotNumber);

        System.out.println("Querying for " + stationNumber + shotNumber);
        trapRef.orderByChild("ID").equalTo(stationNumber + shotNumber).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("FOUND IT");
                finishedCallback.callback(dataSnapshot.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    /*
     * Use a callback listener to handle returning data from an async thread
     */
    public void getCourseData(@NonNull final SimpleCallback<JSONObject> finishedCallback){
        DatabaseReference courseRef = firebaseDatabase.getReference("Courses");
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This will simple call the callback interface SimpleCallback.java
                // Pass back a JSONObject full of all the courses
                try {
                    finishedCallback.callback(new JSONObject(dataSnapshot.toString().substring(38)));
                }catch(JSONException je){
                    je.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: Add onCancelled stuff
            }
        });
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
