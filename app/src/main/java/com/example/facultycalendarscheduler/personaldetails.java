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
    EditText address;
    EditText phone;
    EditText email;
    Button savebutton;

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
        Toast.makeText(getApplicationContext(), designationlist[i], Toast.LENGTH_LONG).show();
        listno = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.button) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            username = user.getEmail();
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
                // need to add mail and the spinner menu
                int sta = st.executeUpdate("insert into personaldetails values(\"" + email.getText().toString() + "\",\"" + phone.getText().toString() + "\",\"" + address.getText().toString() + "\",\"" + designationlist[listno] + "\",\"" + username + "\");");
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
