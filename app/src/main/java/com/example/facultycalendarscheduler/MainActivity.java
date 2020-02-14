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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    public static String listviewstr="";
    ProgressBar prgDialog;


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
        prgDialog=findViewById(R.id.loading);
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
            //new myTask().execute();
            Intent i=new Intent(MainActivity.this,Userlogin.class);

            startActivity(i);


        } else {

            Toast.makeText(MainActivity.this,"Authentication not success",Toast.LENGTH_SHORT).show();
            loginButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginv) {

            signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
//            if(signinstatus==1){
            //new myTask().execute();
            prgDialog.setVisibility(View.VISIBLE);
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

                 Toast.makeText(getApplicationContext(),"Table :"+listviewstr,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu
            (Menu menu) {
        getMenuInflater().inflate
                (R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected
            (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i1:
                Intent i = new Intent(this, Adminlogin.class);
                startActivity(i);
                break;

            case R.id.i2:
                androidx.appcompat.app.AlertDialog.Builder al =
                        new androidx.appcompat.app.AlertDialog.Builder(
                                MainActivity.this);
                al.setTitle("Team Details");
                al.setMessage("Roll No|||Name\nCB.EN.U4CSE17604|||Adityan\nCB.EN.U4CSE17611|||Atisha\nCB.EN.U4CSE17627|||Vinay\nCB.EN.U4CSE17637|||Uday Srinivas\nCB.EN.U4CSE17655|||Sanjay Lal");
                al.setCancelable(true);
                al.show();
                break;

            case R.id.i3:
                androidx.appcompat.app.AlertDialog.Builder a2 =
                        new androidx.appcompat.app.AlertDialog.Builder(
                                MainActivity.this);
                a2.setTitle("Team Details");
                a2.setMessage("This app lets faculty view calendar and timetable");
                a2.setCancelable(true);
                a2.show();
                break;
        }
        return true;
    }
}


