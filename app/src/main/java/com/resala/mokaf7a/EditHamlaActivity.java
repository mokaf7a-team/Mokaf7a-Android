package com.resala.mokaf7a;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class EditHamlaActivity extends AppCompatActivity {

    private static final String TAG = "hamalat";
    EditText hamlaDate_et;
    EditText hamlaRoad_et;
    EditText girlsCount_et;
    EditText boysCount_et;

    FirebaseDatabase database;
    DatePickerDialog picker;
    int day;
    int month;
    int year;
    DatabaseReference hamalatRef;

    String branch;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hamla);
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();

        String boys_count = intent.getStringExtra("boys_count");
        branch = intent.getStringExtra("branch");
        String girls_count = intent.getStringExtra("girls_count");
        String road = intent.getStringExtra("road");
        String date = intent.getStringExtra("date");
        key = intent.getStringExtra("key");

        hamlaDate_et = findViewById(R.id.hamlaDate_et);
        hamlaRoad_et = findViewById(R.id.hamlaRoad_et);
        girlsCount_et = findViewById(R.id.girlsCount_et);
        boysCount_et = findViewById(R.id.boysCount_et);

        hamlaDate_et.setText(date);
        hamlaRoad_et.setText(road);
        girlsCount_et.setText(girls_count);
        boysCount_et.setText(boys_count);

        final Calendar cldr = Calendar.getInstance(Locale.US);
        day = cldr.get(Calendar.DAY_OF_MONTH);
        month = cldr.get(Calendar.MONTH);
        year = cldr.get(Calendar.YEAR);
        hamlaDate_et.setInputType(InputType.TYPE_NULL);
        hamlaDate_et.setOnClickListener(
                v -> {
                    // date picker dialog
                    picker =
                            new DatePickerDialog(
                                    EditHamlaActivity.this,
                                    (view, year, monthOfYear, dayOfMonth) ->
                                            hamlaDate_et.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year),
                                    year,
                                    month,
                                    day);
                    picker.show();
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String boys = boysCount_et.getText().toString();
        if (TextUtils.isEmpty(boys)) {
            boysCount_et.setError("Required.");
            valid = false;
        } else {
            boysCount_et.setError(null);
        }

        String girls = girlsCount_et.getText().toString();
        if (TextUtils.isEmpty(girls)) {
            girlsCount_et.setError("Required.");
            valid = false;
        } else {
            girlsCount_et.setError(null);
        }

        String road = hamlaRoad_et.getText().toString();
        if (TextUtils.isEmpty(road)) {
            hamlaRoad_et.setError("Required.");
            valid = false;
        } else {
            hamlaRoad_et.setError(null);
        }

        String date = hamlaDate_et.getText().toString();
        if (TextUtils.isEmpty(date)) {
            hamlaDate_et.setError("Required.");
            valid = false;
        } else {
            hamlaDate_et.setError(null);
        }
        return valid;
    }

    public void close(View view) {
        finish();
    }

    public void AddHamla(View view) {
        if (!validateForm()) return;
        hamalatRef = database.getReference("hamalat").child(key);

        hamalatRef.child("branch").setValue(branch);
        hamalatRef.child("boys_count").setValue(Integer.parseInt(boysCount_et.getText().toString().trim()));
        hamalatRef.child("girls_count").setValue(Integer.parseInt(girlsCount_et.getText().toString().trim()));
        hamalatRef.child("road").setValue(hamlaRoad_et.getText().toString().trim());
        hamalatRef.child("date").setValue(hamlaDate_et.getText().toString().trim());

//        hamalatRef.setValue(new Hamla(branch
//                , Integer.parseInt(boysCount_et.getText().toString().trim())
//                , Integer.parseInt(girlsCount_et.getText().toString().trim())
//                , hamlaRoad_et.getText().toString().trim()
//                , hamlaDate_et.getText().toString().trim()));
        Toast.makeText(this, "تم تعديل الحملة بنجاح", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), AddCasesActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
        finish();
    }
}