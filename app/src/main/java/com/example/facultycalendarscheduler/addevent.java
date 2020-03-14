package com.example.facultycalendarscheduler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class addevent extends AppCompatActivity implements View.OnClickListener {
    private EditText usermail;
    private EditText event;
    private String date;
    private Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        usermail = findViewById(R.id.usermail);
        event = findViewById(R.id.event);
        add = findViewById(R.id.add);
        add.setEnabled(true);
        add.setOnClickListener(addevent.this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.add) {
            DatePicker datePicker = findViewById(R.id.seldate);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            date = day + "-" + month + "-" + year;
            Log.v("san", date);
            new myTask().execute();
        }
    }

    private class myTask extends AsyncTask<Void, Void, Void> {
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
                Log.v("san", "before");
                // ResultSet rs = st.executeQuery("insert into eventff values(\"2-2-2020\",\"Class Cog\",\"soft@gmail.com\");");
                String insertevent = "insert into eventff values(\"" + date + "\",\"" + event.getText().toString() + "\",\"" + usermail.getText().toString() + "\");";
                int sta = st.executeUpdate(insertevent);
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

                Toast.makeText(getApplicationContext(), "Added succesfully", Toast.LENGTH_LONG).show();
            }
        }
    }

}
