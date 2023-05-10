package com.example.adisti.PtsFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.PicFragment.PicKajianManfaatFragment;
import com.example.adisti.PicFragment.PicProposalFragment;
import com.example.adisti.R;

public class PtsHomeFragment extends Fragment {
    TextView tvUsername;
    SharedPreferences sharedPreferences;
    CardView cvMenuSurvey, cvMenuHasilSurvey;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_home, container, false);
        init(view);

        cvMenuSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new PicProposalFragment());
            }
        });

        cvMenuHasilSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new PicKajianManfaatFragment());
            }
        });






        return view;
    }



    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        cvMenuSurvey = view.findViewById(R.id.cvMenuSurvey);
        cvMenuHasilSurvey = view.findViewById(R.id.cvMenuHasilSurvey);
    }

    private void replace(Fragment fragment) {
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framePts, fragment)
                .addToBackStack(null).commit();
    }
}