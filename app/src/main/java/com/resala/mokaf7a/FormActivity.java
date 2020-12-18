package com.resala.mokaf7a;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resala.mokaf7a.classes.Report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.resala.mokaf7a.LoginActivity.branches;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static String[] gender = {
            "مسن فوق ال60",
            "مسنة فوق ال60",
            "رجل شاب",
            "امراة شابة",
            "طفل",
            "اكثر من شخص"
    };
    public static String[] seen = {
            "مرة",
            "اكثر من مرة"
    };
    public static String[] place = {
            "بدون ماوي",
            "بماوي"
    };
    EditText reportDescription_et;
    EditText reporterPhoneTV;
    Spinner reporterPlaceTV;
    Spinner reporterGenderTV;
    Spinner reporterSeenTV;
    Spinner reporterBranchTV;
    Spinner reporterAreaTV;
    EditText reporterAddressTV;
    EditText reporterNameTV;

    int day;
    int month;
    int year;
    DatabaseReference reportsRef;
    DatabaseReference areasRef;

    FirebaseDatabase database;

    ArrayList<String> areas = new ArrayList<>();
    ArrayAdapter<String> ae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        database = FirebaseDatabase.getInstance();

        reporterNameTV = findViewById(R.id.reporterNameTV);
        reporterPhoneTV = findViewById(R.id.reporterPhoneTV);
        reporterAddressTV = findViewById(R.id.reporterAddressTV);
        reporterSeenTV = findViewById(R.id.reporterSeenTV);
        reporterGenderTV = findViewById(R.id.reporterGenderTV);
        reporterBranchTV = findViewById(R.id.reporterBranchTV);
        reporterAreaTV = findViewById(R.id.reporterAreaTV);
        reporterPlaceTV = findViewById(R.id.reporterPlaceTV);
        reportDescription_et = findViewById(R.id.reportDescription_et);

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        reporterGenderTV.setAdapter(aa);

        ArrayAdapter<String> ab = new ArrayAdapter<>(this, R.layout.spinner_item, seen);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        reporterSeenTV.setAdapter(ab);

        ArrayAdapter<String> ac = new ArrayAdapter<>(this, R.layout.spinner_item, branches);
        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        reporterBranchTV.setAdapter(ac);
        reporterBranchTV.setOnItemSelectedListener(this);

        ArrayAdapter<String> ad = new ArrayAdapter<>(this, R.layout.spinner_item, place);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        reporterPlaceTV.setAdapter(ad);

        ae = new ArrayAdapter<>(this, R.layout.spinner_item, areas);
        ae.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        reporterAreaTV.setAdapter(ae);


    }

    public void addReport(View view) {
        if (!validateForm()) return;
        reportsRef = database.getReference("reports").push();
        final Calendar cldr = Calendar.getInstance(Locale.US);
        day = cldr.get(Calendar.DAY_OF_MONTH);
        month = cldr.get(Calendar.MONTH);
        year = cldr.get(Calendar.YEAR);
        String todayDate = year + "-" + (month + 1) + "-" + day;
        reportsRef.setValue(new Report(reporterAddressTV.getText().toString().trim()
                , reporterAreaTV.getSelectedItem().toString()
                , reporterBranchTV.getSelectedItem().toString()
                , todayDate
                , reporterGenderTV.getSelectedItem().toString()
                , reporterNameTV.getText().toString().trim()
                , reportDescription_et.getText().toString().trim()
                , reporterPhoneTV.getText().toString().trim()
                , reportsRef.getKey()
                , reporterSeenTV.getSelectedItem().toString()
                , reporterPlaceTV.getSelectedItem().toString()));
        Toast.makeText(this, "تم تسجيل البلاغ بنجاح", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateForm() {
        boolean valid = true;

        String address = reporterAddressTV.getText().toString();
        if (TextUtils.isEmpty(address)) {
            reporterAddressTV.setError("Required.");
            valid = false;
        } else {
            reporterAddressTV.setError(null);
        }

        String name = reporterNameTV.getText().toString();
        if (TextUtils.isEmpty(name)) {
            reporterNameTV.setError("Required.");
            valid = false;
        } else {
            reporterNameTV.setError(null);
        }

        String phone = reporterPhoneTV.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            reporterPhoneTV.setError("Required.");
            valid = false;
        } else {
            reporterPhoneTV.setError(null);
        }

        return valid;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        areasRef = database.getReference("Areas").child(adapterView.getItemAtPosition(i).toString());
        areasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                areas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    areas.add((String) snapshot.getValue());
                }
                ae.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}