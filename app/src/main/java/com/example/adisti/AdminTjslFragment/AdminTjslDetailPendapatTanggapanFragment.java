package com.example.adisti.AdminTjslFragment;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.adisti.DcmFragment.DcmKabagDetailPendapatFragment;
import com.example.adisti.DcmFragment.DcmKacabDetailPendapatFragment;
import com.example.adisti.DcmFragment.DcmKasubagDetailPendapatFragment;
import com.example.adisti.FileDownload;
import com.example.adisti.Model.HasilSurveyModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.example.adisti.Util.PtsInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTjslDetailPendapatTanggapanFragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etNilaiPengajuan, etBarangDiajukan, etKelayakan, etBentukBantuan;

    Button  btnBatal, btnDetailPendapatKasubag, btnDetailPendapatKabag,
            btnDetailPendapatKacab, btnDownload, btnEntryRealisasiBantuan;
    SharedPreferences sharedPreferences;
    String proposalId, kodeLoket, noUrutProposal, userId;
    PtsInterface ptsInterface;
    PengajuInterface pengajuInterface;
    LinearLayout layoutBentukBantuan, layoutKabag, layoutKacab;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_detail_pendapat_tanggapan, container, false);
        init(view);
        displayHasilSurvey();



        btnDetailPendapatKasubag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new DcmKasubagDetailPendapatFragment());
            }
        });

        btnDetailPendapatKabag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new DcmKabagDetailPendapatFragment());
            }
        });

        btnDetailPendapatKacab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new DcmKacabDetailPendapatFragment());
            }
        });



        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_PROPOSAL+proposalId;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        btnEntryRealisasiBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminTjslInsertRealisasiBantuanFragment());
            }
        });









        return view;
    }

    private void init(View view) {
        etNamaPetugasSurvey = view.findViewById(R.id.etNamaPetugasSurvey);
        etJabatanPetugasSurvey = view.findViewById(R.id.etJabatanPetugasSurvey);
        btnBatal = view.findViewById(R.id.btnBatal);
        etKelayakan = view.findViewById(R.id.spKelayakan);
        layoutKabag = view.findViewById(R.id.layoutKabag);
        layoutKacab = view.findViewById(R.id.layoutKacab);
        etNilaiPengajuan = view.findViewById(R.id.etNilaiPengajuan);
        etBarangDiajukan = view.findViewById(R.id.etBarangDiajukan);
        etBentukBantuan = view.findViewById(R.id.spBentukBantuan);
        layoutBentukBantuan = view.findViewById(R.id.layoutBantuan);
        btnDetailPendapatKasubag = view.findViewById(R.id.btnDetailPendapatKasubag);
        btnDetailPendapatKabag = view.findViewById(R.id.btnDetailPendapatKabag);
        btnDownload = view.findViewById(R.id.btnDownload);
        btnEntryRealisasiBantuan = view.findViewById(R.id.btnEntryRealisasiBantuan);
        btnDetailPendapatKacab = view.findViewById(R.id.btnDetailPendapatKacab);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        userId = sharedPreferences.getString("user_id", null);
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        noUrutProposal = getArguments().getString("no_urut_proposal");
        ptsInterface = DataApi.getClient().create(PtsInterface.class);

        getProposalDetail();


    }


    private void displayHasilSurvey() {

        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        ptsInterface.getHasilSurveyById(proposalId).enqueue(new Callback<HasilSurveyModel>() {
            @Override
            public void onResponse(Call<HasilSurveyModel> call, Response<HasilSurveyModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    etNamaPetugasSurvey.setText(response.body().getNamaPetugasSurvey());
                    etJabatanPetugasSurvey.setText(response.body().getJabatan());
                    etKelayakan.setText(response.body().getKelayakan());
                    etBentukBantuan.setText(response.body().getBentukBantuan());
                    etNilaiPengajuan.setText(response.body().getNilaiPengajuan());


                    if (response.body().getBentukBantuan().equals("Barang")) {
                        layoutBentukBantuan.setVisibility(View.VISIBLE);
                        etBarangDiajukan.setText(response.body().getBarangDiajukan());
                    }else {
                        layoutBentukBantuan.setVisibility(View.GONE);
                    }

                }else {
                    progressDialog.dismiss();
                    Toasty.error(getContext(), "Terjadi Kesalahan", Toasty.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<HasilSurveyModel> call, Throwable t) {
                progressDialog.dismiss();

                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayHasilSurvey();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();


            }
        });


    }

    private void replace(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("proposal_id", proposalId);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, fragment)
                .addToBackStack(null).commit();
    }


    private void getProposalDetail() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();



        pengajuInterface.getProposalById(proposalId)
                .enqueue(new Callback<ProposalModel>() {
                    @Override
                    public void onResponse(Call<ProposalModel> call, Response<ProposalModel> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getPendapatKasubag().equals("1")) {
                                btnDetailPendapatKasubag.setEnabled(true);
                            }else {
                                btnDetailPendapatKasubag.setEnabled(false);
                                btnDetailPendapatKasubag.setBackgroundColor(getContext().getColor(R.color.light_gray2));
                                btnDetailPendapatKasubag.setText("Belum input pendapat");
                            }
                            if (response.body().getPendapatKabag().equals("1")) {
                                btnDetailPendapatKabag.setEnabled(true);
                            }else {
                                btnDetailPendapatKabag.setEnabled(false);
                                btnDetailPendapatKabag.setBackgroundColor(getContext().getColor(R.color.light_gray2));
                                btnDetailPendapatKabag.setText("Belum input pendapat");
                            }

                            if (response.body().getPendapatKacab().equals("1")) {
                                btnDetailPendapatKacab.setEnabled(true);
                            }else {
                                btnDetailPendapatKacab.setEnabled(false);
                                btnDetailPendapatKacab.setBackgroundColor(getContext().getColor(R.color.light_gray2));
                                btnDetailPendapatKacab.setText("Belum input pendapat");

                            }







                            dialog.dismiss();

                        }else {
                            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<ProposalModel> call, Throwable t) {

                        dialog.dismiss();
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getProposalDetail();
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.show();



                    }
                });

    }





}