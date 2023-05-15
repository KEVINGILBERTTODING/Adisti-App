package com.example.adisti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.adisti.DcmFragment.DcmPendapatTanggapanFragment;
import com.example.adisti.PicFragment.PicProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DcmMainActivty extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcm_main_activty);

        replace(new DcmPendapatTanggapanFragment());
        bottomNavigationView = findViewById(R.id.bottomBar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new DcmPendapatTanggapanFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new PicProfileFragment());
                    return true;
                }
                return false;
            }
        });




    }

    private void replace (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameDcm, fragment).commit();
    }
}