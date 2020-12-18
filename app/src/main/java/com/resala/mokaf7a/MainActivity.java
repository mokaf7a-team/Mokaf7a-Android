package com.resala.mokaf7a;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.resala.mokaf7a.adapters.SectionsPagerAdapter;

import static com.resala.mokaf7a.LoginActivity.branches;
import static com.resala.mokaf7a.LoginActivity.isAdmin;
import static com.resala.mokaf7a.LoginActivity.isMrkzy;
import static com.resala.mokaf7a.LoginActivity.userBranch;
import static com.resala.mokaf7a.LoginActivity.userId;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button logOut_btn;
    public TabLayout.Tab tab0;
    public TabLayout.Tab tab1;
    public TabLayout.Tab tab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        logOut_btn = findViewById(R.id.logOutBtn);
        tabs.setupWithViewPager(viewPager);
        tab0 = tabs.getTabAt(0);
        tab1 = tabs.getTabAt(1);
        tab2 = tabs.getTabAt(2);

        assert tab0 != null;
        tab0.setCustomView(R.layout.tab_item);
        TextView textTab0 = tab0.getCustomView().findViewById(R.id.text1);
        textTab0.setText(getString(R.string.tab_text_1));
        ImageView imageViewTab0 = tab0.getCustomView().findViewById(R.id.icon);
        imageViewTab0.setImageResource(R.drawable.trend);

        assert tab1 != null;
        tab1.setCustomView(R.layout.tab_item);
        TextView textTab1 = tab1.getCustomView().findViewById(R.id.text1);
        textTab1.setText(getString(R.string.tab_text_2));
        ImageView imageViewTab1 = tab1.getCustomView().findViewById(R.id.icon);
        imageViewTab1.setImageResource(R.drawable.list);

        assert tab2 != null;
        tab2.setCustomView(R.layout.tab_item);
        TextView textTab2 = tab2.getCustomView().findViewById(R.id.text1);
        textTab2.setText(getString(R.string.tab_text_3));
        ImageView imageViewTab2 = tab2.getCustomView().findViewById(R.id.icon);
        imageViewTab2.setImageResource(R.drawable.bus);
    }

    @Override
    public void onBackPressed() {
        // do nothing
        Toast.makeText(this, "can't go back .. you can log out only", Toast.LENGTH_SHORT).show();
    }

    public void logOut(View view) {
        if (!isAdmin) {
            mAuth.signOut();
            userBranch = branches[9];
            userId = "-1";

        } else {
            isAdmin = false;
        }
        isMrkzy = false;
        finish();
    }
}
