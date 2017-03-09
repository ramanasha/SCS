package com.example.tmoon.scs;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmoon.scs.DAO.FirebaseDAO;

public class MainActivity extends AppCompatActivity {
    //TODO: Replace this when the Settings Activity is done
    ShooterArray shooters = new ShooterArray(4);

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
        shooters.setShooter(0,"Tyler");
        shooters.setShooter(1,"Dad");
        shooters.setShooter(2,"Granddad");
        shooters.setShooter(3,"Dalton");


        // Initialize the name label with the first shooters name
        shooterName = (TextView)findViewById(R.id.textView2);
        shooterName.setText(shooters.getShooterName());

        // Initialize the hit counter
        hitCounter = (TextView)findViewById(R.id.hitCounter);
        //TODO: Place this into a method so it doesn't have to be repeated so much
        hitCounter.setText(shooters.getHitNumber(stations.getStationNumber()));

        // Initialize the station label
        stationCounter = (TextView)findViewById(R.id.stationNumber);
        stationCounter.setText(stations.getStationNumberString());

        // Total table information
        tblShooter1 = (TextView)findViewById(R.id.tblShooter1);
        tblShooter1.setText(shooters.getShooterName(0));
        tblShooter2 = (TextView)findViewById(R.id.tblShooter2);
        tblShooter2.setText(shooters.getShooterName(1));
        tblShooter3 = (TextView)findViewById(R.id.tblShooter3);
        tblShooter3.setText(shooters.getShooterName(2));
        tblShooter4 = (TextView)findViewById(R.id.tblShooter4);
        tblShooter4.setText(shooters.getShooterName(3));

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
        shooters.incrementHitNumber(stations.getStationNumber());
        hitCounter.setText(shooters.getHitNumber(stations.getStationNumber()));
        // Increment the number of hits at the index stationNumber and set the counter text view
        // to the incremented value

    }
    /*
     * This method is fired when the Next Stations button is pressed. It increments the station
     * counter and updates the text fields
     */
    public void nextStationButtonPressed(MenuItem item){
        stations.incrementStationNumber();
        stationCounter.setText(stations.getStationNumberString());
        hitCounter.setText(shooters.getHitNumber(stations.getStationNumber()));
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
    }

    /*
     * This method is fired when the Next Shooter button is pressed. It increments the shooter
     * index counter and then sets the text of the shooterName text view to the next shooters
     * name.
     */
    public void nextShooterButtonPressed(View view){
        FirebaseDAO fDAO = new FirebaseDAO();
        fDAO.setRoundReference(Integer.toString(roundNumber));

        // Set the total for that shooter
        setShooterTotal();
        fDAO.updateTotal(shooters.getShooterName(), shooters.getTotalHitNumber());

        // Increment the index of the shooter and if it is greater than the number of shooters then
        // go back to the beginning
        shooters.incrementIndex();

        // Set the new shooters name
        shooterName.setText(shooters.getShooterName());

        // Set the hit counter
        hitCounter.setText(shooters.getHitNumber(stations.getStationNumber()));
        fDAO.updateHits(shooters.getShooterName(),stations.getStationNumberString(),shooters.getHitNumber(stations.getStationNumber()));

        // TODO: Remove this once its determine that reading the data works
        // Testing reading Firebase data
        fDAO.testReadingData();
    }


    private void setShooterTotal(){
        String totalNumber = shooters.getTotalHitNumber();
        switch(shooters.getShooterIndex()){
            case 0:
                tblShooter1Total.setText(totalNumber);
                break;
            case 1:
                tblShooter2Total.setText(totalNumber);
                break;
            case 2:
                tblShooter3Total.setText(totalNumber);
                break;
            case 3:
                tblShooter4Total.setText(totalNumber);
                break;
        }
    }
}
