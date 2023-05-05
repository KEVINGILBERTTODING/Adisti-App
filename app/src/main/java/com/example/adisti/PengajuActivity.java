package com.example.adisti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.adisti.PengajuFragment.PengajuHomeFragment;
import com.example.adisti.PengajuFragment.PengajuProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PengajuActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaju);
        bottomBar = findViewById(R.id.bottomBarPengaju);

        bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new PengajuHomeFragment());

                    return true;

                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new PengajuProfileFragment());
                    return true;
                }
                return false;
            }
        });

        replace(new PengajuHomeFragment());
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framePengaju, fragment).commit();
    }
}