package com.example.adisti.AdminTjslFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.AdminTjslAdapter.AdminTjslLaporanProposalAdapter;
import com.example.adisti.FileDownload;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.NotificationModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PengajuFragment.PengajuAddProposalFragment;
import com.example.adisti.PengajuFragment.PengajuNotificationFragment;
import com.example.adisti.PengajuFragment.PengajuProfileFragment;
import com.example.adisti.PicAdapter.SpinnerKodeLoketAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.AdminTjslInterface;
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

public class AdminTjslLaporanFragment extends Fragment {
    TextView tvUsername, tvEmpty, tvDateStart, tvDateEnd, tv_total_notif;
    String userId, status, kodeLoket;
    Button btnDownload;
    SearchView searchView;
    LottieAnimationView emptyAnimation;
    AdminTjslInterface adminTjslInterface;
    List<LoketModel> loketModelList;
    SpinnerKodeLoketAdapter spinnerKodeLoketAdapter;
    PengajuInterface pengajuInterface;
    SharedPreferences sharedPreferences;
    List<ProposalModel>proposalModelList;
    LinearLayoutManager linearLayoutManager;
    AdminTjslLaporanProposalAdapter adminTjslLaporanProposalAdapter;

    RecyclerView rvProposal;
    FloatingActionButton fabFilter;
    ImageButton btnRefreshmain;
    String [] opsiStatus = {"Semua", "Diterima", "Ditolak"};
    Spinner spStatus, spkodeLoket;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_laporan, container, false);
        init(view);

        getAllProposal();



        btnRefreshmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvProposal.setAdapter(null);
                getAllProposal();
            }
        });

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialogFilter = new Dialog(getContext());
                dialogFilter.setContentView(R.layout.dialog_filter_laporan);
                dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final Button btnFilter = dialogFilter.findViewById(R.id.btnFilter);
                tvDateStart = dialogFilter.findViewById(R.id.tvDateStart);
                tvDateEnd = dialogFilter.findViewById(R.id.tvDateEnd);
                spStatus = dialogFilter.findViewById(R.id.spStatus);
                spkodeLoket = dialogFilter.findViewById(R.id.spLoket);

                spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        status = opsiStatus[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spkodeLoket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        kodeLoket = spinnerKodeLoketAdapter.getLoketId(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                getKodeLoket();


                ArrayAdapter adapterStatus = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiStatus);
                adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spStatus.setAdapter(adapterStatus);


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

                            rvProposal.setAdapter(null);

                            Dialog dialog = new Dialog(getContext());
                            dialog.setContentView(R.layout.dialog_progress_bar);
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.setCanceledOnTouchOutside(false);
                            final TextView tvMain;
                            tvMain = dialog.findViewById(R.id.tvMainText);
                            tvMain.setText("Memuat Data...");
                            dialog.show();



                            adminTjslInterface.filterLaporanProposal(
                                            tvDateStart.getText().toString(), tvDateEnd.getText().toString(), kodeLoket, status)
                                    .enqueue(new Callback<List<ProposalModel>>() {
                                        @Override
                                        public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {
                                            proposalModelList = response.body();
                                            if (response.isSuccessful() && response.body().size() > 0) {
                                                adminTjslLaporanProposalAdapter = new AdminTjslLaporanProposalAdapter(getContext(), proposalModelList);
                                                linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                                                rvProposal.setLayoutManager(linearLayoutManager);
                                                rvProposal.setAdapter(adminTjslLaporanProposalAdapter);
                                                rvProposal.setHasFixedSize(false);
                                                dialogFilter.dismiss();
                                                tvEmpty.setVisibility(View.GONE);
                                                emptyAnimation.setVisibility(View.GONE);
                                                dialog.dismiss();
                                                btnDownload.setEnabled(true);

                                            }else {
                                                tvEmpty.setVisibility(View.VISIBLE);
                                                dialogFilter.dismiss();
                                                dialog.dismiss();
                                                btnDownload.setEnabled(false);
                                                emptyAnimation.setVisibility(View.VISIBLE);


                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                                            tvEmpty.setVisibility(View.GONE);
                                            emptyAnimation.setVisibility(View.VISIBLE);
                                            dialog.dismiss();
                                            btnDownload.setEnabled(false);
                                            Dialog dialogNoConnection = new Dialog(getContext());
                                            dialogNoConnection.setContentView(R.layout.dialog_no_connection_close);
                                            dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            dialogNoConnection.setCanceledOnTouchOutside(false);
                                            Button btnOke = dialogNoConnection.findViewById(R.id.btnOke);
                                            btnOke.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

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

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = DataApi.URL_CETAK_LAPORAN_PROPOSAL + tvDateStart.getText().toString() + "/" + tvDateEnd.getText().toString() + "/" + kodeLoket + "/" +status;
                String title = "Laporan_proposal_" + tvDateStart.getText().toString() + "_" + tvDateEnd.getText().toString();
                String description = "Downloading PDF file";
                String fileName = "Laporan_proposal_" + tvDateStart.getText().toString() + "_" + tvDateEnd.getText().toString();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 1000);
                    } else {

                        FileDownload fileDownload = new FileDownload(getContext());
                        fileDownload.downloadFile(url, title, description, fileName);

                    }
                } else {

                    FileDownload fileDownload = new FileDownload(getContext());
                    fileDownload.downloadFile(url, title, description, fileName);
                }
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





        return view;
    }


    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        rvProposal = view.findViewById(R.id.rvProposal);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnRefreshmain = view.findViewById(R.id.btnRefreshMain);
        fabFilter = view.findViewById(R.id.fabFilter);
        tv_total_notif = view.findViewById(R.id.tv_total_notif);
        searchView = view.findViewById(R.id.searchView);
        emptyAnimation = view.findViewById(R.id.EmptyAnimation);
        btnDownload = view.findViewById(R.id.btnDownload);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        userId = sharedPreferences.getString("user_id", null);
        adminTjslInterface = DataApi.getClient().create(AdminTjslInterface.class);




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



        adminTjslInterface.getAllProposal()
                .enqueue(new Callback<List<ProposalModel>>() {
                    @Override
                    public void onResponse(Call<List<ProposalModel>> call, Response<List<ProposalModel>> response) {
                        proposalModelList = response.body();
                        if (response.isSuccessful() && response.body().size() > 0) {
                            adminTjslLaporanProposalAdapter = new AdminTjslLaporanProposalAdapter(getContext(), proposalModelList);
                            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                            rvProposal.setLayoutManager(linearLayoutManager);
                            rvProposal.setAdapter(adminTjslLaporanProposalAdapter);
                            rvProposal.setHasFixedSize(false);
                            tvEmpty.setVisibility(View.GONE);
                            emptyAnimation.setVisibility(View.GONE);
                            dialog.dismiss();

                        }else {
                            tvEmpty.setVisibility(View.VISIBLE);
                            emptyAnimation.setVisibility(View.VISIBLE);
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<List<ProposalModel>> call, Throwable t) {
                        tvEmpty.setVisibility(View.GONE);
                        emptyAnimation.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
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

    private void filter(String text){
        ArrayList<ProposalModel>filteredList = new ArrayList<>();
        for (ProposalModel item : proposalModelList) {
            if (item.getNoProposal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminTjslLaporanProposalAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
            Toasty.normal(getContext(), "Tidak ditemukan", Toasty.LENGTH_SHORT).show();
        }else {
            adminTjslLaporanProposalAdapter.filter(filteredList);
        }

    }

    private void getKodeLoket() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();

        pengajuInterface.getAllLoket().enqueue(new Callback<List<LoketModel>>() {
            @Override
            public void onResponse(Call<List<LoketModel>> call, Response<List<LoketModel>> response) {
                loketModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {
                    spinnerKodeLoketAdapter = new SpinnerKodeLoketAdapter(getContext(), loketModelList);
                    spkodeLoket.setAdapter(spinnerKodeLoketAdapter);
                    dialog.dismiss();

                }else {
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<List<LoketModel>> call, Throwable t) {
                dialog.dismiss();

                dialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getKodeLoket();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();



            }
        });




    }
}