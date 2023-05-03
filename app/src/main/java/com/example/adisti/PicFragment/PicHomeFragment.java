package com.example.adisti.PicFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    PicProposalAdapter picProposalAdapter;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    PicInterface  picInterface;
    RecyclerView rvProposal;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_home, container, false);
        init(view);




        return view;
    }

    private void getAllProposal() {
        Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();

        picInterface.getAllProposal(sharedPreferences.getString("kode_loket", null))
                .enqueue(new Callback<List<ProposalModel>>() {
                    @Override
                    public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {
                        proposalModelList = response.body();
                        if (response.isSuccessful() && response.body().size() > 0) {

                        }

                    }

                    @Override
                    public void onFailure(Call<List<ProposalModel>> call, Throwable t) {

                    }
                });

    }

    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        rvProposal = view.findViewById(R.id.rvProposal);
        picInterface = DataApi.getClient().create(PicInterface.class);
    }
}