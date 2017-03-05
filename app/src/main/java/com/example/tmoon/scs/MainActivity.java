package com.example.tmoon.scs;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    Stations stations = new Stations(0);

    TextView shooterName;
    TextView hitCounter;
    TextView stationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Replace this when the Settings Activity is done
        shooters[0] = new Shooter("Tyler");
        shooters[0] = new Shooter("Tyler");
        shooters[1] = new Shooter("Dad");
        shooters[2] = new Shooter("Granddad");
        shooters[3] = new Shooter("Dalton");

        shooterName = (TextView)findViewById(R.id.textView2);
        shooterName.setText(shooters[shooterIndex].getName());


        hitCounter = (TextView)findViewById(R.id.hitCounter);
        //TODO: Place this into a method so it doesn't have to be repeated so much
        hitCounter.setText(Integer.toString(shooters[shooterIndex].getHits()[stations.getStationNumber()]));

        stationCounter = (TextView)findViewById(R.id.stationNumber);
        stationCounter.setText(Integer.toString(stations.getStationNumber()));
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
    public void nextStationButtonPressed(View view){
        stations.incrementStationNumber();
        stationCounter.setText(Integer.toString(stations.getStationNumber()));
        hitCounter.setText(Integer.toString(shooters[shooterIndex].incrementHits(stations.getStationNumber())));
    }

    /*
     * This method is fired when the Next Shooter button is pressed. It increments the shooter
     * index counter and then sets the text of the shooterName text view to the next shooters
     * name.
     */
    public void nextShooterButtonPressed(View view){
        incrementShooterIndex();
        shooterName.setText(shooters[shooterIndex].getName());
        hitCounter.setText(Integer.toString(shooters[shooterIndex].getHits()[stations.getStationNumber()]));// Reset the hit counter for the new shooter

        // TODO: Possibly dont save every time this is pressed if it slows it down or something
        saveData();
    }

    private void saveData(){

        String filename = "SCSAnalysisData.csv";
        String string = "Hello World!";

        File path = getFilesDir();
        File file = new File(path, filename);
        System.out.println(file.getAbsolutePath().toString());
        FileOutputStream stream;
        try {
            stream = new FileOutputStream(file);
            stream.write(string.getBytes());
            stream.close();
        } catch(IOException ie){
            ie.printStackTrace();
        }





        /*PrintWriter csvWriter;
        String filePath;
        try {
            String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName = "SCSAnalysisData.csv";
            filePath = baseDir + File.separator + fileName;
            System.out.println("Writing to file " + fileName);
            File file = new File(filePath);
            if (!file.exists())
                file = new File(filePath);
            csvWriter = new PrintWriter(new FileWriter(file,true));
            csvWriter.print("Testing");
            csvWriter.close();
        }catch(IOException ie){
            ie.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }*/

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
}
