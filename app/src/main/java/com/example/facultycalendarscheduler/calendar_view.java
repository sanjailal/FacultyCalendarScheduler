package com.example.facultycalendarscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.graphics.Color;
import android.os.AsyncTask;

import android.os.Bundle;

import android.util.Log;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;


public class calendar_view extends AppCompatActivity implements OnDateSelectedListener {

    public ArrayList<String> mobileArray =new ArrayList<String>();
    public String daystr="";
    private ProgressBar loadingProgressBar;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    public String listviewstr="";
    public ArrayAdapter adapter;
    @BindView(R2.id.calendarView_single) MaterialCalendarView single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        ButterKnife.bind(this);
        single.setOnDateChangedListener(this);
        loadingProgressBar=findViewById(R.id.loading);
        Bundle extras;
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());


    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        final String text = selected ? FORMATTER.format(date.getDate()) : "No Selection";
        int curdate =selected ? (date.getDay()):0;
        int curmonth =selected ? (date.getMonth()):0;
        int curyr=selected ? (date.getYear()):0;
        daystr =curdate+"-"+curmonth+"-"+curyr;
        new myTask().execute();
        Toast.makeText(calendar_view.this, daystr, Toast.LENGTH_SHORT).show();
    }





    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LocalDate temp = LocalDate.now().minusMonths(2);
            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                final CalendarDay day = CalendarDay.from(temp);
                dates.add(day);
                temp = temp.plusDays(5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            Log.d("fd",calendarDays.toString(),null);
            single.addDecorator(new EventDecorator(Color.BLUE, calendarDays));
        }
    }

    public class myTask extends AsyncTask<Void,Void,Void>
    {
        String s="";
        String url="jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr="admin";
        String pwd="123456789";



        @Override
        protected void onPreExecute() {
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                Class.forName("com.mysql.jdbc.Driver");
                Log.v("san","pending");
                Connection con = DriverManager.getConnection(url, usr, pwd);
                Log.v("san","comp");
                s="0";
                Statement st = con.createStatement();
                ResultSet rsmon=st.executeQuery("select descp from eventlist where date_time=\""+daystr+"\";");
                while(rsmon.next())
                    mobileArray.add(rsmon.getString(1)+" ");
                Log.v("san",listviewstr);

                con.close();
            }
            catch(Exception E)
            {
                E.printStackTrace();

                s="1";
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(s.equals("1"))
            {
                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_LONG).show();
            }
            if(s.equals("0"))
            {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Table :"+listviewstr,Toast.LENGTH_LONG).show();
                listupdate(listviewstr);
                }
        }
    }
    public void listupdate(String listviewstr){
        adapter = new ArrayAdapter<String>(this,R.layout.listitems, mobileArray);

        ListView listView = (ListView) findViewById(R.id.items_in_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value=adapter.getItem(position).toString();
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class myTask2 extends AsyncTask<Void,Void,Void>
    {
        String s="";
        String url="jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr="admin";
        String pwd="123456789";
        String temp="";
        final ArrayList<CalendarDay> calday = new ArrayList<CalendarDay>();



        @Override
        protected void onPreExecute() {
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                Class.forName("com.mysql.jdbc.Driver");
                Log.v("san","pending");
                Connection con = DriverManager.getConnection(url, usr, pwd);
                Log.v("san","comp");
                s="0";
                Statement st = con.createStatement();
                ResultSet rsmon=st.executeQuery("select * from cd;");
                while(rsmon.next())
                    temp= (rsmon.getString(1)+" ");
                    String day =YearMonth.from(ZonedDateTime.parse(temp ,DateTimeFormatter.ofPattern( "dd-MM-yyyy" ))).toString();
                con.close();
            }
            catch(Exception E)
            {
                E.printStackTrace();

                s="1";
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(s.equals("1"))
            {
                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_LONG).show();
            }
            if(s.equals("0"))
            {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Table :"+listviewstr,Toast.LENGTH_LONG).show();
                single.addDecorator(new EventDecorator(Color.BLUE,calday));
            }
        }
    }

}


