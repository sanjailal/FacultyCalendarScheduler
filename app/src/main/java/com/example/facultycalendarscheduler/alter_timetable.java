package com.example.facultycalendarscheduler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class alter_timetable extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private String username;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private EditText ed4;
    private int dayselected;
    private ProgressBar prg;
    private String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_timetable);
        FirebaseUser user;
        Spinner sp;
        Button btn;
        prg = findViewById(R.id.progressBar2);
        prg.setVisibility(View.INVISIBLE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getEmail();
         sp=findViewById(R.id.spinner);
         ed1=findViewById(R.id.editText3);
         ed2=findViewById(R.id.editText);
         ed3=findViewById(R.id.editText2);
         ed4=findViewById(R.id.editText4);
         btn=findViewById(R.id.button2);
         btn.setOnClickListener(this);
        sp.setOnItemSelectedListener(this);
        btn.setOnClickListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,days);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp.setAdapter(aa);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    dayselected=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //when nothing is selected
    }

    @Override
    public void onClick(View v) {
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private String s = "";
        private String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        private String usr = "admin";
        private String pwd = "123456789";
        private int day = dayselected;
        private int starthr = Integer.parseInt(ed1.getText().toString());
        private int startmin = Integer.parseInt(ed2.getText().toString());
        private int endhr = Integer.parseInt(ed3.getText().toString());
        private int endmin = Integer.parseInt(ed4.getText().toString());


        @Override
        protected void onPreExecute() {
            prg.setVisibility(View.VISIBLE);
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
                String alterreqpush = "insert into flag values(1," + day + "," + starthr + "," + startmin + "," + endhr + "," + endmin + ",\"" + username + "\");";
                int sta = st.executeUpdate(alterreqpush);
                if (sta == 1) {
                    Log.v("san", "pass", null);
                } else {
                    Log.v("san", "fail", null);
                }
            } catch (Exception E) {
                Log.e("error", String.valueOf(E));

                s = "1";
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    Log.e("error", String.valueOf(e));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            prg.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);
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
