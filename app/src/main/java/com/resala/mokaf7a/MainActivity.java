package com.resala.mokaf7a;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button2);
        button.setOnClickListener(v -> openLoginActivity());
        Button button1=findViewById(R.id.button3);
        button1.setOnClickListener(v -> openSginUpActivity());
        Button button2=findViewById(R.id.button4);
        button2.setOnClickListener(v -> openform());
    }
    public void openLoginActivity(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void openSginUpActivity(){
        Intent intent =new Intent(this,SginUpActivity.class);
        startActivity(intent);
    }
    public void openform(){
        Intent intent =new Intent(this,form.class);
        startActivity(intent);
    }

}
