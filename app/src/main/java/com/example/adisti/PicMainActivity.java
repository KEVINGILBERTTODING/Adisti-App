package com.example.adisti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.adisti.PicFragment.PicHomeFragment;

public class PicMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_main);
        replace(new PicHomeFragment());
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framePic, fragment)
                .addToBackStack(null).commit();
    }
}