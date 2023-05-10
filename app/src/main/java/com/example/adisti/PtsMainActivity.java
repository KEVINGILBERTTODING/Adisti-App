package com.example.adisti;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.adisti.PicFragment.PicHomeFragment;
import com.example.adisti.PtsFragment.PtsHomeFragment;

public class PtsMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pts_main);
        replace(new PtsHomeFragment());
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framePts, fragment)
                .addToBackStack(null).commit();
    }
}