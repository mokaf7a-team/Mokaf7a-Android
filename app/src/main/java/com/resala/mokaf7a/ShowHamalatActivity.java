package com.resala.mokaf7a;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resala.mokaf7a.adapters.HamalatAdapter;
import com.resala.mokaf7a.classes.Hamla;

import java.util.ArrayList;

public class ShowHamalatActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference hamalatRef;
    ValueEventListener hamalatListener;

    HamalatAdapter adapter;
    ArrayList<Hamla> hamalatItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hamalat);
        database = FirebaseDatabase.getInstance();
        hamalatRef = database.getReference("hamalat");

        RecyclerView recyclerView = findViewById(R.id.hamalatRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new HamalatAdapter(hamalatItems, getApplicationContext());
        recyclerView.setAdapter(adapter);

        hamalatListener = hamalatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hamalatItems.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Hamla hamla = snapshot.getValue(Hamla.class);
                    hamla.setKey(snapshot.getKey());
                    hamalatItems.add(hamla);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hamalatRef != null && hamalatListener != null) {
            hamalatRef.removeEventListener(hamalatListener);
        }
    }
}