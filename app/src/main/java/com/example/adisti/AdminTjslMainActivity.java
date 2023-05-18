package com.example.adisti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.adisti.AdminTjslFragment.AdminTjslHomeFragment;
import com.example.adisti.AdminTjslFragment.AdminTjslInsertLpjKegiatanFragment;
import com.example.adisti.AdminTjslFragment.AdminTjslLpjKegiatanFragment;
import com.example.adisti.AdminTjslFragment.AdminTjslRealisasiBantuanFragment;
import com.example.adisti.PicFragment.PicProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminTjslMainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tjs_main);
        init();

        bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new AdminTjslHomeFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new PicProfileFragment());
                    return true;
                } else if (item.getItemId() == R.id.menuRealisasiBantuan) {
                    replace(new AdminTjslRealisasiBantuanFragment());
                    return true;
                } else if (item.getItemId() == R.id.menuLpj) {
                    replace(new AdminTjslLpjKegiatanFragment());
                    return true;

                }
                return false;
            }
        });



    }

    private void init() {
        bottomBar = findViewById(R.id.bottomBar);
        replace(new AdminTjslHomeFragment());
    }


    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, fragment)
                .commit();
    }
}