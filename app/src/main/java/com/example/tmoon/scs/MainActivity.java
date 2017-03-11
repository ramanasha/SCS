package com.example.tmoon.scs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tmoon.scs.CallbackInterfaces.SimpleCallback;
import com.example.tmoon.scs.DAO.FirebaseDAO;
import com.example.tmoon.scs.Models.Shooter;

public class MainActivity extends AppCompatActivity {
    //TODO: Replace this when the Settings Activity is done
    Shooter[] shooters = new Shooter[4];
    int shooterIndex;
    String[] shooterNames = {"Tyler","Dad","Granddad","Dalton"};

    int stationNumber;
    int shotNumber;

    //TODO: Make this actually change instead of being hardcoded
    final String dateReference = "3_12_2017";

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
        for(int i = 0; i < shooters.length; i++){
            shooters[i] = new Shooter(shooterNames[i]);
            System.out.println("Created new shooter " + shooters[i].toString());
        }


        //TODO: Make this a class variable
        FirebaseDAO fDAO = new FirebaseDAO();


        // Update when the shooter index is changed
        fDAO.getIndexValue(dateReference,"ShooterIndex", new SimpleCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                // Shooter
                shooterName = (TextView)findViewById(R.id.textView2);
                shooterName.setText(shooters[shooterIndex].getName());
            }
        });

        // Update when the shot number is changed
        fDAO.getIndexValue(dateReference, "ShotNumber", new SimpleCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                // TODO: Make a shot label
                hitCounter = (TextView)findViewById(R.id.hitCounter);
                hitCounter.setText(String.valueOf(data));
            }
        });

        // Update when the station number is changed
        fDAO.getIndexValue(dateReference, "StationNumber", new SimpleCallback<Integer>() {
            @Override
            public void callback(Integer data) {
                stationCounter = (TextView)findViewById(R.id.stationNumber);
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
                    switch(currentShooter.getName()){
                        //TODO: Remove the hardcoded strings
                        case "Tyler":
                            tblShooter1 = (TextView)findViewById(R.id.tblShooter1);
                            tblShooter1.setText(shooters[0].getName());
                            tblShooter1Total = (TextView)findViewById(R.id.tblShooter1Total);
                            tblShooter1Total.setText(String.valueOf(shooters[0].getScore()));
                            break;
                        case "Dad":
                            tblShooter2 = (TextView)findViewById(R.id.tblShooter2);
                            tblShooter2.setText(shooters[1].getName());
                            tblShooter2Total = (TextView)findViewById(R.id.tblShooter2Total);
                            tblShooter2Total.setText(String.valueOf(shooters[1].getScore()));
                            break;
                        case "Granddad":
                            tblShooter3 = (TextView)findViewById(R.id.tblShooter3);
                            tblShooter3.setText(shooters[2].getName());
                            tblShooter3Total = (TextView)findViewById(R.id.tblShooter3Total);
                            tblShooter3Total.setText(String.valueOf(shooters[2].getScore()));
                            break;
                        case "Dalton":
                            tblShooter4 = (TextView)findViewById(R.id.tblShooter4);
                            tblShooter4.setText(shooters[3].getName());
                            tblShooter4Total = (TextView)findViewById(R.id.tblShooter4Total);
                            tblShooter4Total.setText(String.valueOf(shooters[3].getScore()));
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

        int score = shooters[shooterIndex].getScore()+1;
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

        // Build the id for the shot
        StringBuilder sb = new StringBuilder();
        sb.append(stationNumber);
        sb.append(shotNumber);

        String shotID = sb.toString();

        int result = 0;

        // Save to the database
        fDAO.saveAShot(dateReference,name,shotID,result);
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
