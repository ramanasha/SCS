package com.example.tmoon.scs.DAO;

import android.support.annotation.NonNull;

import com.example.tmoon.scs.CallbackInterfaces.SimpleCallback;
import com.example.tmoon.scs.Models.Course;
import com.example.tmoon.scs.Models.Score;
import com.example.tmoon.scs.Models.Shooter;
import com.example.tmoon.scs.Models.Shot;
import com.example.tmoon.scs.Models.Station;
import com.example.tmoon.scs.Models.StatisticsShooter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmoon on 3/9/17.
 */

public class FirebaseDAO {
    private FirebaseDatabase firebaseDatabase;
    private String roundReference;

    public FirebaseDAO(){
        // Initialize the firebase instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_yyyy");
        String currentDateAndTime = simpleDateFormat.format(new Date());
        roundReference = simpleDateFormat.format(new Date());
    }


    /*

    TESTING STUFF

     */

    public boolean updateShooter(Shooter shooter){
        DatabaseReference shooterRef = firebaseDatabase.getReference(roundReference);
        try {
            shooterRef.child(shooter.getName()).setValue(shooter);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void updateScore(Score score){
        DatabaseReference scoreRef = firebaseDatabase.getReference(roundReference);
        try{
            scoreRef.child(String.valueOf(score.getId())).setValue(score);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getShooter(Shooter shooter, @NonNull final SimpleCallback<Shooter> finishedCallback){
        DatabaseReference courseRef = firebaseDatabase.getReference(roundReference).child(shooter.getName());
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This will simple call the callback interface SimpleCallback.java
                try {
                    finishedCallback.callback(dataSnapshot.getValue(Shooter.class));
                    System.out.println("Returning " + dataSnapshot.getValue(Shooter.class));
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

    END TESTING STUFF

     */

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
                maxAllowed = 10;
                resetValue = 0;
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
    * This method returns a integer value for the give n index in the Firebase database.
    * Uses a callback listener to handle returning data from an async thread
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
    public void getTotalValue(String reference, final String indexName, @NonNull final SimpleCallback<Shooter> finishedCallback){
        DatabaseReference courseRef = firebaseDatabase.getReference(reference).child(indexName);
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This will simple call the callback interface SimpleCallback.java
                try {
                    finishedCallback.callback(dataSnapshot.getValue(Shooter.class));
                    System.out.println("Returning " + dataSnapshot.getValue(Shooter.class) + " " + indexName);
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
    public void getCourse(String reference, int stationNumber, int shotNumber , @NonNull final SimpleCallback<Course> finishedCallback){
        DatabaseReference trapRef = firebaseDatabase.getReference("Courses").child("RED");

        trapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Course datashot = " + dataSnapshot.getValue());
                HashMap<String,Station> stations = new HashMap<String, Station>();


                for(DataSnapshot child : dataSnapshot.getChildren()){
                    // Loop through each of the stations

                    HashMap<String,Shot> shots = new HashMap<String, Shot>();

                    for(DataSnapshot subChild : child.getChildren()){
                        // Loop through each of the shots for the station
                        try {
                            System.out.println(subChild.toString());
                            shots.put(subChild.getKey(), subChild.getValue(Shot.class));
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                    Station tempStation = new Station(shots);
                    stations.put(child.getKey(),tempStation);
                }
                Course course = new Course(stations);
                finishedCallback.callback(course);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //TODO: Below this is just for reference and should be removed
    public void getShooterScores(String reference, String shooterName, @NonNull final SimpleCallback<StatisticsShooter> finishedCallback){
        DatabaseReference trapRef = firebaseDatabase.getReference(reference).child(shooterName);

        trapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Course datashot = " + dataSnapshot.getValue());
                HashMap<String,Integer> scores = new HashMap<String, Integer>();


                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(!child.getKey().equalsIgnoreCase("Total") &&
                            !child.getKey().equalsIgnoreCase("CurrentScore") &&
                            child.getValue(Integer.class).equals(1)) {
                        scores.put(child.getKey(), child.getValue(Integer.class));
                    }
                }
                StatisticsShooter statisticsShooter = new StatisticsShooter(scores);
                finishedCallback.callback(statisticsShooter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getStation(String reference, int stationNumber, int shotNumber , @NonNull final SimpleCallback<Station> finishedCallback){
        DatabaseReference trapRef = firebaseDatabase.getReference("Courses").child("RED").child("Station1");

        trapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("------- In getStation -------");
                //   System.out.println("dataSnapshot" + dataSnapshot.toString());

                Station station = new Station();
                HashMap<String, Shot> shots = new HashMap<String, Shot>();
                if(dataSnapshot.getValue() != null) {
                    try {
                        for(DataSnapshot child : dataSnapshot.getChildren()){





                            Shot s = child.getValue(Shot.class);

                            System.out.println("S = " + s.toString());

                            shots.put(child.getKey(),s);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(dataSnapshot.toString() + " did not have data :(");
                }
                station.setShots(shots);
                finishedCallback.callback(station);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getTrap(String reference, int stationNumber, int shotNumber , @NonNull final SimpleCallback<Shot> finishedCallback){
        DatabaseReference trapRef = firebaseDatabase.getReference("Courses").child("RED").child("Station" + stationNumber).child("Shot"+ shotNumber);

        System.out.println("Querying for " + stationNumber+1 + shotNumber);
        trapRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("DATA CHANGE");
             //   System.out.println("dataSnapshot" + dataSnapshot.toString());

                Shot newShot = new Shot();
                if(dataSnapshot.getValue() != null) {
                    try {
                        newShot = dataSnapshot.getValue(Shot.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(dataSnapshot.toString() + " did not have data :(");
                }
                System.out.println("SHOT: " + newShot.toString());

                finishedCallback.callback(newShot);
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
