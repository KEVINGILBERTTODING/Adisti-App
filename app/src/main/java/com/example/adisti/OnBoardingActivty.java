package com.example.adisti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.adisti.OnBoardingFragment.OnBoarding1Fragment;

public class OnBoardingActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_activty);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_on_board, new OnBoarding1Fragment()).commit();
    }
}