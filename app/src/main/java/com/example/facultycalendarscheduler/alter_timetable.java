package com.example.facultycalendarscheduler;

import android.content.DialogInterface;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class alter_timetable extends AppCompatActivity {
    public String username;
    public int idx;
    public ProgressBar prgbar;
    public ArrayList<String> mobileArray = new ArrayList<>();
    TimetableView timetable1;
    ArrayList<Schedule> schedules = new ArrayList<>();
    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_timetable);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        prgbar = findViewById(R.id.progressBar);
        username = user.getEmail();
        timetable1 = findViewById(R.id.timetable);
        timetable1.setHeaderHighlight(1);
        timetable1.setHeaderHighlight(2);
        timetable1.setHeaderHighlight(3);
        timetable1.setHeaderHighlight(4);
        timetable1.setHeaderHighlight(5);
        Bundle extras = getIntent().getExtras();
        String s = "";
        if (extras == null) {
            Log.v("san", "null", null);
        } else {
            mobileArray = extras.getStringArrayList("event");
        }
        givvedetail(mobileArray);
        Log.v("san", mobileArray.toString(), null);
    }

    private void givvedetail(ArrayList<String> mobileArray) {

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

    private void initView() {


        timetable1.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                //String str=schedules.get(idx).getClassPlace();
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            new myTask().execute();
                            Log.v("san", "Yes", null);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            Log.v("san", "No", null);
                            break;
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(alter_timetable.this);
                builder.setMessage("Do you need to change this period?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }

    private class myTask extends AsyncTask<Void, Void, Void> {
        String s = "";
        String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr = "admin";
        String pwd = "123456789";
        String[] daysep = mobileArray.toString().split(",");
        String[] eventsep = daysep[0].split(";");
        int day = schedules.get(idx).getDay();
        int starthr = schedules.get(idx).getStartTime().getHour();
        int startmin = schedules.get(idx).getStartTime().getMinute();
        int endhr = schedules.get(idx).getEndTime().getHour();
        int endmin = schedules.get(idx).getEndTime().getMinute();


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
                int sta = st.executeUpdate("insert into flag values(1," + day + "," + starthr + "," + startmin + "," + endhr + "," + endmin + ",\"" + username + "\");");
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
                Toast.makeText(getApplicationContext(), "Contact Developer", Toast.LENGTH_LONG).show();
            }
            if (s.equals("0")) {
                Toast.makeText(getApplicationContext(), "Request has been sent", Toast.LENGTH_LONG).show();
                Log.v("san", "Inserted Successfully", null);

            }
        }
    }
}
