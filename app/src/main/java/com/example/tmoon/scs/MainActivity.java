package com.example.tmoon.scs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    //TODO: Replace this when the Settings Activiy is done
    Shooter[] shooters = new Shooter[4];
    int shooterIndex = 0;

    int roundNumber = 0;

    Stations stations = new Stations(0);

    TextView shooterName;
    TextView hitCounter;
    TextView stationCounter;

    TextView tblShooter1,tblShooter2, tblShooter3, tblShooter4;
    TextView tblShooter1Total, tblShooter2Total, tblShooter3Total, tblShooter4Total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the shooters array for each shooter
        // TODO: Replace this when the Settings Activity is done
        shooters[0] = new Shooter("Tyler");
        shooters[1] = new Shooter("Dad");
        shooters[2] = new Shooter("Granddad");
        shooters[3] = new Shooter("Dalton");

        // Initialize the name label with the first shooters name
        shooterName = (TextView)findViewById(R.id.textView2);
        shooterName.setText(shooters[shooterIndex].getName());

        // Initialize the hit counter
        hitCounter = (TextView)findViewById(R.id.hitCounter);
        //TODO: Place this into a method so it doesn't have to be repeated so much
        hitCounter.setText(Integer.toString(shooters[shooterIndex].getHits()[stations.getStationNumber()]));

        // Initialize the station label
        stationCounter = (TextView)findViewById(R.id.stationNumber);
        stationCounter.setText(Integer.toString(stations.getStationNumber()));

        // Total table information
        tblShooter1 = (TextView)findViewById(R.id.tblShooter1);
        tblShooter1.setText(shooters[0].getName());
        tblShooter2 = (TextView)findViewById(R.id.tblShooter2);
        tblShooter2.setText(shooters[1].getName());
        tblShooter3 = (TextView)findViewById(R.id.tblShooter3);
        tblShooter3.setText(shooters[2].getName());
        tblShooter4 = (TextView)findViewById(R.id.tblShooter4);
        tblShooter4.setText(shooters[3].getName());

        tblShooter1Total = (TextView)findViewById(R.id.tblShooter1Total);
        tblShooter1Total.setText("0");
        tblShooter2Total = (TextView)findViewById(R.id.tblShooter2Total);
        tblShooter2Total.setText("0");
        tblShooter3Total = (TextView)findViewById(R.id.tblShooter3Total);
        tblShooter3Total.setText("0");
        tblShooter4Total = (TextView)findViewById(R.id.tblShooter4Total);
        tblShooter4Total.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    /*
     * This method is fired when the Hit button is pressed. It Increments the hit counter for the
     * current shooter at the current station number index.
     */
    public void shooterHitPressed(View view){
        hitCounter.setText(Integer.toString(shooters[shooterIndex].incrementHits(stations.getStationNumber())));
                                                                // Increment the
                                                                // number of hits at the
                                                                // index stationNumber and set the counter text view
                                                                // to the incremented value

    }
    /*
     * This method is fired when the Next Stations button is pressed. It increments the station
     * counter and updates the text fields
     */
    public void nextStationButtonPressed(MenuItem item){
        stations.incrementStationNumber();
        stationCounter.setText(Integer.toString(stations.getStationNumber()));
        hitCounter.setText(Integer.toString(shooters[shooterIndex].getHits()[stations.getStationNumber()]));
    }

    public void nextRoundButtonPressed(MenuItem item){
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Do you want to go on to the next round?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(MainActivity.this, "New Round!", Toast.LENGTH_SHORT).show();
                        roundNumber++;
                        shooterIndex = 0;
                        // TODO: Replace this when the Settings Activity is done
                        shooters[0] = new Shooter("Tyler");
                        shooters[1] = new Shooter("Dad");
                        shooters[2] = new Shooter("Granddad");
                        shooters[3] = new Shooter("Dalton");

                        stations.setStationNumber(0);

                        hitCounter.setText("0");
                        stationCounter.setText("0");
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    /*
     * This method is fired when the Next Shooter button is pressed. It increments the shooter
     * index counter and then sets the text of the shooterName text view to the next shooters
     * name.
     */
    public void nextShooterButtonPressed(View view){
        // Set the total for that shooter
        setShooterTotal();

        // Save the data to the Firebase database
        // TODO: Possibly dont save every time this is pressed if it slows it down or something
        saveData();

        // Increment the index of the shooter and if it is greater than the number of shooters then
        // go back to the beginning
        incrementShooterIndex();

        // Set the new shooters name
        shooterName.setText(shooters[shooterIndex].getName());

        // Set the hit counter
        hitCounter.setText(Integer.toString(shooters[shooterIndex].getHits()[stations.getStationNumber()]));


    }
    /*
     * Save the station hits and update the total in the Firebase database.
     */
    private void saveData(){
        try {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Round " + Integer.toString(roundNumber));
            myRef.child(shooters[shooterIndex].getName())
                    .child("Station " + stations.getStationNumber())
                    .child("Hits").setValue(shooters[shooterIndex].getHits()[stations.getStationNumber()]);

            DatabaseReference totalRef = database.getReference("Round " + Integer.toString(roundNumber));
            myRef.child(shooters[shooterIndex].getName()).child("Total").setValue(shooters[shooterIndex].totalHits());

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /*
     * Increments the shooterIndex variable if it is less than the size of the shooters array
     * and resets it to zero if its not
     */
    private void incrementShooterIndex(){
        if(shooterIndex < shooters.length-1){
            System.out.println("Incrementing counter to " + shooterIndex);
            shooterIndex++;
        }else{
            System.out.println("Returning counter to 0");
            shooterIndex = 0;
        }
    }

    private void setShooterTotal(){
        switch(shooterIndex){
            case 0:
                tblShooter1Total.setText(Integer.toString(shooters[shooterIndex].totalHits()));
                break;
            case 1:
                tblShooter2Total.setText(Integer.toString(shooters[shooterIndex].totalHits()));
                break;
            case 2:
                tblShooter3Total.setText(Integer.toString(shooters[shooterIndex].totalHits()));
                break;
            case 3:
                tblShooter4Total.setText(Integer.toString(shooters[shooterIndex].totalHits()));
                break;
        }
    }
}
