package com.example.facultycalendarscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class admin_layout extends AppCompatActivity implements View.OnClickListener {

    private Button addfaculty;
    private Button addevent;
    private Button resetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_layout);
        addfaculty = findViewById(R.id.addFaculty);
        addfaculty.setEnabled(true);
        addfaculty.setOnClickListener(this);
        addevent = findViewById(R.id.addevent);
        addevent.setEnabled(true);
        addevent.setOnClickListener(this);
        resetpassword = findViewById(R.id.resetpassword);
        resetpassword.setEnabled(true);
        resetpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i= view.getId();
        if(i==R.id.addFaculty){
            Intent Signup = new Intent(this,signUp.class);
            startActivity(Signup);
        }
        if (i == R.id.addevent) {
            Intent addevent = new Intent(this, addevent.class);
            startActivity(addevent);
        }
        if (i == R.id.resetpassword) {
            Intent resetpasintent = new Intent(this, resetpassword.class);
            startActivity(resetpasintent);
        }
    }
}
