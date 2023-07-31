package com.example.adisti.PicFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.Model.NotificationModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.PihakPenerimaBantuanModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PengajuFragment.PengajuAddProposalFragment;
import com.example.adisti.PengajuFragment.PengajuNotificationFragment;
import com.example.adisti.PengajuFragment.PengajuProfileFragment;
import com.example.adisti.PicAdapter.PicProposalAdapter;
import com.example.adisti.PicAdapter.SpinnerPemohonBantuanAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.example.adisti.Util.PicInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicProposalFragment extends Fragment {
    private TextView tvUsername, tvEmpty;
    private String userId, kodeLoket, namaPihak;
    private SearchView searchView;
    private Integer verified = 2;
    private LottieAnimationView emptyAnimation;
    SharedPreferences sharedPreferences;
    PicProposalAdapter picProposalAdapter;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    private List<PihakPenerimaBantuanModel> pihakPenerimaBantuanModelList;
    private SpinnerPemohonBantuanAdapter spinnerPemohonBantuanAdapter;
    ImageButton btnFilter;
    RecyclerView rvProposal;
    PicInterface picInterface;

    TabLayout tabLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pic_proposal, container, false);
        init(view);
        getAllProposal(2);


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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ((tab.getPosition() == 0)){
                    getAllProposal(2);
                    verified = 2;
                } else if (tab.getPosition() == 1) {
                    getAllProposal(1);
                    verified = 1;
                }else if (tab.getPosition() == 2) {
                    getAllProposal(0);
                    verified = 0;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getPenerimaBantuan();

        listener();




        return view;
    }


    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        rvProposal = view.findViewById(R.id.rvProposal);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        emptyAnimation = view.findViewById(R.id.emptyAnimation);
        picInterface = DataApi.getClient().create(PicInterface.class);
        tabLayout = view.findViewById(R.id.tab_layout);
        searchView = view.findViewById(R.id.searchView);
        userId = sharedPreferences.getString("user_id", null);
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        btnFilter = view.findViewById(R.id.btnFilter);


        // set tablayout
        tabLayout.addTab(tabLayout.newTab().setText("Pengajuan"));
        tabLayout.addTab(tabLayout.newTab().setText("Valid"));
        tabLayout.addTab(tabLayout.newTab().setText("Tidak Valid"));
    }

    private void listener() {
        btnFilter.setOnClickListener(View -> {
            Dialog dialogFilter = new Dialog(getContext());
            dialogFilter.setContentView(R.layout.dialog_filter_category);
            dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final Button btnFilter = dialogFilter.findViewById(R.id.btnFilter);
            final Spinner spPenerimaBantuan = dialogFilter.findViewById(R.id.spkategoriPemohonBantuan);
            dialogFilter.show();

            spPenerimaBantuan.setAdapter(spinnerPemohonBantuanAdapter);
            spPenerimaBantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                    namaPihak = spinnerPemohonBantuanAdapter.getNamaPihak(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    if (namaPihak == null) {
                        Toasty.normal(getContext(), "Mohon memilih kategory terlebih dahulu", Toasty.LENGTH_SHORT).show();
                    }else {
                        dialogFilter.dismiss();
                        rvProposal.setAdapter(null);
                        Dialog dialogProgressBar = new Dialog(getContext());
                        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
                        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogProgressBar.setCancelable(false);
                        dialogProgressBar.setCanceledOnTouchOutside(false);
                        dialogProgressBar.show();

                        picInterface.getFilterProposal(kodeLoket, namaPihak, verified).enqueue(new Callback<List<ProposalModel>>() {
                            @Override
                            public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {

                                if (response.isSuccessful() && response.body().size() > 0) {
                                    proposalModelList = response.body();
                                    picProposalAdapter = new PicProposalAdapter(getContext(), proposalModelList);
                                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                    rvProposal.setAdapter(null);
                                    rvProposal.setLayoutManager(linearLayoutManager);
                                    rvProposal.setAdapter(picProposalAdapter);
                                    rvProposal.setHasFixedSize(true);
                                    dialogProgressBar.dismiss();
                                    tvEmpty.setVisibility(View.GONE);
                                    emptyAnimation.setVisibility(View.GONE);
                                }else {
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    rvProposal.setAdapter(null);
                                    emptyAnimation.setVisibility(View.VISIBLE);

                                    dialogProgressBar.dismiss();

                                }
                            }

                            @Override
                            public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                               dialogProgressBar.dismiss();
                               Toasty.error(getContext(), "Tidak ada koneksi internet",Toasty.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            });
        });
    }

    private void getAllProposal(Integer tipe){
        Dialog dialogProgressBar = new Dialog(getContext());
        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgressBar.setCancelable(false);
        dialogProgressBar.setCanceledOnTouchOutside(false);
        dialogProgressBar.show();

        picInterface.getAllTipeVerified(tipe, kodeLoket).enqueue(new Callback<List<ProposalModel>>() {
            @Override
            public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {

                if (response.isSuccessful() && response.body().size() > 0) {
                    proposalModelList = response.body();
                    picProposalAdapter = new PicProposalAdapter(getContext(), proposalModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvProposal.setAdapter(null);
                    rvProposal.setLayoutManager(linearLayoutManager);
                    rvProposal.setAdapter(picProposalAdapter);
                    rvProposal.setHasFixedSize(true);
                    dialogProgressBar.dismiss();
                    tvEmpty.setVisibility(View.GONE);
                    emptyAnimation.setVisibility(View.GONE);
                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    rvProposal.setAdapter(null);
                    emptyAnimation.setVisibility(View.VISIBLE);

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
                         getAllProposal(tipe);
                         dialogProgressBar.dismiss();
                    }
                });
                tvEmpty.setVisibility(View.GONE);
                emptyAnimation.setVisibility(View.GONE);
                dialogProgressBar.dismiss();

            }
        });

    }

    private void filter(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getBantuanDiajukan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        picProposalAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            picProposalAdapter.filter(filteredList);
        }

    }

    private void getPenerimaBantuan() {
        Dialog dialogProgressBar = new Dialog(getContext());
        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgressBar.setCancelable(false);
        dialogProgressBar.setCanceledOnTouchOutside(false);
        dialogProgressBar.show();
        picInterface.getPenerimaBantuan().enqueue(new Callback<List<PihakPenerimaBantuanModel>>() {
            @Override
            public void onResponse(Call<List<PihakPenerimaBantuanModel>> call, Response<List<PihakPenerimaBantuanModel>> response) {
                dialogProgressBar.dismiss();
                if (response.isSuccessful() && response.body().size() > 0) {
                    pihakPenerimaBantuanModelList = response.body();
                    spinnerPemohonBantuanAdapter = new SpinnerPemohonBantuanAdapter(getContext(), pihakPenerimaBantuanModelList);


                }else {
                    Toasty.error(getContext(), "Gagal memuat data penerima bantuan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PihakPenerimaBantuanModel>> call, Throwable t) {
                dialogProgressBar.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }


}