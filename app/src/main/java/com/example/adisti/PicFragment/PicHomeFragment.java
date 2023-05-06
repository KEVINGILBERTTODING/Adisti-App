package com.example.adisti.PicFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.adisti.Model.ProposalModel;
import com.example.adisti.PicAdapter.PicProposalAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicHomeFragment extends Fragment {
    TextView tvUsername;
    SharedPreferences sharedPreferences;
    CardView cvMenuProposal;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_home, container, false);
        init(view);

        cvMenuProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new PicProposalFragment());
            }
        });




        return view;
    }



    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        cvMenuProposal = view.findViewById(R.id.cvMenuProposal);
    }

    private void replace(Fragment fragment) {
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framePic, fragment)
                .addToBackStack(null).commit();
    }
}