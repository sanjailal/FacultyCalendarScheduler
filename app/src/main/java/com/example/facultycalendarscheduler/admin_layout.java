package com.example.facultycalendarscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_layout extends AppCompatActivity implements View.OnClickListener {

    private Button addfaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_layout);
        addfaculty = findViewById(R.id.addFaculty);
        addfaculty.setEnabled(true);
        addfaculty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i= view.getId();
        if(i==R.id.addFaculty){
            Intent Signup = new Intent(this,signUp.class);
            startActivity(Signup);
        }
    }
}
