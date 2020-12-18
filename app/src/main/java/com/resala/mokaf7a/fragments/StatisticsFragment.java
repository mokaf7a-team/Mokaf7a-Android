package com.resala.mokaf7a.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.resala.mokaf7a.adapters.Top5Adapter;
import com.resala.mokaf7a.classes.Report;
import com.resala.mokaf7a.classes.Top5Item;
import com.resala.mokaf7a.classes.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.resala.mokaf7a.LoginActivity.branches;
import static com.resala.mokaf7a.LoginActivity.isAdmin;
import static com.resala.mokaf7a.LoginActivity.isMrkzy;
import static com.resala.mokaf7a.LoginActivity.userBranch;
import static com.resala.mokaf7a.LoginActivity.userId;
import static com.resala.mokaf7a.fragments.ShowDataFragment.t3amolTypes;

public class StatisticsFragment extends Fragment {
    private static final int BRANCHES_COUNT = 9;

    FirebaseDatabase database;
    DatabaseReference usersRef;
    View view;
    TextView branchTV;

    private ProgressDialog progress;

    DatabaseReference reportsRef;
    ValueEventListener reportsListener;
    private long totalReports;
    TextView totalReportsTV;
    TextView totalFeedbacksTV;

    TextView[] unfinishedTwasolTV = new TextView[9];
    TextView[] unfinishedT3amolTV = new TextView[9];

    int[] unfinishedTwasolCounter = new int[9];
    int[] unfinishedT3amolCounter = new int[9];
    int branchIterator;

    ArrayList<Top5Item> top5Items = new ArrayList<>();
    Top5Adapter adapter;
    HashMap<String, Integer> t3amolTypesCounter = new HashMap<>();
    int totalFinished;

