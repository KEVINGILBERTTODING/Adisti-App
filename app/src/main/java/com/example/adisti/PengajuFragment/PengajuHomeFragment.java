package com.example.adisti.PengajuFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PicAdapter.PicProposalAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.example.adisti.Util.PicInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuHomeFragment extends Fragment {
    TextView tvUsername, tvEmpty, tvDateStart, tvDateEnd;
    String userId;
    SharedPreferences sharedPreferences;
    PengajuProposalAdapter pengajuProposalAdapter;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    PengajuInterface pengajuInterface;
    RecyclerView rvProposal;
    Button btnRefresh;
    FloatingActionButton fabInsert;
    ImageView ivProfile;
    PengajuModel pengajuModel;
    ImageButton btnRefreshmain, btnFilter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengaju_home, container, false);
        init(view);

        getAllProposal();
        getUserData();

        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framePengaju, new PengajuAddProposalFragment())
                        .addToBackStack(null).commit();
            }
        });
        btnRefreshmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvProposal.setAdapter(null);
                getAllProposal();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogFilter = new Dialog(getContext());
                dialogFilter.setContentView(R.layout.dialog_filter);
                dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final Button btnFilter = dialogFilter.findViewById(R.id.btnFilter);
                tvDateStart = dialogFilter.findViewById(R.id.tvDateStart);
                tvDateEnd = dialogFilter.findViewById(R.id.tvDateEnd);

                tvDateStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(tvDateStart);
                    }
                });

                tvDateEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(tvDateEnd);
                    }
                });

                btnFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvDateStart.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field tanggal dari tidak boleh kosng", Toasty.LENGTH_SHORT).show();
                        } else if (tvDateEnd.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field tanggal akhir tidak boleh kosng", Toasty.LENGTH_SHORT).show();
                        }else {
                            Dialog dialogProgress = new Dialog(getContext());
                            dialogProgress.setContentView(R.layout.dialog_progress_bar);
                            dialogProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialogProgress.setCanceledOnTouchOutside(false);
                            dialogProgress.show();

                            Dialog dialog = new Dialog(getContext());
                            dialog.setContentView(R.layout.dialog_progress_bar);
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.setCanceledOnTouchOutside(false);
                            final TextView tvMain;
                            tvMain = dialog.findViewById(R.id.tvMainText);
                            tvMain.setText("Memuat Data...");
                            dialog.show();



                            pengajuInterface.getAllFilterProposal(userId, tvDateStart.getText().toString(), tvDateEnd.getText().toString())
                                    .enqueue(new Callback<List<ProposalModel>>() {
                                        @Override
                                        public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {
                                            proposalModelList = response.body();
                                            if (response.isSuccessful() && response.body().size() > 0) {
                                                rvProposal.setAdapter(null);
                                                pengajuProposalAdapter = new PengajuProposalAdapter(getContext(), proposalModelList);
                                                linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                                rvProposal.setLayoutManager(linearLayoutManager);
                                                rvProposal.setAdapter(pengajuProposalAdapter);
                                                rvProposal.setHasFixedSize(false);
                                                tvEmpty.setVisibility(View.GONE);
                                                dialog.dismiss();
                                                dialogProgress.dismiss();
                                                dialogFilter.dismiss();

                                            }else {
                                                tvEmpty.setVisibility(View.VISIBLE);
                                                dialog.dismiss();
                                                dialogProgress.dismiss();
                                                dialogFilter.dismiss();


                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                                            tvEmpty.setVisibility(View.GONE);
                                            dialogProgress.dismiss();
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
                    }
                });
                dialogFilter.show();


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


    private void getUserData() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();
        pengajuInterface.getPengajuById(userId).enqueue(new Callback<PengajuModel>() {
            @Override
            public void onResponse(Call<PengajuModel> call, Response<PengajuModel> response) {
                pengajuModel = response.body();
                if (response.isSuccessful()) {
                    Glide.with(getContext())
                            .load(pengajuModel.getPhotoProfile()) .
                            diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).
                            fitCenter().centerCrop().into(ivProfile);

                    dialog.dismiss();

                }else {

                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PengajuModel> call, Throwable t) {
                dialog.dismiss();

                dialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                btnRefresh= dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getUserData();
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
        btnRefreshmain = view.findViewById(R.id.btnRefreshMain);
        btnFilter = view.findViewById(R.id.btnFilter);
        ivProfile = view.findViewById(R.id.ivProfile);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        userId = sharedPreferences.getString("user_id", null);
    }

    private void datePicker(TextView tvDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateFormatted, monthFormatted;
                if (dayOfMonth < 10) {
                    dateFormatted = String.format("%02d", dayOfMonth);
                }else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }

                if (month < 10) {
                    monthFormatted = String.format("%02d", month + 1);
                }else {
                    monthFormatted = String.valueOf(month + 1);
                }

                tvDate.setText(year + "-"+monthFormatted + "-"+ dateFormatted);
            }
        });
        datePickerDialog.show();
    }
}