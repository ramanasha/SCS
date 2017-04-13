package com.example.tmoon.scs.Activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
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
import com.example.tmoon.scs.R;

import org.w3c.dom.Text;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //TODO: Replace this when the Settings Activity is done
    Shooter[] shooters = new Shooter[4];
    int shooterIndex;
    String[] shooterNames = {"Tyler","Dad","Granddad","Dalton"};

    int stationNumber;
    int shotNumber;

    String dateReference = "";

    TextView shooterName;
    TextView hitCounter;
    TextView stationCounter;

    TextView tblShooter1,tblShooter2, tblShooter3, tblShooter4;
    TextView tblShooter1Current,tblShooter2Current, tblShooter3Current, tblShooter4Current;
    TextView tblShooter1Total, tblShooter2Total, tblShooter3Total, tblShooter4Total;
    TextView tblShooter1Percent,tblShooter2Percent, tblShooter3Percent, tblShooter4Percent;

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
            fDAO.getTotalValue(dateReference, currentShooter.getName(), new SimpleCallback<Shooter>() {
                @Override
                public void callback(Shooter data) {
                    switch(currentShooter.getName()){
                        //TODO: Remove the hardcoded strings
                        //TODO: Put each case in a method or something
                        case "Tyler":
                            tblShooter1 = (TextView)findViewById(R.id.tblShooter1);
                            tblShooter1.setText(shooters[0].getName());
                            tblShooter1Current = (TextView) findViewById(R.id.tblShooter1Current);
                            tblShooter1Total = (TextView)findViewById(R.id.tblShooter1Total);
                            tblShooter1Percent = (TextView) findViewById(R.id.tblShooter1Percent);
                            if(data != null){
                                shooters[0].setCurrentScore(data.getCurrentScore());
                                shooters[0].setTotal(data.getTotal());
                                tblShooter1Total.setText(String.valueOf(data.getTotal()));
                                tblShooter1Current.setText(String.valueOf(data.getCurrentScore()));
                                double percent = ((double)data.getCurrentScore() / (double)data.getTotal())*100.0;
                                tblShooter1Percent.setText(String.format("%.2f",percent));
                            }
                            break;
                        case "Dad":
                            tblShooter2 = (TextView)findViewById(R.id.tblShooter2);
                            tblShooter2.setText(shooters[1].getName());
                            tblShooter2Current = (TextView) findViewById(R.id.tblShooter2Current);
                            tblShooter2Total = (TextView)findViewById(R.id.tblShooter2Total);
                            tblShooter2Percent = (TextView) findViewById(R.id.tblShooter2Percent);
                            if(data != null){
                                shooters[1].setCurrentScore(data.getCurrentScore());
                                shooters[1].setTotal(data.getTotal());
                                tblShooter2Total.setText(String.valueOf(data.getTotal()));
                                tblShooter2Current.setText(String.valueOf(data.getCurrentScore()));
                                double percent = ((double)data.getCurrentScore() / (double)data.getTotal())*100.0;
                                tblShooter2Percent.setText(String.format("%.2f",percent));
                            }
                            break;
                        case "Granddad":
                            tblShooter3 = (TextView)findViewById(R.id.tblShooter3);
                            tblShooter3.setText(shooters[2].getName());
                            tblShooter3Current = (TextView) findViewById(R.id.tblShooter3Current);
                            tblShooter3Total = (TextView)findViewById(R.id.tblShooter3Total);
                            tblShooter3Percent = (TextView) findViewById(R.id.tblShooter3Percent);
                            if(data != null){
                                shooters[2].setCurrentScore(data.getCurrentScore());
                                shooters[2].setTotal(data.getTotal());
                                tblShooter3Total.setText(String.valueOf(data.getTotal()));
                                tblShooter3Current.setText(String.valueOf(data.getCurrentScore()));
                                double percent = ((double)data.getCurrentScore() / (double)data.getTotal())*100.0;
                                tblShooter3Percent.setText(String.format("%.2f",percent));
                            }
                            break;
                        case "Dalton":
                            tblShooter4 = (TextView)findViewById(R.id.tblShooter4);
                            tblShooter4.setText(shooters[3].getName());
                            tblShooter4Current = (TextView) findViewById(R.id.tblShooter4Current);
                            tblShooter4Total = (TextView)findViewById(R.id.tblShooter4Total);
                            tblShooter4Percent = (TextView) findViewById(R.id.tblShooter4Percent);
                            if(data != null){
                                shooters[3].setCurrentScore(data.getCurrentScore());
                                shooters[3].setTotal(data.getTotal());
                                tblShooter4Total.setText(String.valueOf(data.getTotal()));
                                tblShooter4Current.setText(String.valueOf(data.getCurrentScore()));
                                double percent = ((double)data.getCurrentScore() / (double)data.getTotal())*100.0;
                                tblShooter4Percent.setText(String.format("%.2f",percent));
                            }
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

        int totalScore = shooters[shooterIndex].getTotal()+1;
        shooters[shooterIndex].setTotal(totalScore);

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
        fDAO.saveAShot(dateReference,name,"total",totalScore);
        fDAO.saveAShot(dateReference,name,"currentScore",score);

        // Vibrator
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
    }
    public void shooterMissPressed(View view){
        FirebaseDAO fDAO = new FirebaseDAO();
        shotNumber = fDAO.incrementShooterIndex(dateReference,"ShotNumber",shotNumber);

        String name = shooters[shooterIndex].getName();

        int totalScore = shooters[shooterIndex].getTotal()+1;
        shooters[shooterIndex].setTotal(totalScore);

        // Build the id for the shot
        StringBuilder sb = new StringBuilder();
        sb.append(stationNumber);
        sb.append(shotNumber);

        String shotID = sb.toString();

        int result = 0;

        // Save to the database
        fDAO.saveAShot(dateReference,name,shotID,result);
        fDAO.saveAShot(dateReference,name,"total",totalScore);
        // Vibrator
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch(keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    FirebaseDAO fDAO = new FirebaseDAO();
                    shotNumber = fDAO.incrementShooterIndex(dateReference,"ShotNumber",shotNumber);

                    int score = shooters[shooterIndex].getCurrentScore()+1;
                    shooters[shooterIndex].setCurrentScore(score);

                    int totalScore = shooters[shooterIndex].getTotal()+1;
                    shooters[shooterIndex].setTotal(totalScore);

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
                    fDAO.saveAShot(dateReference,name,"total",totalScore);
                    fDAO.saveAShot(dateReference,name,"currentScore",score);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    FirebaseDAO fDAO = new FirebaseDAO();
                    shotNumber = fDAO.incrementShooterIndex(dateReference,"ShotNumber",shotNumber);

                    String name = shooters[shooterIndex].getName();

                    int totalScore = shooters[shooterIndex].getTotal()+1;
                    shooters[shooterIndex].setTotal(totalScore);

                    // Build the id for the shot
                    StringBuilder sb = new StringBuilder();
                    sb.append(stationNumber);
                    sb.append(shotNumber);

                    String shotID = sb.toString();

                    int result = 0;

                    // Save to the database
                    fDAO.saveAShot(dateReference,name,shotID,result);
                    fDAO.saveAShot(dateReference,name,"total",totalScore);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
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
    }

    public void nextStationButtonPressed(View view){
        FirebaseDAO fDAO = new FirebaseDAO();
        stationNumber = fDAO.incrementShooterIndex(dateReference,"StationNumber",stationNumber);

        shotNumber = fDAO.resetValue(dateReference,"ShotNumber");
        shooterIndex = fDAO.resetValue(dateReference,"ShooterIndex");
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
    }
}
