package com.example.facultycalendarscheduler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class personaldetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String[] designationlist = {"Professor", "Assistant Professor", "Associate Professor"};
    private int listno;
    private String username;
    private EditText address;
    private EditText phone;
    private EditText email;
    private Button savebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldetails);
        Spinner spin = findViewById(R.id.designation);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, designationlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.mail);
        savebutton = findViewById(R.id.button);
        savebutton.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        listno = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //when nothing is selected
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.button) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            username = user.getEmail();
            new MyTask().execute();
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private String s = "";
        private String url = "jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/SE";
        private String usr = "admin";
        private String pwd = "123456789";


        @Override
        protected void onPreExecute() {
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
                Log.v("san", "before");
                // need to add mail and the spinner menu
                int sta = st.executeUpdate("insert into personaldetails values(\"" + email.getText().toString() + "\",\"" + phone.getText().toString() + "\",\"" + address.getText().toString() + "\",\"" + designationlist[listno] + "\",\"" + username + "\");");
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
