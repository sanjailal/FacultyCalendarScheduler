package com.example.facultycalendarscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

public class Timetable extends AppCompatActivity {
    TimetableView timetable1;
    private Schedule schedule;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        timetable1=findViewById(R.id.timetable);
        timetable1.setHeaderHighlight(1);
        timetable1.setHeaderHighlight(2);
        timetable1.setHeaderHighlight(3);
        timetable1.setHeaderHighlight(4);
        timetable1.setHeaderHighlight(5);






        ArrayList<Schedule> schedules = new ArrayList<Schedule>();

        schedule = new Schedule();
        schedule.setDay(0);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(8,40)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(9,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        schedule = new Schedule();
        schedule.setDay(0);
        schedule.setClassTitle("15CSE303"+"\n"+"Theory of Computation"); // sets subject
        schedule.setClassPlace("A204"+"\n"+"B.Tech-CSE-V Sem-F"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(14,00)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(14,50)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        schedule = new Schedule();
        schedule.setDay(1);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(9,30)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(10,20)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        schedule = new Schedule();
        schedule.setDay(1);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("ABI CP Lab2"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(14,00)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(16,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);


        schedule = new Schedule();
        schedule.setDay(2);
        schedule.setClassTitle("15CSE303"+"\n"+"Theory of Computation"); // sets subject
        schedule.setClassPlace("C203"+"\n"+"B.Tech-CSE-V Sem-F"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(8,40)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(9,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);


        schedule = new Schedule();
        schedule.setDay(2);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(9,30)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(10,20)); // sets the end of class time (hour,minute)
        schedules.add(schedule);



        schedule = new Schedule();
        schedule.setDay(2);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(14,00)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(16,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);



        schedule = new Schedule();
        schedule.setDay(3);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(8,40)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(9,30)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        schedule = new Schedule();
        schedule.setDay(3);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(11,20)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(12,10)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        schedule = new Schedule();
        schedule.setDay(4);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(10,20)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(11,10)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

        schedule = new Schedule();
        schedule.setDay(4);
        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
        schedule.setProfessorName("Aarthi R"); // sets professor
        schedule.setStartTime(new Time(13,00)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,50)); // sets the end of class time (hour,minute)
        schedules.add(schedule);


//.. add one or more schedules
        timetable1.add(schedules);
        initView();
    }



    private void initView(){


        timetable1.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                //String str=schedules.get(idx).getClassPlace();
                AlertDialog.Builder al =
                        new AlertDialog.Builder(
                                Timetable.this);
                al.setTitle("Timetable");
                al.setMessage(schedules.get(idx).getClassTitle()+"\n"+schedules.get(idx).getClassPlace()+"\n"+schedules.get(idx).getStartTime().getHour()+":"+schedules.get(idx).getStartTime().getMinute()+"-"+schedules.get(idx).getEndTime().getHour()+":"+schedules.get(idx).getEndTime().getMinute());
                al.setCancelable(true);
                al.show();

                al.setIcon(R.drawable.ic_launcher_background);

            }
        });


    }



}
