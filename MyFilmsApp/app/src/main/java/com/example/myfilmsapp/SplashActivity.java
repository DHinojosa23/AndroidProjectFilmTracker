package com.example.myfilmsapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBarLoading = findViewById(R.id.progressBarLoading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, 2000);

    }



    public void openActivity_Main() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void startApp(){
        openActivity_Main();

    }
}