    /**
     * Called when the fragment is visible to the user and actively running.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (userBranch != null) branchTV.setText(userBranch);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        database = FirebaseDatabase.getInstance();
        branchTV = view.findViewById(R.id.branch);
        totalReportsTV = view.findViewById(R.id.totalReportsTV);
        totalFeedbacksTV = view.findViewById(R.id.totalFeedbacksTV);

        RecyclerView recyclerView = view.findViewById(R.id.feebacksRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Top5Adapter(top5Items, getContext());
        recyclerView.setAdapter(adapter);

        getBranchesTables();
        initializeLists();
        if (!isAdmin) {
            progress = new ProgressDialog(getContext());
            progress.setTitle("Loading");
            progress.setMessage("لحظات معانا...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            if (userId == null) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            usersRef = database.getReference("users").child(userId);
            usersRef.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                userBranch = user.branch;
                                isMrkzy = userBranch.equals(branches[9]);
                                branchTV.setText(userBranch);
                                refreshReports();
                            }
                            progress.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
        } else {
            branchTV.setText(userBranch);
            refreshReports();
        }
        return view;
    }

    private void initializeLists() {
        for (branchIterator = 0; branchIterator < BRANCHES_COUNT; branchIterator++) {
            unfinishedT3amolCounter[branchIterator] = 0;
            unfinishedTwasolCounter[branchIterator] = 0;
        }
    }

    private void getBranchesTables() {
        // تواصل
        unfinishedTwasolTV[0] = view.findViewById(R.id.mohTwasol);
        unfinishedTwasolTV[1] = view.findViewById(R.id.maadiTwasol);
        unfinishedTwasolTV[2] = view.findViewById(R.id.faisalTwasol);
        unfinishedTwasolTV[3] = view.findViewById(R.id.mnasrTwasol);
        unfinishedTwasolTV[4] = view.findViewById(R.id.melgdidaTwasol);
        unfinishedTwasolTV[5] = view.findViewById(R.id.octTwasol);
        unfinishedTwasolTV[6] = view.findViewById(R.id.helwanTwasol);
        unfinishedTwasolTV[7] = view.findViewById(R.id.alexTwasol);
        unfinishedTwasolTV[8] = view.findViewById(R.id.mokattamTwasol);

        // تعامل
        unfinishedT3amolTV[0] = view.findViewById(R.id.mohT3amol);
        unfinishedT3amolTV[1] = view.findViewById(R.id.maadiT3amol);
        unfinishedT3amolTV[2] = view.findViewById(R.id.faisalT3amol);
        unfinishedT3amolTV[3] = view.findViewById(R.id.mnasrT3amol);
        unfinishedT3amolTV[4] = view.findViewById(R.id.melgdidaT3amol);
        unfinishedT3amolTV[5] = view.findViewById(R.id.octT3amol);
        unfinishedT3amolTV[6] = view.findViewById(R.id.helwanT3amol);
        unfinishedT3amolTV[7] = view.findViewById(R.id.alexT3amol);
        unfinishedT3amolTV[8] = view.findViewById(R.id.mokattamT3amol);
    }

    private void refreshReports() {
        reportsRef = database.getReference("reports");

        reportsListener = reportsRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        totalFinished = 0;
                        initializeLists();
                        totalReports = dataSnapshot.getChildrenCount();
                        totalReportsTV.setText(String.valueOf(totalReports));

                        // initialize map
                        t3amolTypesCounter.put(t3amolTypes[1], 0);
                        t3amolTypesCounter.put(t3amolTypes[2], 0);
                        t3amolTypesCounter.put(t3amolTypes[3], 0);
                        t3amolTypesCounter.put(t3amolTypes[4], 0);
                        t3amolTypesCounter.put(t3amolTypes[5], 0);
                        t3amolTypesCounter.put(t3amolTypes[7], 0);

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Report report = snapshot.getValue(Report.class);
                            assert report != null;
                            if (!(report.feed_back_type == null || report.feed_back_type.trim().isEmpty())) {
                                totalFinished++;
                                try {
                                    if (!t3amolTypesCounter.containsKey(report.feed_back_type.trim())) {//تعامل اخر
                                        t3amolTypesCounter.put(
                                                t3amolTypes[7],
                                                t3amolTypesCounter.get(t3amolTypes[7]) + 1);
                                    } else {
                                        t3amolTypesCounter.put(
                                                report.feed_back_type.trim(),
                                                t3amolTypesCounter.get(report.feed_back_type.trim()) + 1);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            for (branchIterator = 0; branchIterator < BRANCHES_COUNT; branchIterator++) {
                                if (report.branch.trim().equals(branches[branchIterator])) {
                                    if (report.feed_back_type == null || report.feed_back_type.trim().isEmpty())
                                        unfinishedT3amolCounter[branchIterator]++;
                                    if (report.first_feedback == null || report.first_feedback.trim().isEmpty())
                                        unfinishedTwasolCounter[branchIterator]++;
                                }
                            }
                        }
                        updateTopTypes();
                        updateUnfinishedReports();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }

    private void updateUnfinishedReports() {
        int totalTwasol = 0;
        int totalT3amol = 0;
        for (branchIterator = 0; branchIterator < BRANCHES_COUNT; branchIterator++) {
            totalTwasol += unfinishedTwasolCounter[branchIterator];
            totalT3amol += unfinishedT3amolCounter[branchIterator];

            unfinishedTwasolTV[branchIterator].setText(String.valueOf(unfinishedTwasolCounter[branchIterator]));
            unfinishedT3amolTV[branchIterator].setText(String.valueOf(unfinishedT3amolCounter[branchIterator]));
        }
        TextView totalT3amolTV = view.findViewById(R.id.totalT3amolTV);
        TextView totalTwasolTV = view.findViewById(R.id.totalTwasolTV);
        totalTwasolTV.setText(String.valueOf(totalTwasol));
        totalT3amolTV.setText(String.valueOf(totalT3amol));
    }

    private void updateTopTypes() {
        totalFeedbacksTV.setText(String.valueOf(totalFinished));
        ArrayList<Top5Item> top5ItemsArray = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : t3amolTypesCounter.entrySet()) {
            top5ItemsArray.add(new Top5Item(entry.getKey(), entry.getValue(), 0, false));
        }
        Collections.sort(top5ItemsArray);
        int top5Sum = 0;
        top5Items.clear();
        for (int i = 0; i < t3amolTypesCounter.size(); i++) {
            top5Items.add(top5ItemsArray.get(i));
            top5Sum += top5ItemsArray.get(i).total;
        }
        for (int i = 0; i < top5Items.size(); i++) {
            top5Items.get(i).setProgress(Math.round(((float) top5Items.get(i).total / top5Sum) * 100));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reportsRef != null && reportsListener != null) {
            reportsRef.removeEventListener(reportsListener);
        }
    }
}