package com.example.adisti.OnBoardingFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adisti.LoginActivity;
import com.example.adisti.R;


public class OnBoarding4Fragment extends Fragment {

    Button btnNext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_boarding4_fragmnet, container, false);
        sharedPreferences = getContext().getSharedPreferences("on_boarding", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("onboarding", true);
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });


        return view;
    }
}