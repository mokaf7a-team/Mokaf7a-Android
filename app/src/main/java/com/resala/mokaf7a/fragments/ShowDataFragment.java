package com.resala.mokaf7a.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resala.mokaf7a.R;
import com.resala.mokaf7a.adapters.ReportsAdapter;
import com.resala.mokaf7a.classes.Report;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;
import static com.resala.mokaf7a.LoginActivity.branches;
import static com.resala.mokaf7a.LoginActivity.isMrkzy;
import static com.resala.mokaf7a.LoginActivity.userBranch;

public class ShowDataFragment extends Fragment {
    Spinner spin;
    Spinner branchSpin;
    TextView textView;

    View view;
    FirebaseDatabase database;
    public static final String[] t3amolTypes = {
            "الكل",
            "تم التعامل",
            "غير مستحق",
            "مش موجود",
            "مكرر",
            "محافظات",
            "جاري التعامل",
            "تعامل اخر"
    };
    ImageButton refresh_btn;
    boolean unFinishedTwasolFilter;
    CheckBox unFinishedTwasolcheckbox;

    ReportsAdapter adapter;
    ArrayList<Report> reportItems = new ArrayList<>();

    DatabaseReference reportsRef;
    ValueEventListener reportsListener;

    @Override
    public void onResume() {
        super.onResume();
        if (!isMrkzy) {
            textView.setVisibility(View.GONE);
            branchSpin.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            branchSpin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_data, container, false);

        spin = view.findViewById(R.id.t3amolTypeSpinner);
        branchSpin = view.findViewById(R.id.branchSpinner);
        textView = view.findViewById(R.id.textView24);

        database = FirebaseDatabase.getInstance();
        ArrayAdapter<String> aa = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, t3amolTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin.setSelection(0);

        ArrayAdapter<String> ab = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, branches);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Setting the ArrayAdapter data on the Spinner
        branchSpin.setAdapter(ab);
        branchSpin.setSelection(9);

        unFinishedTwasolcheckbox = view.findViewById(R.id.allReportscheckbox);
        unFinishedTwasolcheckbox.setOnClickListener(v -> unFinishedTwasolFilter = unFinishedTwasolcheckbox.isChecked());

        RecyclerView recyclerView = view.findViewById(R.id.reportsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReportsAdapter(reportItems, getContext());
        recyclerView.setAdapter(adapter);

        refresh_btn = view.findViewById(R.id.refresh_btn);
        refresh_btn.setOnClickListener(v -> {
            refreshReports();
        });
        return view;

    }

    private void refreshReports() {
        reportsRef = database.getReference("reports");

        reportsListener = reportsRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reportItems.clear();
                        adapter.notifyDataSetChanged();

                        int current_id = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Report report = snapshot.getValue(Report.class);
                            assert report != null;

                            current_id++;
                            report.setId(current_id);
                            if (isMrkzy && !branchSpin.getSelectedItem().toString().equals(branches[9]) && !report.branch.trim().equals(branchSpin.getSelectedItem().toString()))
                                continue;

                            if (!isMrkzy && !report.branch.trim().equals(userBranch))
                                continue;

                            if (unFinishedTwasolFilter) {
                                if (!(report.first_feedback == null || report.first_feedback.trim().isEmpty()))
                                    continue;
                            }

                            if (spin.getSelectedItem().toString().equals(t3amolTypes[0])) //الكل
                                reportItems.add(report);
                            else if (report.feed_back_type == null || report.feed_back_type.trim().isEmpty()) //جاري التعامل
                            {
                                if (spin.getSelectedItem().toString().equals(t3amolTypes[6]))
                                    reportItems.add(report);
                            } else if (!spin.getSelectedItem().toString().equals(t3amolTypes[7]) &&
                                    Arrays.asList(t3amolTypes).contains(report.feed_back_type.trim())) {
                                //مش اخري .. تعامل موجود في الانواع .. هل هو مطابق ؟
                                if (report.feed_back_type.trim().equals(spin.getSelectedItem().toString()))
                                    reportItems.add(report);
                            } else if (spin.getSelectedItem().toString().equals(t3amolTypes[7]) &&
                                    !Arrays.asList(t3amolTypes).contains(report.feed_back_type.trim())) {
                                reportItems.add(report);
                            } else {
                                Log.e(TAG, "onDataChange: write reports impossible situation");
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reportsRef != null && reportsListener != null) {
            reportsRef.removeEventListener(reportsListener);
        }
    }
}