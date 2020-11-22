package com.resala.mokaf7a;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int SPLASH_DISPLAY_LENGTH = 1000; // Duration of wait
        new Handler()
                .postDelayed(
                        () -> {
                            SplashActivity.this.startActivity(new Intent(getApplicationContext(), StarterActivity.class));
                            SplashActivity.this.finish();
                        },
                        SPLASH_DISPLAY_LENGTH);
  }
}
