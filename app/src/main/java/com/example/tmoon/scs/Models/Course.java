package com.example.tmoon.scs.Models;

import com.example.tmoon.scs.CallbackInterfaces.SimpleCallback;
import com.example.tmoon.scs.DAO.FirebaseDAO;
import com.example.tmoon.scs.Enums.CourseNames;

/**
 * Created by tmoon on 3/9/17.
 */

public class Course {
    private Station[] stations;
    private static final int STATIONCOUNT = 10;

    public Course(CourseNames name){
        this.stations = new Station[STATIONCOUNT];
        FirebaseDAO fDAO = new FirebaseDAO();
        switch(name){
            case BLUE:
                // Setup the blue course variables
                System.out.println("Blue course not currently supported :(");
                break;
            case YELLOW:
                // Setup the yellow course variables
                fDAO.getCourseData(new SimpleCallback<String>() {
                    @Override
                    public void callback(String data) {
                        System.out.println("DATA CHECK ****** " + data);
                    }
                });
                break;
            case RED:
                // Setup the red course variables
                break;
            case GREEN:
                // Setup the green course variables
                break;
            default:

        }
    }
}
