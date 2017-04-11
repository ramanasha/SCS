package com.example.tmoon.scs.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tmoon.scs.CallbackInterfaces.SimpleCallback;
import com.example.tmoon.scs.DAO.FirebaseDAO;
import com.example.tmoon.scs.Enums.Trap;
import com.example.tmoon.scs.Models.Course;
import com.example.tmoon.scs.Models.Shot;
import com.example.tmoon.scs.Models.Station;
import com.example.tmoon.scs.Models.StatisticsShooter;
import com.example.tmoon.scs.R;

/**
 * Created by tmoon on 3/11/17.
 */

public class StatisticsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    TextView bestTrap, worstTrap, averageScore;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_main);


        // Name spinner
        spinner = (Spinner) findViewById(R.id.nameSpinner);
        // Load the names from the names.xml file
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.shooterNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // TextViews
        bestTrap = (TextView) findViewById(R.id.bestTrapTextView);
        worstTrap = (TextView) findViewById(R.id.worstTrapTextView);
        averageScore = (TextView) findViewById(R.id.averageScoreTextView);

        final FirebaseDAO fDAO = new FirebaseDAO();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        System.out.println(parent.getSelectedItem().toString());
        final FirebaseDAO fDAO = new FirebaseDAO();
        //TODO: Remove this when testing is complete
        //TODO: Remove these hardcoded values
        fDAO.getShooterScores("3_12_2017", parent.getSelectedItem().toString(), new SimpleCallback<StatisticsShooter>() {
            @Override
            public void callback(final StatisticsShooter statData) {
                System.out.println("Printing Shooter Statistics data");
                System.out.println(statData.toString());
                //TODO: Remove this when testing is complete
                fDAO.getCourse("This", 1, 1, new SimpleCallback<Course>() {
                    @Override
                    public void callback(Course data) {
                        System.out.println("Printing course data");
                        System.out.println(data.toString());
                        String best = data.calculateBestTrap(statData);
                        bestTrap.setText(best);

                        String worst = data.calculateWorstTrap(statData);
                        worstTrap.setText(worst);
                    }
                });
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
}
