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
    Statement st;
    private ProgressBar prgbar;
    private ArrayList<String> mobileArray = new ArrayList<>();
    private ArrayList<String> flagevent = new ArrayList<>();
    private String username;
    private int fla;
    int gloflag;
    int day;
    int starthr;
    int startmin;
    int endhr;
    int endmin;
    String faculty;
    String err = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getEmail();
        prgbar = findViewById(R.id.progressBar);
        gloflag = 0;
        new MyTask().execute();
        timetable1=findViewById(R.id.timetable);
        timetable1.setHeaderHighlight(1);
        timetable1.setHeaderHighlight(2);
        timetable1.setHeaderHighlight(3);
        timetable1.setHeaderHighlight(4);
        timetable1.setHeaderHighlight(5);

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

            case R.id.Logout:
                Intent i1=new Intent(this,MainActivity.class);
                startActivity(i1);
            default:

        }
        return true;
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
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
                Log.e(err, String.valueOf(E));

                s = "1";
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    Log.e(err, String.valueOf(e));
                } finally {
                    Log.v("san", "try", null);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prgbar.setVisibility(View.INVISIBLE);
            if (s.equals("1")) {
                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
            }
            if (s.equals("0")) {

                Log.v("san", mobileArray.toString(), null);
                Log.v("san", flagevent.toString(), null);
                givvedetail(mobileArray);
                if (flagevent.size() == 1 && gloflag == 0) {
                    showdialogbox(flagevent);
                } else if (flagevent.size() > 1) {
                    Toast.makeText(Timetable.this, "You have multiple requests! Please contact developer for more update", Toast.LENGTH_LONG).show();
                }
            }
        }

        private void givvedetail(ArrayList<String> mobileArray) {
            Schedule schedule;
            ArrayList<Schedule> schedules = new ArrayList<>();
            String[] daysep = mobileArray.toString().split(",");
            for (int i = 0; i < daysep.length; i++) {
                String[] eventsep = daysep[i].split(";");
                int dayt = Integer.parseInt(eventsep[0].substring(1));
                String classtitle = eventsep[1];
                String classplace = eventsep[2];
                String profname = eventsep[3];
                int starthrt = Integer.parseInt(eventsep[4]);
                int startmint = Integer.parseInt(eventsep[5]);
                int endhrt = Integer.parseInt(eventsep[6]);
                int endmint = Integer.parseInt(eventsep[7].substring(0, 2));
                schedule = new Schedule();
                schedule.setDay(dayt);
                schedule.setClassTitle(classtitle); // sets subject
                schedule.setClassPlace(classplace); // sets place
                schedule.setProfessorName(profname); // sets professor
                schedule.setStartTime(new Time(starthrt, startmint)); // sets the beginning of class time (hour,minute)
                schedule.setEndTime(new Time(endhrt, endmint)); // sets the end of class time (hour,minute)
                schedules.add(schedule);
            }
            timetable1.add(schedules);

        }

        private void showdialogbox(ArrayList<String> flagevent) {
            String[] daysep = flagevent.toString().split(",");
            String[] eventsep = daysep[0].split(";");
            day = Integer.parseInt(eventsep[0].substring(1));
            starthr = Integer.parseInt(eventsep[1]);
            startmin = Integer.parseInt(eventsep[2]);
            endhr = Integer.parseInt(eventsep[3]);
            endmin = Integer.parseInt(eventsep[4]);
            faculty = eventsep[5].replace("]", "/a");

            String[] facult = faculty.split("/");

            faculty = facult[0];
            String[] daysepmob = mobileArray.toString().split(",");
            for (int i = 0; i < daysepmob.length; i++) {
                String[] eventsepmob = daysepmob[i].split(";");
                int daymob = Integer.parseInt(eventsepmob[0].substring(1));
                int starthrmob = Integer.parseInt(eventsepmob[4]);
                int startminmob = Integer.parseInt(eventsepmob[5]);
                int endhrmob = Integer.parseInt(eventsepmob[6]);
                int endminmob = Integer.parseInt(eventsepmob[7].substring(0, 2));
                Log.v("san", day + " " + starthr + " " + startmin + " " + endhr + " " + endmin);
                Log.v("san", daymob + " " + starthrmob + " " + startminmob + " " + endhrmob + " " + endminmob);
                boolean checkstatus = (day == daymob) && (starthr == starthrmob) && (startmin == startminmob) && (endhr == endhrmob) && (endmin == endminmob);
                Log.v("san", String.valueOf(checkstatus), null);
                if (!checkstatus) {
                    fla = 1;
                }
            }
            if (fla == 1) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            new MyTaskalter().execute();
                            Log.v("san", "Yes", null);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            Log.v("san", "No", null);
                            break;
                        default:

                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(Timetable.this);
                builder.setMessage("Do you need this period " + "\nday= " + day + "\nstart hour= " + starthr + "\nstart minute= " + startmin + "\nEnd hour= " + endhr + "\nEnd minute= " + endmin + "\nFrom Faculty= " + faculty).setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        }
    }

    private class MyTaskalter extends AsyncTask<Void, Void, Void> {
        String s = "";
        String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr = "admin";
        String pwd = "123456789";


        @Override
        protected void onPreExecute() {
            prgbar.setVisibility(View.VISIBLE);
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Connection con = null;
            try {
                Log.v("san", "pending");
                con = DriverManager.getConnection(url, usr, pwd);
                Log.v("san", "comp");
                s = "0";
                Statement st = con.createStatement();
                String alterflag = "update flag set faculty=\"" + username + "\",flag=0 where flag=1 and day=" + day + " and start_hr=" + starthr + " and start_min=" + startmin + " and end_hr=" + endhr + " and end_min=" + endmin + ";";
                String alterperiod = "update timetable_entry set faculty=\"" + username + "\" where day=" + day + " and start_hr=" + starthr + " and start_min=" + startmin + " and end_hr=" + endhr + " and end_min=" + endmin + " and faculty=\"" + faculty + "\";";
                Log.v("san", alterperiod, null);
                int sta = st.executeUpdate(alterperiod);
                int sta1 = st.executeUpdate(alterflag);
                if (sta + sta1 > 1)
                    Log.v("san", "success");
                else
                    Log.v("san", "fail");

            } catch (Exception E) {
                Log.e(err, String.valueOf(E));

                s = "1";
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    Log.e(err, String.valueOf(e));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            prgbar.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);
            if (s.equals("1")) {
                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
            }
            if (s.equals("0")) {
                gloflag = 1;
                new MyTask().execute();
                Toast.makeText(getApplicationContext(), "Changed succesfully", Toast.LENGTH_LONG).show();
            }
        }
    }

}
