package com.resala.mokaf7a;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resala.mokaf7a.adapters.CasesAdapter;
import com.resala.mokaf7a.classes.Case;

import java.util.ArrayList;

public class AddCasesActivity extends AppCompatActivity {
    String key;
    FirebaseDatabase database;
    DatabaseReference hamalatRef;
    ValueEventListener hamalatListener;

    EditText place_et;
    EditText blanketsCount_et;
    EditText clothesCount_et;
    EditText mealsCount_et;
    EditText caseName_et;

    CasesAdapter adapter;
    ArrayList<Case> casesItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cases);
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        hamalatRef = database.getReference("hamalat").child(key).child("cases");

        place_et = findViewById(R.id.place_et);
        blanketsCount_et = findViewById(R.id.blanketsCount_et);
        clothesCount_et = findViewById(R.id.clothesCount_et);
        mealsCount_et = findViewById(R.id.mealsCount_et);
        caseName_et = findViewById(R.id.caseName_et);

        RecyclerView recyclerView = findViewById(R.id.casesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CasesAdapter(casesItems, getApplicationContext(), key);
        recyclerView.setAdapter(adapter);

        hamalatListener = hamalatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                casesItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Case aCase = snapshot.getValue(Case.class);
                    aCase.setKey(snapshot.getKey());
                    casesItems.add(aCase);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean validateForm() {
        boolean valid = true;

        String place = place_et.getText().toString();
        if (TextUtils.isEmpty(place)) {
            place_et.setError("Required.");
            valid = false;
        } else {
            place_et.setError(null);
        }
        String meals = mealsCount_et.getText().toString();
        if (TextUtils.isEmpty(meals)) {
            mealsCount_et.setError("Required.");
            valid = false;
        } else {
            mealsCount_et.setError(null);
        }
        String clothes = clothesCount_et.getText().toString();
        if (TextUtils.isEmpty(clothes)) {
            clothesCount_et.setError("Required.");
            valid = false;
        } else {
            clothesCount_et.setError(null);
        }
        String blankets = blanketsCount_et.getText().toString();
        if (TextUtils.isEmpty(blankets)) {
            blanketsCount_et.setError("Required.");
            valid = false;
        } else {
            blanketsCount_et.setError(null);
        }
        return valid;

    }

    public void AddCase(View view) {
        if (!validateForm()) return;

        DatabaseReference casesRef = hamalatRef.push();
        casesRef.setValue(new Case(
                place_et.getText().toString().trim(),
                caseName_et.getText().toString().trim(),
                Integer.parseInt(mealsCount_et.getText().toString().trim()),
                Integer.parseInt(blanketsCount_et.getText().toString().trim()),
                Integer.parseInt(clothesCount_et.getText().toString().trim())));

        blanketsCount_et.setText("");
        clothesCount_et.setText("");
        mealsCount_et.setText("");
        caseName_et.setText("");
        place_et.setText("");

    }

    public void close(View view) {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hamalatRef != null && hamalatListener != null) {
            hamalatRef.removeEventListener(hamalatListener);
        }
    }
}