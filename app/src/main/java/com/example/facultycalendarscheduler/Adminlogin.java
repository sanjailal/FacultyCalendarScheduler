package com.example.facultycalendarscheduler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Adminlogin extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        usernameEditText = findViewById(R.id.username);
        String username = usernameEditText.getText().toString();
        passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();
        loginButton = findViewById(R.id.loginv);
        loadingProgressBar = findViewById(R.id.loading);
        loginButton.setEnabled(true);
        loginButton.setOnClickListener(this);
    }
    private void signIn(String username, String password) {
        Log.d("das",username+password,null);
        if(username.equals("admin@gmail.com")&& password.equals("admin01")){
            Intent Adminlayout =new Intent(this,admin_layout.class);
            this.startActivity(Adminlayout);
        }
        else{
            Toast.makeText(Adminlogin.this,"Login Failed",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.loginv){
            signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());

        }
    }

}
