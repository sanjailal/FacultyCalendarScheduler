package com.example.facultycalendarscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    private Button adminButton;
    public static String listviewstr="";
    ProgressDialog prgDialog;
    int signinstatus =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        usernameEditText = findViewById(R.id.username);
        String username = usernameEditText.getText().toString();
        passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();
        loginButton = findViewById(R.id.loginv);
        loadingProgressBar = findViewById(R.id.loading);
        loginButton.setEnabled(true);
        loginButton.setOnClickListener(this);
        adminButton = findViewById(R.id.admin_button);
        adminButton.setEnabled(true);
        adminButton.setOnClickListener(this);



    }

    private void signIn(String username, String password) {
        Log.d("d", "signIn:" + username);
        if (!validateForm()) {
            Toast.makeText(MainActivity.this, "Validation failed.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        loadingProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        loadingProgressBar.setVisibility(View.GONE);

                        // ...
                    }
                });

    }
    private boolean validateForm() {
        boolean valid = true;

        String email = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            usernameEditText.setError("Required.");
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        loadingProgressBar.setVisibility(View.GONE);
        if (user != null) {

            loginButton.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this,"Authentication success",Toast.LENGTH_SHORT).show();
            Log.d("san","success",null);
            // put an intent here to jump to your next layout @uday
            //*******************************************************************************************************
            //Intent calintent = new Intent(this, calendar_view.class);
            //this.startActivity ( calintent );
            new myTask().execute();
            //signinstatus =1;

        } else {

            Toast.makeText(MainActivity.this,"Authentication not success",Toast.LENGTH_SHORT).show();

            loginButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginv) {

            //signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
//            if(signinstatus==1){
            new myTask().execute();
//            }

        }
        if(i==R.id.admin_button){
            Intent adminintent = new Intent(this,Adminlogin.class);
            this.startActivity ( adminintent );
        }
    }


    public class myTask extends AsyncTask<Void,Void,Void>
    {
        String s="";
        String url="jdbc:mysql://database-1.cyn8mvqyzihy.us-east-1.rds.amazonaws.com:3306/sed";
        String usr="admin";
        String pwd="123456789";



        @Override
        protected void onPreExecute() {

//            prgDialog = new ProgressDialog(
//                    studentview.this);
//            prgDialog.setMessage
//                    ("Gathering Details");
//            // prgDialog.setIndeterminate(false);
//            prgDialog.setProgressStyle
//                    (ProgressDialog.STYLE_SPINNER);
//            prgDialog.setCancelable(false);
//            prgDialog.show();

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
                ResultSet rsmon=st.executeQuery("(select * from listd);");
                while(rsmon.next())
                    listviewstr += (rsmon.getString(1)+" ");
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

                prgDialog = new ProgressDialog(
                        MainActivity.this);
                prgDialog.setMessage
                        ("Gathering Details");
                // prgDialog.setIndeterminate(false);
                prgDialog.setProgressStyle
                        (ProgressDialog.STYLE_SPINNER);
                prgDialog.setCancelable(false);
                prgDialog.show();

                //  Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainActivity.this,calendar_view.class);
                i.putExtra("STRING_I_NEED", listviewstr);
//                i.putExtra("tue",rstue);
//                i.putExtra("wed",rswed);
//                i.putExtra("thu",rsthu);
//                i.putExtra("fri",rsfri);
                startActivity(i);
                 Toast.makeText(getApplicationContext(),"Table :"+listviewstr,Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(" Timetable")
                        .setMessage(listviewstr)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                prgDialog.dismiss();

            }


        }
    }
}


