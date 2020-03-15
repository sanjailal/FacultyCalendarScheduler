package com.example.facultycalendarscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Userlogin extends AppCompatActivity implements View.OnClickListener {
    ImageButton b1,b2;
    Button addpersonaldetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        b1=findViewById(R.id.imageButton);
        b2=findViewById(R.id.imageButton1);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        addpersonaldetails = findViewById(R.id.personaldetails);
        addpersonaldetails.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==b1){
            Intent i= new Intent(this,calendar_view.class);
            startActivity(i);
        }
        if(view==b2){
            Intent i=new Intent(this,Timetable.class);
            startActivity(i);
        }
        if (view == addpersonaldetails) {
            Intent perdetails = new Intent(this, personaldetails.class);
            startActivity(perdetails);
        }
    }
    @Override
    public boolean onCreateOptionsMenu
            (Menu menu) {
        getMenuInflater().inflate
                (R.menu.logout, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected
            (MenuItem item) {

                Intent i1=new Intent(this,MainActivity.class);
                startActivity(i1);

        return true;
    }
}
