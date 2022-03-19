package com.example.storePerfect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent I = new Intent(getApplicationContext(), Store_Activity.class);
                startActivity(I);
                finish();
            }
        }, 1500);

    }
}