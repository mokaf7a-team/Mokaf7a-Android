package com.resala.mokaf7a;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resala.mokaf7a.classes.Rules;

import static android.content.ContentValues.TAG;

public class SplashActivity extends AppCompatActivity {
    DatabaseReference rulesRef;
    public static Rules myRules;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        database = FirebaseDatabase.getInstance();
        rulesRef = database.getReference("Rules");
        rulesRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        myRules = dataSnapshot.getValue(Rules.class);
                        int SPLASH_DISPLAY_LENGTH = 500; // Duration of wait
                        new Handler()
                                .postDelayed(
                                        () -> {
                                            SplashActivity.this.startActivity(new Intent(getApplicationContext(), StarterActivity.class));
                                            SplashActivity.this.finish();
                                        },
                                        SPLASH_DISPLAY_LENGTH);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


    }
}
