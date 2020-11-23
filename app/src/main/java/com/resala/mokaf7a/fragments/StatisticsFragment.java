package com.resala.mokaf7a.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.resala.mokaf7a.R;
import com.resala.mokaf7a.classes.User;

import static android.content.ContentValues.TAG;
import static com.resala.mokaf7a.LoginActivity.isAdmin;
import static com.resala.mokaf7a.LoginActivity.userBranch;
import static com.resala.mokaf7a.LoginActivity.userId;

public class StatisticsFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference usersRef;
    View view;
    TextView branchTV;

    private ProgressDialog progress;

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

        if (!isAdmin) {
            progress = new ProgressDialog(getContext());
            progress.setTitle("Loading");
            progress.setMessage("لحظات معانا...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            usersRef = database.getReference("users").child(userId);
            usersRef.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                userBranch = user.branch;

                                branchTV.setText(userBranch);
//                                    refreshReports();
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
//            refreshReports();
        }
        return view;
    }
}