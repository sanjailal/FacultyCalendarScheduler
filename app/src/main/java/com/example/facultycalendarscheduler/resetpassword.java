package com.example.facultycalendarscheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText resetemail;
    private Button reset;
    private EditText retype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        resetemail = findViewById(R.id.resetpassword);
        reset = findViewById(R.id.reset);
        reset.setEnabled(true);
        reset.setOnClickListener(this);
        retype=findViewById(R.id.resetpassword2);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if ((i == R.id.reset)&&(reset.getText().equals(retype.getText()))) {
            reset();
        }else{
            Toast.makeText(this, "Please check the retype password", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {
        String email = resetemail.getText().toString();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("san", "Email sent.");
                            Toast.makeText(resetpassword.this, "Check your email....", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
