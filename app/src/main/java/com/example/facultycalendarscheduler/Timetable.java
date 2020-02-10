package com.example.facultycalendarscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

public class Timetable extends AppCompatActivity implements TimetableView.OnStickerSelectedListener{
    private TimetableView timetable;
    protected void onCreate(Bundle savedInstanceState) {
        timetable=findViewById(R.id.timetable);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        Schedule schedule = new Schedule();
        schedule.setClassTitle("Data Structure"); // sets subject
        schedule.setClassPlace("IT-601"); // sets place
        schedule.setProfessorName("Won Kim"); // sets professor
        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

//.. add one or more schedules
        timetable.add(schedules);
    }


    @Override
    public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {

    }
}
