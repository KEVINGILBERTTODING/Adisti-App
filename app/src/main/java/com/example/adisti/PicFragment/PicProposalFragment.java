package com.example.adisti.PicFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.Model.NotificationModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PengajuFragment.PengajuAddProposalFragment;
import com.example.adisti.PengajuFragment.PengajuNotificationFragment;
import com.example.adisti.PengajuFragment.PengajuProfileFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicProposalFragment extends Fragment {
    TextView tvUsername, tvEmpty, tvDateStart, tvDateEnd, tv_total_notif;
    String userId;
    SearchView searchView;
    RelativeLayout rl_count_notif;
    SharedPreferences sharedPreferences;
    PengajuProposalAdapter pengajuProposalAdapter;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    PengajuInterface pengajuInterface;
    RecyclerView rvProposal;
    FloatingActionButton fabInsert;
    ImageView ivProfile;
    PengajuModel pengajuModel;
    ImageButton btnRefreshmain, btnFilter;
    TabLayout tabLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_proposal, container, false);
        init(view);

//        getAllProposal();
//        getUserData();

        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framePengaju, new PengajuAddProposalFragment())
                        .addToBackStack(null).commit();
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null).replace(R.id.framePengaju, new PengajuProfileFragment())
                        .commit();
            }
        });

        return view;
    }


    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        rvProposal = view.findViewById(R.id.rvProposal);
        fabInsert = view.findViewById(R.id.fabInsert);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rl_count_notif = view.findViewById(R.id.rl_count_notif);
        btnRefreshmain = view.findViewById(R.id.btnRefreshMain);
        ivProfile = view.findViewById(R.id.img_profile);
        tv_total_notif = view.findViewById(R.id.tv_total_notif);
        tabLayout = view.findViewById(R.id.tab_layout);
        searchView = view.findViewById(R.id.searchView);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        userId = sharedPreferences.getString("user_id", null);

        tabLayout.addTab(tabLayout.newTab().setText("Verifikasi"));
        tabLayout.addTab(tabLayout.newTab().setText("Diterima"));
        tabLayout.addTab(tabLayout.newTab().setText("Ditolak"));
    }

//    private void getAllProposal() {
//        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.dialog_progress_bar);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.setCanceledOnTouchOutside(false);
//        final TextView tvMain;
//        tvMain = dialog.findViewById(R.id.tvMainText);
//        tvMain.setText("Memuat Data...");
//        dialog.show();
//
//
//
//        pengajuInterface.getAllProposal(userId)
//                .enqueue(new Callback<List<ProposalModel>>() {
//                    @Override
//                    public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {
//                        proposalModelList = response.body();
//                        if (response.isSuccessful() && response.body().size() > 0) {
//                            pengajuProposalAdapter = new PengajuProposalAdapter(getContext(), proposalModelList);
//                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                            rvProposal.setLayoutManager(linearLayoutManager);
//                            rvProposal.setAdapter(pengajuProposalAdapter);
//                            rvProposal.setHasFixedSize(false);
//                            tvEmpty.setVisibility(View.GONE);
//                            dialog.dismiss();
//
//                        }else {
//                            tvEmpty.setVisibility(View.VISIBLE);
//                            dialog.dismiss();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
//                        tvEmpty.setVisibility(View.GONE);
//                        dialog.dismiss();
//                        Dialog dialogNoConnection = new Dialog(getContext());
//                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
//                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                        dialogNoConnection.setCanceledOnTouchOutside(false);
//                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
//                        btnRefresh.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                getAllProposal();
//                                dialogNoConnection.dismiss();
//                            }
//                        });
//                        dialogNoConnection.show();
//
//
//
//                    }
//                });
//
//    }




//    private void getUserData() {
//        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.dialog_progress_bar);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.setCanceledOnTouchOutside(false);
//        final TextView tvMain;
//        tvMain = dialog.findViewById(R.id.tvMainText);
//        tvMain.setText("Memuat Data...");
//        dialog.show();
//        pengajuInterface.getPengajuById(userId).enqueue(new Callback<PengajuModel>() {
//            @Override
//            public void onResponse(Call<PengajuModel> call, Response<PengajuModel> response) {
//                pengajuModel = response.body();
//                if (response.isSuccessful()) {
//                    Glide.with(getContext())
//                            .load(pengajuModel.getPhotoProfile())
//                            .placeholder(R.drawable.photo_default)
//                            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).
//                            fitCenter().centerCrop().into(ivProfile);
//
//                    dialog.dismiss();
//
//                }else {
//
//                    dialog.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<PengajuModel> call, Throwable t) {
//                dialog.dismiss();
//
//                dialog.dismiss();
//                Dialog dialogNoConnection = new Dialog(getContext());
//                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
//                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                dialogNoConnection.setCanceledOnTouchOutside(false);
//                Button btnRefresh= dialogNoConnection.findViewById(R.id.btnRefresh);
//                btnRefresh.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getUserData();
//                        dialogNoConnection.dismiss();
//                    }
//                });
//                dialogNoConnection.show();
//
//
//            }
//        });
//
//    }



    private void filter(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getNoProposal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        pengajuProposalAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            pengajuProposalAdapter.filter(filteredList);
        }

    }
}