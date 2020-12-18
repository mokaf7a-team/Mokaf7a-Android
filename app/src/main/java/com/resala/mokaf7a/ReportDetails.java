package com.resala.mokaf7a;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import static com.resala.mokaf7a.LoginActivity.branches;
import static com.resala.mokaf7a.LoginActivity.isMrkzy;
import static com.resala.mokaf7a.fragments.ShowDataFragment.t3amolTypes;

public class ReportDetails extends AppCompatActivity {
    FirebaseDatabase database;
    EditText reportDescription_et;
    EditText reporterFirstFeedback_et;
    EditText reporterSecondFeedbackTV;
    EditText reporterFoodNum_et;
    EditText reporterClothesNum_et;
    EditText reporterBlanketsNum_et;
    EditText reporterFeedbackDate_et;
    EditText reporterCasesNum_et;
    EditText reporterFeedbackDetails_et;
    AutoCompleteTextView reporterFeedbackType_et;
    EditText reporterPhoneTV;

    TextView reporterPlaceTV;
    TextView reporterGenderTV;
    TextView reporterSeenTV;
    TextView reporterAddressTV;
    TextView reporterNameTV;
    TextView reporterDateTV;
    TextView reporterId;

    /* **********added version 3**********************/
    EditText reporterAreaTV;
    EditText caseName_et;
    AutoCompleteTextView reporterBranch_et;
    /* ************************************************/

    String key;
    DatabaseReference reportsRef;
    int day;
    int month;
    int year;
    DatePickerDialog picker;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        String nameText = intent.getStringExtra("name");
        String phoneText = intent.getStringExtra("phone");
        String addressText = intent.getStringExtra("address");
        String genderText = intent.getStringExtra("gender");
        String seenText = intent.getStringExtra("seen");
        String dateText = intent.getStringExtra("date");
        String stateText = intent.getStringExtra("state");
        String notesText = intent.getStringExtra("notes");
        String idText = intent.getStringExtra("id");
        String first_feedbackText = intent.getStringExtra("first_feedback");
        String second_feedbackText = intent.getStringExtra("second_feedback");

        String blankets = intent.getStringExtra("blankets");
        String case_num = intent.getStringExtra("case_num");
        String clothes_num = intent.getStringExtra("clothes_num");
        String meals = intent.getStringExtra("meals");
        String help_date = intent.getStringExtra("help_date");
        String feed_back_type = intent.getStringExtra("feed_back_type");
        String feed_back = intent.getStringExtra("feed_back");

        /* **********added version 3**********************/
        String branchText = intent.getStringExtra("branch");
        String areaText = intent.getStringExtra("area");
        String case_nameText = intent.getStringExtra("case_name");
        reporterAreaTV = findViewById(R.id.reporterAreaTV);
        reporterBranch_et = findViewById(R.id.reporterBranch_et);
        caseName_et = findViewById(R.id.caseName_et);
        /* ************************************************/

        reporterDateTV = findViewById(R.id.reporterDateTV);
        reporterNameTV = findViewById(R.id.reporterNameTV);
        reporterPhoneTV = findViewById(R.id.reporterPhoneTV);
        reporterAddressTV = findViewById(R.id.reporterAddressTV);
        reporterSeenTV = findViewById(R.id.reporterSeenTV);
        reporterGenderTV = findViewById(R.id.reporterGenderTV);
        reporterPlaceTV = findViewById(R.id.reporterPlaceTV);
        reportDescription_et = findViewById(R.id.reportDescription_et);
        reporterId = findViewById(R.id.reporterId);
        reporterFirstFeedback_et = findViewById(R.id.reporterFirstFeedback_et);
        reporterSecondFeedbackTV = findViewById(R.id.reporterSecondFeedbackTV);

