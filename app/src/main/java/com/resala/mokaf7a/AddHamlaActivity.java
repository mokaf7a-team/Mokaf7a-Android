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
import com.resala.mokaf7a.classes.Hamla;

import java.util.Calendar;
import java.util.Locale;

import static com.resala.mokaf7a.LoginActivity.userBranch;

public class AddHamlaActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hamla);
        database = FirebaseDatabase.getInstance();

        hamlaDate_et = findViewById(R.id.hamlaDate_et);
        hamlaRoad_et = findViewById(R.id.hamlaRoad_et);
        girlsCount_et = findViewById(R.id.girlsCount_et);
        boysCount_et = findViewById(R.id.boysCount_et);

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
                                    AddHamlaActivity.this,
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
        hamalatRef = database.getReference("hamalat").push();
        hamalatRef.setValue(new Hamla(userBranch
                , Integer.parseInt(boysCount_et.getText().toString().trim())
                , Integer.parseInt(girlsCount_et.getText().toString().trim())
                , hamlaRoad_et.getText().toString().trim()
                , hamlaDate_et.getText().toString().trim()));
        Toast.makeText(this, "تم تسجيل الحملة بنجاح", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), AddCasesActivity.class);
        intent.putExtra("key", hamalatRef.getKey());
        startActivity(intent);
        finish();
    }
}