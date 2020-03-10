package com.example.facultycalendarscheduler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Timetable extends AppCompatActivity {
    TimetableView timetable1;
    private Schedule schedule;
    public ProgressBar prgbar;
    public String username;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getEmail();
        prgbar = findViewById(R.id.progressBar);
        new myTask().execute();
        timetable1=findViewById(R.id.timetable);
        timetable1.setHeaderHighlight(1);
        timetable1.setHeaderHighlight(2);
        timetable1.setHeaderHighlight(3);
        timetable1.setHeaderHighlight(4);
        timetable1.setHeaderHighlight(5);

//        ArrayList<Schedule> schedules = new ArrayList<>();
//
//        schedule = new Schedule();
//        schedule.setDay(0);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(8,40)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(9,30)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//        schedule = new Schedule();
//        schedule.setDay(0);
//        schedule.setClassTitle("15CSE303"+"\n"+"Theory of Computation"); // sets subject
//        schedule.setClassPlace("A204"+"\n"+"B.Tech-CSE-V Sem-F"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(14, 0)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(14,50)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//        schedule = new Schedule();
//        schedule.setDay(1);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(9,30)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(10,20)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//        schedule = new Schedule();
//        schedule.setDay(1);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("ABI CP Lab2"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(14,0)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(16,30)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//
//        schedule = new Schedule();
//        schedule.setDay(2);
//        schedule.setClassTitle("15CSE303"+"\n"+"Theory of Computation"); // sets subject
//        schedule.setClassPlace("C203"+"\n"+"B.Tech-CSE-V Sem-F"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(8,40)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(9,30)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//
//        schedule = new Schedule();
//        schedule.setDay(2);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(9,30)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(10,20)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//
//
//        schedule = new Schedule();
//        schedule.setDay(2);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(14,0)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(16,30)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//
//
//        schedule = new Schedule();
//        schedule.setDay(3);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(8,40)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(9,30)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//        schedule = new Schedule();
//        schedule.setDay(3);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(11,20)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(12,10)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//        schedule = new Schedule();
//        schedule.setDay(4);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(10,20)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(11,10)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//
//        schedule = new Schedule();
//        schedule.setDay(4);
//        schedule.setClassTitle("19CSE100"+"\n"+"Problem solving and Algorithmic Thinking"); // sets subject
//        schedule.setClassPlace("C405"+"\n"+"B.Tech-ECE-I Sem-A"); // sets place
//        schedule.setProfessorName("Aarthi R"); // sets professor
//        schedule.setStartTime(new Time(13,0)); // sets the beginning of class time (hour,minute)
//        schedule.setEndTime(new Time(13,50)); // sets the end of class time (hour,minute)
//        schedules.add(schedule);
//

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

    private void givvedetail(ArrayList<String> mobileArray) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        String[] daysep = mobileArray.toString().split(",");
        for (int i = 0; i < daysep.length; i++) {
            String[] eventsep = daysep[i].split(";");
            int day = Integer.parseInt(eventsep[0].substring(1));
            String classtitle = eventsep[1];
            String classplace = eventsep[2];
            String profname = eventsep[3];
            int starthr = Integer.parseInt(eventsep[4]);
            int startmin = Integer.parseInt(eventsep[5]);
            int endhr = Integer.parseInt(eventsep[6]);
            int endmin = Integer.parseInt(eventsep[7].substring(0, 2));
            schedule = new Schedule();
            schedule.setDay(day);
            schedule.setClassTitle(classtitle); // sets subject
            schedule.setClassPlace(classplace); // sets place
            schedule.setProfessorName(profname); // sets professor
            schedule.setStartTime(new Time(starthr, startmin)); // sets the beginning of class time (hour,minute)
            schedule.setEndTime(new Time(endhr, endmin)); // sets the end of class time (hour,minute)
            schedules.add(schedule);
        }
        timetable1.add(schedules);
        initView();
    }

    private class myTask extends AsyncTask<Void, Void, Void> {
        String s = "";
        String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr = "admin";
        String pwd = "123456789";
        private ArrayList<String> mobileArray = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Connection con = null;
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Log.v("san", "pending");
                con = DriverManager.getConnection(url, usr, pwd);
                Log.v("san", "comp");
                s = "0";
                Statement st = con.createStatement();
                ResultSet rsmon = st.executeQuery("select * from timetable_entry where faculty=\"" + username + "\";");
                while (rsmon.next())
                    mobileArray.add(rsmon.getString(1) + ";" + rsmon.getString(2) + ";" + rsmon.getString(3) + ";" + rsmon.getString(4) + ";" + rsmon.getString(5) + ";" + rsmon.getString(6) + ";" + rsmon.getString(7) + ";" + rsmon.getString(8));
            } catch (Exception E) {
                E.printStackTrace();

                s = "1";
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prgbar.setVisibility(View.GONE);
            if (s.equals("1")) {
                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
            }
            if (s.equals("0")) {

                Log.v("timetable", mobileArray.toString(), null);
                givvedetail(mobileArray);
            }
        }
    }


}
