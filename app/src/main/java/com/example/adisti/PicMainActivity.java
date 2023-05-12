package com.example.adisti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.adisti.PicFragment.PicHomeFragment;
import com.example.adisti.PicFragment.PicKajianManfaatFragment;
import com.example.adisti.PicFragment.PicProfileFragment;
import com.example.adisti.PicFragment.PicProposalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PicMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_main);
        replace(new PicHomeFragment());

        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new PicHomeFragment());
                    return true;
                }else  if (item.getItemId() == R.id.menuProposal) {
                    replace(new PicProposalFragment());
                    return true;
                }else  if (item.getItemId() == R.id.menuKajianManfaat) {
                    replace(new PicKajianManfaatFragment());
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
        getSupportFragmentManager().beginTransaction().replace(R.id.framePic, fragment)
                .commit();
    }
}