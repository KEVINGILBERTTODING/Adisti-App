package com.example.adisti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("on_boarding", MODE_PRIVATE);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (sharedPreferences.getBoolean("onboarding", false)) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashScreenActivity.this, OnBoardingActivty.class));
                    finish();

                }

            }
        }, 1500L);
    }
}