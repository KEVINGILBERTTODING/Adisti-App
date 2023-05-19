package com.example.adisti.PicFragment;

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

import com.airbnb.lottie.LottieAnimationView;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.PengajuFragment.PengajuProfileFragment;
import com.example.adisti.PicAdapter.PicKajianManfaatAdapter;
import com.example.adisti.PicAdapter.PicProposalAdapter;
import com.example.adisti.PicAdapter.PicProposalKajianManfaatAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicKajianManfaatFragment extends Fragment {
    TextView tvUsername, tvEmpty;
    String userId, kodeLoket;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    PicKajianManfaatAdapter picKajianManfaatAdapter;
    PicProposalKajianManfaatAdapter picProposalKajianManfaatAdapter;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rvProposal;
    PicInterface picInterface;

    TabLayout tabLayout;
    LottieAnimationView emptyAnim;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pic_kajian_manfaat, container, false);
        init(view);
        getAllProposal(1, 0);

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
                    getAllProposal(1, 0);
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
                    getProposalkajianManfaat(1, 1);
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
        picInterface = DataApi.getClient().create(PicInterface.class);
        tabLayout = view.findViewById(R.id.tab_layout);
        searchView = view.findViewById(R.id.searchView);
        emptyAnim = view.findViewById(R.id.emptyAnimation);
        userId = sharedPreferences.getString("user_id", null);
        kodeLoket = sharedPreferences.getString("kode_loket", null);


        // set tablayout
        tabLayout.addTab(tabLayout.newTab().setText("Proposal"));
        tabLayout.addTab(tabLayout.newTab().setText("Kajian Manfaat"));
    }


    // Belum di input kajian manfaat
    private void getAllProposal(Integer tipe, Integer codeKajianManfaat){
        Dialog dialogProgressBar = new Dialog(getContext());
        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgressBar.setCancelable(false);
        dialogProgressBar.setCanceledOnTouchOutside(false);
        dialogProgressBar.show();

        picInterface.getAllProposalKajianManfaat(kodeLoket, tipe, codeKajianManfaat).enqueue(new Callback<List<ProposalModel>>() {
            @Override
            public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {

                if (response.isSuccessful() && response.body().size() > 0) {
                    proposalModelList = response.body();
                    picProposalKajianManfaatAdapter = new PicProposalKajianManfaatAdapter(getContext(), proposalModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvProposal.setAdapter(null);
                    rvProposal.setLayoutManager(linearLayoutManager);
                    rvProposal.setAdapter(picProposalKajianManfaatAdapter);
                    rvProposal.setHasFixedSize(true);
                    dialogProgressBar.dismiss();
                    tvEmpty.setVisibility(View.GONE);
                    emptyAnim.setVisibility(View.GONE);
                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    emptyAnim.setVisibility(View.VISIBLE);
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
                         getAllProposal(tipe, codeKajianManfaat);
                         dialogProgressBar.dismiss();
                    }
                });
                tvEmpty.setVisibility(View.GONE);
                emptyAnim.setVisibility(View.GONE);

                dialogProgressBar.dismiss();

            }
        });

    }

    // telah diinput kajian manfaaat
    private void getProposalkajianManfaat(Integer tipe, Integer codeKajianManfaat){
        Dialog dialogProgressBar = new Dialog(getContext());
        dialogProgressBar.setContentView(R.layout.dialog_progress_bar);
        dialogProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogProgressBar.setCancelable(false);
        dialogProgressBar.setCanceledOnTouchOutside(false);
        dialogProgressBar.show();

        picInterface.getAllProposalKajianManfaat(kodeLoket, tipe, codeKajianManfaat).enqueue(new Callback<List<ProposalModel>>() {
            @Override
            public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {

                if (response.isSuccessful() && response.body().size() > 0) {
                    proposalModelList = response.body();
                    picKajianManfaatAdapter = new PicKajianManfaatAdapter(getContext(), proposalModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvProposal.setAdapter(null);
                    rvProposal.setLayoutManager(linearLayoutManager);
                    rvProposal.setAdapter(picKajianManfaatAdapter);
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
                        getAllProposal(tipe, codeKajianManfaat);
                        dialogProgressBar.dismiss();
                    }
                });
                tvEmpty.setVisibility(View.GONE);
                dialogProgressBar.dismiss();

            }
        });

    }


    //filter proposal belum input kajian manfaat
    private void filter(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getNoProposal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        picProposalKajianManfaatAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {

        }else {
            picProposalKajianManfaatAdapter.filter(filteredList);
        }

    }

    //filter proposal telah input kajian manfaat
    private void filter2(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getNoProposal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        picKajianManfaatAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {

        }else {
            picKajianManfaatAdapter.filter(filteredList);
        }

    }



}