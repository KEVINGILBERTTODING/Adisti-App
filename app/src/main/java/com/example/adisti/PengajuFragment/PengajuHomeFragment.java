package com.example.adisti.PengajuFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adisti.Model.ProposalModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PicAdapter.PicProposalAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.example.adisti.Util.PicInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuHomeFragment extends Fragment {
    TextView tvUsername, tvEmpty;
    String userId;
    SharedPreferences sharedPreferences;
    PengajuProposalAdapter pengajuProposalAdapter;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    PengajuInterface pengajuInterface;
    RecyclerView rvProposal;
    Button btnRefresh;
    FloatingActionButton fabInsert;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengaju_home, container, false);
        init(view);

        getAllProposal();

        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framePengaju, new PengajuAddProposalFragment())
                        .addToBackStack(null).commit();
            }
        });




        return view;
    }

    private void getAllProposal() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();



        pengajuInterface.getAllProposal(userId)
                .enqueue(new Callback<List<ProposalModel>>() {
                    @Override
                    public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {
                        proposalModelList = response.body();
                        if (response.isSuccessful() && response.body().size() > 0) {
                            pengajuProposalAdapter = new PengajuProposalAdapter(getContext(), proposalModelList);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rvProposal.setLayoutManager(linearLayoutManager);
                            rvProposal.setAdapter(pengajuProposalAdapter);
                            rvProposal.setHasFixedSize(false);
                            tvEmpty.setVisibility(View.GONE);
                            dialog.dismiss();

                        }else {
                            tvEmpty.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                        tvEmpty.setVisibility(View.GONE);
                        dialog.dismiss();
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getAllProposal();
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.show();



                    }
                });

    }

    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        rvProposal = view.findViewById(R.id.rvProposal);
        fabInsert = view.findViewById(R.id.fabInsert);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        userId = sharedPreferences.getString("user_id", null);
    }
}