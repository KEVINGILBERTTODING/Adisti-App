package com.example.adisti.DcmFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.UserModel;
import com.example.adisti.PengajuFragment.PengajuProfileFragment;
import com.example.adisti.PicFragment.PicProfileFragment;
import com.example.adisti.PtsAdapter.PtsHasilSurveyAdapter;
import com.example.adisti.PtsAdapter.PtsSurvey2Adapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;
import com.example.adisti.Util.PtsInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DcmPendapatTanggapanFragment extends Fragment {
    TextView tvUsername, tvEmpty;
    String userId, kodeLoket;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    PtsSurvey2Adapter ptsSurvey2Adapter;
    PtsHasilSurveyAdapter ptsHasilSurveyAdapter;
    PicInterface picInterface;
    ImageView img_profile;

    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rvProposal;
    PtsInterface ptsInterface;

    ImageView ivProfile;
    TabLayout tabLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dcm_pendapat_tanggapan, container, false);
        init(view);
//        getProposalSurvey(0);



        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null).replace(R.id.frameDcm, new PicProfileFragment())
                        .commit();
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ((tab.getPosition() == 0)){
//                    getProposalSurvey(0);
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
                } else if (tab.getPosition() == 1) {
//                    getProposalHasilSurvey(1);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            filter2(newText);
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }


    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        rvProposal = view.findViewById(R.id.rvProposal);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        ptsInterface = DataApi.getClient().create(PtsInterface.class);
        ivProfile = view.findViewById(R.id.img_profile);
        tabLayout = view.findViewById(R.id.tab_layout);
        searchView = view.findViewById(R.id.searchView);
        img_profile = view.findViewById(R.id.img_profile);
        userId = sharedPreferences.getString("user_id", null);

        // set tablayout
        tabLayout.addTab(tabLayout.newTab().setText("Survey"));
        tabLayout.addTab(tabLayout.newTab().setText("Hasil Survey"));
        picInterface = DataApi.getClient().create(PicInterface.class);

        loadProfile();
    }

    @Override
    public void onResume() {
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
        super.onResume();
    }

    // Get proposal yang belum di input hasil survey
    private void getProposalSurvey(Integer status){
        Dialog dialogProgressBar = new Dialog(getContext());
        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgressBar.setCancelable(false);
        dialogProgressBar.setCanceledOnTouchOutside(false);
        dialogProgressBar.show();

        ptsInterface.getProposalHasilSurvey(kodeLoket, status).enqueue(new Callback<List<ProposalModel>>() {
            @Override
            public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {

                if (response.isSuccessful() && response.body().size() > 0) {
                    proposalModelList = response.body();
                    ptsSurvey2Adapter = new PtsSurvey2Adapter(getContext(), proposalModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvProposal.setAdapter(null);
                    rvProposal.setLayoutManager(linearLayoutManager);
                    rvProposal.setAdapter(ptsSurvey2Adapter);
                    rvProposal.setHasFixedSize(true);
                    dialogProgressBar.dismiss();
                    tvEmpty.setVisibility(View.GONE);
                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvProposal.setAdapter(null);
                    dialogProgressBar.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.setCancelable(false);
                dialogProgressBar.setCanceledOnTouchOutside(false);
                dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button  btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         getProposalSurvey(status);
                         dialogProgressBar.dismiss();
                    }
                });
                tvEmpty.setVisibility(View.GONE);
                dialogProgressBar.dismiss();

            }
        });

    }


    // get proposal yang telah diinpput hasil survey
    private void getProposalHasilSurvey(Integer status){
        Dialog dialogProgressBar = new Dialog(getContext());
        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgressBar.setCancelable(false);
        dialogProgressBar.setCanceledOnTouchOutside(false);
        dialogProgressBar.show();

        ptsInterface.getProposalHasilSurvey(kodeLoket, status).enqueue(new Callback<List<ProposalModel>>() {
            @Override
            public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {

                if (response.isSuccessful() && response.body().size() > 0) {
                    proposalModelList = response.body();
                    ptsHasilSurveyAdapter = new PtsHasilSurveyAdapter(getContext(), proposalModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvProposal.setAdapter(null);
                    rvProposal.setLayoutManager(linearLayoutManager);
                    rvProposal.setAdapter(ptsHasilSurveyAdapter);
                    rvProposal.setHasFixedSize(true);
                    dialogProgressBar.dismiss();
                    tvEmpty.setVisibility(View.GONE);
                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvProposal.setAdapter(null);
                    dialogProgressBar.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.setCancelable(false);
                dialogProgressBar.setCanceledOnTouchOutside(false);
                dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button  btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getProposalHasilSurvey(status);
                        dialogProgressBar.dismiss();
                    }
                });
                tvEmpty.setVisibility(View.GONE);
                dialogProgressBar.dismiss();

            }
        });

    }




    //filter proposal belum input hasil survey
    private void filter(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getNoProposal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        ptsSurvey2Adapter.filter(filteredList);
        if (filteredList.isEmpty()) {

        }else {
            ptsSurvey2Adapter.filter(filteredList);
        }

    }

    //filter proposal telah input hasil survey
    private void filter2(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getNoProposal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        ptsHasilSurveyAdapter.filter(filteredList);

        if (filteredList.isEmpty()) {

        }else {
            ptsHasilSurveyAdapter.filter(filteredList);
        }

    }

    private void loadProfile(){
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        picInterface.getUserById(userId).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Glide.with(getContext()).load(response.body().getPhotoProfile())
                            .skipMemoryCache(false)
                            .dontAnimate()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(img_profile);



                    dialog.dismiss();


                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Dialog dialog1 = new Dialog(getContext());
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setContentView(R.layout.dialog_no_connection);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh;
                btnRefresh = dialog1.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadProfile();
                        dialog1.dismiss();
                    }
                });
                dialog1.show();

            }
        });



    }




}