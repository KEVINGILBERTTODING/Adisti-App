package com.example.adisti;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.adisti.PicFragment.PicHomeFragment;
import com.example.adisti.PicFragment.PicProfileFragment;
import com.example.adisti.PtsFragment.PtsHasilSurveyFragment;
import com.example.adisti.PtsFragment.PtsHomeFragment;
import com.example.adisti.PtsFragment.PtsSurveyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PtsMainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pts_main);
        replace(new PtsHomeFragment());

        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new PtsHomeFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuSurvey) {
                    replace(new PtsSurveyFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuHasilSurvey) {
                    replace(new PtsHasilSurveyFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new PicProfileFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framePts, fragment)
                .commit();
    }
}