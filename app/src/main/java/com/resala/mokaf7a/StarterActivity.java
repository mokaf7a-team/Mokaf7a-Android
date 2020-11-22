package com.resala.mokaf7a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
    }

    public void goToSignup(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    public void goToLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void goToForm(View view) {
        startActivity(new Intent(getApplicationContext(), FormActivity.class));
    }
}