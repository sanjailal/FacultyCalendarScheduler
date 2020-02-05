package com.example.facultycalendarscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    private Button adminButton;

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
            // put an intent here to jump to your next layout @uday
            //*******************************************************************************************************
            Intent calintent = new Intent(this, calendar_view.class);
            this.startActivity ( calintent );

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

        }
        if(i==R.id.admin_button){
            Intent adminintent = new Intent(this,Adminlogin.class);
            this.startActivity ( adminintent );
        }
    }
}


