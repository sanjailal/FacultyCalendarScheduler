package com.example.facultycalendarscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    Statement st;
    private ProgressBar prgbar;
    private ArrayList<String> mobileArray = new ArrayList<>();
    private ArrayList<String> flagevent = new ArrayList<>();
    private String username;
    private int fla;
    int daymob;
    int starthrmob;
    int startminmob;
    int endhrmob;
    int endminmob;
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
                Log.d("id", String.valueOf(idx));
                al.setCancelable(true);
                al.show();

                al.setIcon(R.drawable.ic_launcher_background);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu
            (Menu menu) {
        getMenuInflater().inflate
                (R.menu.alter_timetable_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected
            (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changetimetable:
                Intent i = new Intent(this, alter_timetable.class);
                i.putExtra("event", mobileArray);
                startActivity(i);
                break;
            case R.id.request:
                Intent j = new Intent(this, alter_timetable.class);
                j.putExtra("flag", flagevent);
                startActivity(j);
                break;

        }
        return true;
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
            int endmin = Integer.parseInt(eventsep[7].substring(0,2));
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
                st = con.createStatement();
                String viewtt = "select * from timetable_entry where faculty=\"" + username + "\";";
                ResultSet rsmon = st.executeQuery(viewtt);
                while (rsmon.next())
                    mobileArray.add(rsmon.getString(1) + ";" + rsmon.getString(2) + ";" + rsmon.getString(3) + ";" + rsmon.getString(4) + ";" + rsmon.getString(5) + ";" + rsmon.getString(6) + ";" + rsmon.getString(7) + ";" + rsmon.getString(8));
                String viewreq = "select day,start_hr,start_min,end_hr,end_min,faculty from flag where flag=1 and faculty<>\"" + username + "\"";
                ResultSet flag = st.executeQuery(viewreq);
                while (flag.next())
                    flagevent.add(flag.getString(1) + ";" + flag.getString(2) + ";" + flag.getString(3) + ";" + flag.getString(4) + ";" + flag.getString(5) + ";" + flag.getString(6));

            } catch (Exception E) {
                E.printStackTrace();

                s = "1";
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    Log.v("san", "try", null);
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

                Log.v("san", mobileArray.toString(), null);
                Log.v("san", flagevent.toString(), null);
                givvedetail(mobileArray);
                if (flagevent.size() == 1) {
                    showdialogbox(flagevent);
                } else if (flagevent.size() > 1) {
                    Toast.makeText(Timetable.this, "You have multiple requests! Please contact developer for more update", Toast.LENGTH_LONG).show();
                }
            }
        }

        private void showdialogbox(ArrayList<String> flagevent) {
            String[] daysep = flagevent.toString().split(",");
            String[] eventsep = daysep[0].split(";");
            int day = Integer.parseInt(eventsep[0].substring(1));
            int starthr = Integer.parseInt(eventsep[1]);
            int startmin = Integer.parseInt(eventsep[2]);
            int endhr = Integer.parseInt(eventsep[3]);
            int endmin = Integer.parseInt(eventsep[4]);
            String faculty = eventsep[5];
            String[] daysepmob = mobileArray.toString().split(",");
            for (int i = 0; i < daysep.length; i++) {
                String[] eventsepmob = daysepmob[i].split(";");
                daymob = Integer.parseInt(eventsepmob[0].substring(1));
                starthrmob = Integer.parseInt(eventsepmob[4]);
                startminmob = Integer.parseInt(eventsepmob[5]);
                endhrmob = Integer.parseInt(eventsepmob[6]);
                endminmob = Integer.parseInt(eventsepmob[7].substring(0, 2));
                boolean checkstatus = day != daymob && starthr != starthrmob && startmin != startminmob && endhr != endhrmob && endmin != endminmob;
                if (!checkstatus) {

                    fla = 1;
                } else {
                    fla = 0;
                }
                Log.v("san", String.valueOf(checkstatus), null);
            }
            if (fla == 1) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            new myTaskalter().execute();
                            Log.v("san", "Yes", null);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            Log.v("san", "No", null);
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(Timetable.this);
                builder.setMessage("Do you need this period " + "\nday= " + day + "\nstart hour= " + starthr + "\nstart minute= " + startmin + "\nEnd hour= " + endhr + "\nEnd minute= " + endmin + "\nFrom Faculty= " + faculty).setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        }
    }

    private class myTaskalter extends AsyncTask<Void, Void, Void> {
        String s = "";
        String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr = "admin";
        String pwd = "123456789";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // loadingProgressBar.setVisibility(View.VISIBLE);
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
                // st.executeQuery("insert into eventff values(\""+date+"\",\""+add+"\",\""+usermail+"\");");
                // ResultSet rs = st.executeQuery("insert into eventff values(\"2-2-2020\",\"Class Cog\",\"soft@gmail.com\");");
                String alterperiod = "update flag set faculty=\"" + username + "\",flag=0 where flag=1 and day=" + daymob + " and start_hr=" + starthrmob + " and start_min=" + startminmob + " and end_hr=" + endhrmob + " and end_min=" + endminmob + ";";
                Log.v("san", alterperiod, null);
                int sta = st.executeUpdate(alterperiod);
                if (sta > 0)
                    Log.v("san", "success");
                else
                    Log.v("san", "fail");

            } catch (Exception E) {
                E.printStackTrace();

                s = "1";
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (s.equals("1")) {
                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
            }
            if (s.equals("0")) {

                Toast.makeText(getApplicationContext(), "Changed succesfully", Toast.LENGTH_LONG).show();
            }
        }
    }

}
