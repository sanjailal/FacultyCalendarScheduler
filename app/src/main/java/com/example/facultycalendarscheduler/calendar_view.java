package com.example.facultycalendarscheduler;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

//import org.threeten.bp.format.DateTimeFormatter;


public class calendar_view extends AppCompatActivity implements OnDateSelectedListener {

    private String username = "";
    private String daystr = "";
    private ProgressBar prgbar;
   // private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
   //public String listviewstr="";
   private ArrayAdapter adapter;
    @BindView(R2.id.calendarView_single) MaterialCalendarView single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        ButterKnife.bind(this);
        prgbar = findViewById(R.id.loadingcal);
        single.setOnDateChangedListener(this);
       // loadingProgressBar=(ProgressBar)findViewById(R.id.loadingcal);
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Toast.makeText(calendar_view.this, uid, Toast.LENGTH_LONG).show();
        username = user.getEmail();
        Log.d("san", username, null);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //final String text = selected ? FORMATTER.format(date.getDate()) : "No Selection";
        int curdate =selected ? (date.getDay()):0;
        int curmonth =selected ? (date.getMonth()):0;
        int curyr=selected ? (date.getYear()):0;
        daystr =curdate+"-"+curmonth+"-"+curyr;
        new myTask().execute();
        Toast.makeText(calendar_view.this, "Please Wait....", Toast.LENGTH_LONG).show();
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
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

    private class myTask extends AsyncTask<Void,Void,Void>
    {
        String s="";
        String url="jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr="admin";
        String pwd="123456789";
        private ArrayList<String> mobileArray =new ArrayList<>();




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Connection con = null;
            try{

                Class.forName("com.mysql.jdbc.Driver");
                Log.v("san","pending");
                con = DriverManager.getConnection(url, usr, pwd);
                Log.v("san","comp");
                s="0";
                Statement st = con.createStatement();
                String viewcal = "select descp from eventff where date_time=\"" + daystr + "\" and username=\"" + username + "\";";
                ResultSet rsmon = st.executeQuery(viewcal);
                while(rsmon.next())
                    mobileArray.add(rsmon.getString(1)+" ");
            }
            catch(Exception E) {
                E.printStackTrace();

                s="1";
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
            if(s.equals("1"))
            {
                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_LONG).show();
            }
            if(s.equals("0"))
            {

                listupdate(mobileArray);
                }
        }
    }
    public void listupdate(ArrayList<String > mobileArray){
        adapter = new ArrayAdapter<>(this,R.layout.listitems, mobileArray);

        ListView listView =  findViewById(R.id.items_in_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value=adapter.getItem(position).toString();
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });
    }

}