        reporterFeedbackType_et = findViewById(R.id.reporterFeedbackType_et);
        reporterFeedbackDetails_et = findViewById(R.id.reporterFeedbackDetails_et);
        reporterCasesNum_et = findViewById(R.id.reporterCasesNum_et);
        reporterFeedbackDate_et = findViewById(R.id.reporterFeedbackDate_et);
        reporterBlanketsNum_et = findViewById(R.id.reporterBlanketsNum_et);
        reporterClothesNum_et = findViewById(R.id.reporterClothesNum_et);
        reporterFoodNum_et = findViewById(R.id.reporterFoodNum_et);

        reporterDateTV.setText(dateText);
        reporterNameTV.setText(nameText);
        reporterPhoneTV.setText(phoneText);
        reporterAddressTV.setText(addressText);
        reporterSeenTV.setText(seenText);
        reporterGenderTV.setText(genderText);
        reporterPlaceTV.setText(stateText);
        reportDescription_et.setText(notesText);
        reporterId.setText(idText);
        reporterFirstFeedback_et.setText(first_feedbackText);
        reporterSecondFeedbackTV.setText(second_feedbackText);

        reporterFeedbackType_et.setText(feed_back_type);
        reporterFeedbackDetails_et.setText(feed_back);
        reporterCasesNum_et.setText(case_num);
        reporterFeedbackDate_et.setText(help_date);
        reporterBlanketsNum_et.setText(blankets);
        reporterClothesNum_et.setText(clothes_num);
        reporterFoodNum_et.setText(meals);

        /* **********added version 3**********************/
        caseName_et.setText(case_nameText);
        reporterBranch_et.setText(branchText);
        reporterAreaTV.setText(areaText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, branches);
        reporterBranch_et.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, t3amolTypes);
        reporterFeedbackType_et.setAdapter(adapter2);
        /* ************************************************/

        key = intent.getStringExtra("key");
        reportsRef = database.getReference("reports");

        final Calendar cldr = Calendar.getInstance(Locale.US);
        day = cldr.get(Calendar.DAY_OF_MONTH);
        month = cldr.get(Calendar.MONTH);
        year = cldr.get(Calendar.YEAR);
        reporterFeedbackDate_et.setInputType(InputType.TYPE_NULL);
        reporterFeedbackDate_et.setOnClickListener(
                v -> {
                    // date picker dialog
                    picker =
                            new DatePickerDialog(
                                    ReportDetails.this,
                                    (view, year, monthOfYear, dayOfMonth) ->
                                            reporterFeedbackDate_et.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year),
                                    year,
                                    month,
                                    day);
                    picker.show();
                });
    }

    public void editReport(View view) {
        reportsRef.child(key).child("first_feedback").setValue(reporterFirstFeedback_et.getText().toString().trim());
        reportsRef.child(key).child("second_feedback").setValue(reporterSecondFeedbackTV.getText().toString().trim());
        reportsRef.child(key).child("feed_back").setValue(reporterFeedbackDetails_et.getText().toString().trim());
        reportsRef.child(key).child("help_date").setValue(reporterFeedbackDate_et.getText().toString().trim());
        reportsRef.child(key).child("feed_back_type").setValue(reporterFeedbackType_et.getText().toString().trim());
        reportsRef.child(key).child("case_num").setValue(reporterCasesNum_et.getText().toString().trim());
        reportsRef.child(key).child("blankets").setValue(reporterBlanketsNum_et.getText().toString().trim());
        reportsRef.child(key).child("meals").setValue(reporterFoodNum_et.getText().toString().trim());
        reportsRef.child(key).child("clothes_num").setValue(reporterClothesNum_et.getText().toString().trim());
        reportsRef.child(key).child("notes").setValue(reportDescription_et.getText().toString().trim());

        /* **********added version 3**********************/
        reportsRef.child(key).child("case_name").setValue(caseName_et.getText().toString().trim());
        if (isMrkzy) {
            reportsRef.child(key).child("branch").setValue(reporterBranch_et.getText().toString().trim());
            reportsRef.child(key).child("area").setValue(reporterAreaTV.getText().toString().trim());
        }
        /* ************************************************/
        Toast.makeText(this, "تم تعديل البلاغ", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void close(View view) {
        finish();
    }
}