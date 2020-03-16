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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        usermail = findViewById(R.id.usermail);
        event = findViewById(R.id.event);
        Button add;
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
            new MyTask().execute();
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String s = "";
        String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        String usr = "admin";
        String pwd = "123456789";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //add progressbar

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Connection con = null;
            String insertevent = "insert into eventff values(\"" + date + "\",\"" + event.getText().toString() + "\",\"" + usermail.getText().toString() + "\");";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Log.v("san", "pending");
                con = DriverManager.getConnection(url, usr, pwd);
                Log.v("san", "comp");
                s = "0";
                Statement st = con.createStatement();
                Log.v("san", "before");
                Log.v("san", insertevent, null);
                int sta = st.executeUpdate(insertevent);
                if (sta > 0)
                    Log.v("san", "success");
                else
                    Log.v("san", "fail");

            } catch (Exception E) {
                Log.e("error", String.valueOf(E));

                s = "1";
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    Log.e("error", String.valueOf(e));
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
