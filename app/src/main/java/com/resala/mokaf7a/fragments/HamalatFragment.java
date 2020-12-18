package com.resala.mokaf7a.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.resala.mokaf7a.AddHamlaActivity;
import com.resala.mokaf7a.R;

public class HamalatFragment extends Fragment {
    View view;
    ImageButton viewHamalatBtn;
    ImageButton addHamlaBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hamalat, container, false);
        viewHamalatBtn = view.findViewById(R.id.viewHamalatBtn);
        addHamlaBtn = view.findViewById(R.id.addHamlaBtn);
        viewHamalatBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddHamlaActivity.class));
            //TODO :: change destination activity to view all hamalat
        });

        addHamlaBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddHamlaActivity.class));
        });
        return view;
    }
}
