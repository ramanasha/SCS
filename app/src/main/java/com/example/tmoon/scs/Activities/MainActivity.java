package com.example.tmoon.scs.Activities;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.tmoon.scs.CallbackInterfaces.SimpleCallback;
import com.example.tmoon.scs.DAO.FirebaseDAO;
import com.example.tmoon.scs.Models.Shooter;
import com.example.tmoon.scs.Models.Station;
import com.example.tmoon.scs.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //TODO: Replace this when the Settings Activity is done
    Shooter[] shooters = new Shooter[4];
    int shooterIndex;
    String[] shooterNames = {"Tyler","Dad","Granddad","Dalton"};

    int stationNumber;
    int shotNumber;

    //TODO: Make this actually change instead of being hardcoded
    String dateReference = "";

    TextView shooterName;
    TextView hitCounter;
    TextView stationCounter;

    TextView tblShooter1,tblShooter2, tblShooter3, tblShooter4;
    TextView tblShooter1Total, tblShooter2Total, tblShooter3Total, tblShooter4Total;

    private FirebaseDAO fDAO = new FirebaseDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup dateReference to the current date
        // TODO: Allow for a second or third round
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_yyyy");
        String currentDateAndTime = simpleDateFormat.format(new Date());
        dateReference = simpleDateFormat.format(new Date());
        System.out.println("~~~~~ USING REFERENCE " + currentDateAndTime + " ~~~~~~~");


        // Counter to see when all the asyncs finish and then refresh the layout with the new values
        final int asyncCounter = 0;


        // Set up the shooters array for each shooter
        // TODO: Replace this when the Settings Activity is done
        for(int i = 0; i < shooters.length; i++){
            shooters[i] = new Shooter(shooterNames[i]);
            System.out.println("Created new shooter " + shooters[i].toString());
        }

        shooterName = (TextView)findViewById(R.id.textView2);
        // Update when the shooter index is changed
        fDAO.getIndexValue(dateReference,"ShooterIndex", new SimpleCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                // Shooter
                shooterName.setText(shooters[shooterIndex].getName());
            }
        });

        hitCounter = (TextView)findViewById(R.id.hitCounter);
        // Update when the shot number is changed
        fDAO.getIndexValue(dateReference, "ShotNumber", new SimpleCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                // TODO: Make a shot label

                hitCounter.setText(String.valueOf(data));
            }
        });

        stationCounter = (TextView)findViewById(R.id.stationNumber);
        // Update when the station number is changed
        fDAO.getIndexValue(dateReference, "StationNumber", new SimpleCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                stationCounter.setText(String.valueOf(stationNumber));
            }
        });

        // Total table information
        // TODO: Figure out how to not hardcode these
        for(int i = 0; i < shooters.length; i++){
            // Update when the station number is changed
            final Shooter currentShooter = shooters[i];
            fDAO.getTotalValue(dateReference, currentShooter.getName(), new SimpleCallback<Integer>() {
                @Override
                public void callback(Integer data) {
                    System.out.println("############ " + data + " ###########");
                    switch(currentShooter.getName()){
                        //TODO: Remove the hardcoded strings
                        case "Tyler":
                            tblShooter1 = (TextView)findViewById(R.id.tblShooter1);
                            tblShooter1.setText(shooters[0].getName());
                            tblShooter1Total = (TextView)findViewById(R.id.tblShooter1Total);
                            if(data != null)
                                shooters[0].setCurrentScore(data);
                            tblShooter1Total.setText(String.valueOf(data));
                            break;
                        case "Dad":
                            tblShooter2 = (TextView)findViewById(R.id.tblShooter2);
                            tblShooter2.setText(shooters[1].getName());
                            tblShooter2Total = (TextView)findViewById(R.id.tblShooter2Total);
                            if(data != null)
                                shooters[1].setCurrentScore(data);
                            tblShooter2Total.setText(String.valueOf(data));
                            break;
                        case "Granddad":
                            tblShooter3 = (TextView)findViewById(R.id.tblShooter3);
                            tblShooter3.setText(shooters[2].getName());
                            tblShooter3Total = (TextView)findViewById(R.id.tblShooter3Total);
                            if(data != null)
                                shooters[2].setCurrentScore(data);
                            tblShooter3Total.setText(String.valueOf(data));
                            break;
                        case "Dalton":
                            tblShooter4 = (TextView)findViewById(R.id.tblShooter4);
                            tblShooter4.setText(shooters[3].getName());
                            tblShooter4Total = (TextView)findViewById(R.id.tblShooter4Total);
                            if(data != null)
                                shooters[3].setCurrentScore(data);
                            tblShooter4Total.setText(String.valueOf(data));
                            break;
                    }
                }
            });
        }
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
        FirebaseDAO fDAO = new FirebaseDAO();
        shotNumber = fDAO.incrementShooterIndex(dateReference,"ShotNumber",shotNumber);

        int score = shooters[shooterIndex].getCurrentScore()+1;
        shooters[shooterIndex].setCurrentScore(score);

        int totalScore = shooters[shooterIndex].getScore()+1;
        shooters[shooterIndex].setScore(totalScore);

        // Save the score
        String name = shooters[shooterIndex].getName();

        // Build the id for the shot
        StringBuilder sb = new StringBuilder();
        sb.append(stationNumber);
        sb.append(shotNumber);

        String shotID = sb.toString();

        int result = 1;

        // Save to the database
        fDAO.saveAShot(dateReference,name,shotID,result);
        fDAO.saveAShot(dateReference,name,"Total",totalScore);
        fDAO.saveAShot(dateReference,name,"CurrentScore",score);
    }
    public void shooterMissPressed(View view){
        FirebaseDAO fDAO = new FirebaseDAO();
        shotNumber = fDAO.incrementShooterIndex(dateReference,"ShotNumber",shotNumber);

        String name = shooters[shooterIndex].getName();

        int totalScore = shooters[shooterIndex].getScore()+1;
        shooters[shooterIndex].setScore(totalScore);

        // Build the id for the shot
        StringBuilder sb = new StringBuilder();
        sb.append(stationNumber);
        sb.append(shotNumber);

        String shotID = sb.toString();

        int result = 0;

        // Save to the database
        fDAO.saveAShot(dateReference,name,shotID,result);
        fDAO.saveAShot(dateReference,name,"Total",totalScore);
    }
    /*
     * This method is fired when the Next Stations button is pressed. It increments the station
     * counter and updates the text fields
     */
    public void nextStationButtonPressed(MenuItem item){
        FirebaseDAO fDAO = new FirebaseDAO();
        stationNumber = fDAO.incrementShooterIndex(dateReference,"StationNumber",stationNumber);

        shotNumber = fDAO.resetValue(dateReference,"ShotNumber");
        shooterIndex = fDAO.resetValue(dateReference,"ShooterIndex");

        for(int i = 0; i < shooters.length; i++){
            shooters[i].setCurrentScore(0);
            fDAO.saveAShot(dateReference,shooters[i].getName(),"CurrentScore",0);
        }
    }
    /*
     * This method is fired when the Next Shooter button is pressed. It increments the shooter
     * index counter and then sets the text of the shooterName text view to the next shooters
     * name.
     */
    public void nextShooterButtonPressed(View view){
        FirebaseDAO fDAO = new FirebaseDAO();
        shooterIndex = fDAO.incrementShooterIndex(dateReference,"ShooterIndex",shooterIndex);

        // Reset the shot number
        shotNumber = fDAO.resetValue(dateReference,"ShotNumber");
    }


    public void toggleTotalTable(View view){
        TableLayout totalTable = (TableLayout) findViewById(R.id.totalsTable);
        ToggleButton totalToggle = (ToggleButton) findViewById(R.id.tableToggleButton);
        System.out.println("totalToggle.isChecked() = " + totalToggle.isChecked());
        if(totalToggle.isChecked()){
            totalTable.setVisibility(View.GONE);
        }else{
            totalTable.setVisibility(View.VISIBLE);
        }
    }

    public void statisticsButtonPressed(MenuItem item){
        // Switch to the statistics activity
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void nextRoundButtonPressed(MenuItem item){

        //TODO: Implement the next round button
       /* new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Do you want to go on to the next round?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(MainActivity.this, "New Round!", Toast.LENGTH_SHORT).show();
                        roundNumber++;
                        shooters.setShooterIndex(0);
                        // TODO: Replace this when the Settings Activity is done

                        shooters.setShooter(0,"Tyler");
                        shooters.setShooter(1,"Dad");
                        shooters.setShooter(2,"Granddad");
                        shooters.setShooter(3,"Dalton");


                        tblShooter1Total.setText("0");
                        tblShooter2Total.setText("0");
                        tblShooter3Total.setText("0");
                        tblShooter4Total.setText("0");


                        stations.setStationNumber(0);

                        hitCounter.setText("0");
                        stationCounter.setText("0");
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    */
    }


}
