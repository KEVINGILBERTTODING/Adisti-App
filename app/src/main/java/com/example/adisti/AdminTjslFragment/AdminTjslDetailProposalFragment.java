package com.example.adisti.AdminTjslFragment;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
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
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.PengajuFragment.PengajuEditProposalFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTjslDetailProposalFragment extends Fragment {
    PengajuInterface pengajuInterface;
    SharedPreferences sharedPreferences;
    String userID, proposalId, fileProposal;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, et_loket, etTanggalProposal, etLatarBelakangProposal;
    Button btnKembali, btnRefresh, btnDownloadSurat;
    TextView tvStatus;
    CardView cvStatus;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_admin_tjsl_detail_proposal, container, false);
        init(view);
        getProposalDetail();



       btnKembali.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });






        btnDownloadSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_SURAT_KACAB + proposalId;
                String title = "FileSuratKacab";
                String description = "Downloading PDF file";
                String fileName = fileProposal;

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setTitle(title);
                request.setDescription(description);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.allowScanningByMediaScanner();

                DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
            }
        });



       return view;
    }

    private void init(View view) {

        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);
        proposalId = getArguments().getString("proposal_id");
        etNoProposal = view.findViewById(R.id.et_noProposal);
        etInstansi = view.findViewById(R.id.et_instansi);
        etTanggalProposal = view.findViewById(R.id.et_tglProposal);
        etBantuan = view.findViewById(R.id.et_bantuan);
        btnDownloadSurat = view.findViewById(R.id.btnDownloadSurat);
        etNamaPengaju = view.findViewById(R.id.et_namaPengaju);
        etEmail = view.findViewById(R.id.et_emailPengaju);
        etLatarBelakangProposal = view.findViewById(R.id.et_latarBelakangProposal);
        etAlamat = view.findViewById(R.id.et_alamat);
        tvStatus = view.findViewById(R.id.tvStatus);
        cvStatus = view.findViewById(R.id.cvStatus);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        et_loket = view.findViewById(R.id.et_loket);
        btnKembali = view.findViewById(R.id.btnKembali);
        etPdfPath.setEnabled(false);
        etTanggalProposal.setEnabled(false);
        etAlamat.setEnabled(false);
        etBantuan.setEnabled(false);
        etEmail.setEnabled(false);
        etInstansi.setEnabled(false);
        etJabatan.setEnabled(false);
        etNamaPengaju.setEnabled(false);
        etNoProposal.setEnabled(false);
        etNoTelp.setEnabled(false);
        et_loket.setEnabled(false);
        etLatarBelakangProposal.setEnabled(false);



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
                            etNoProposal.setText(response.body().getNoProposal());
                            etTanggalProposal.setText(response.body().getTglProposal());
                            etInstansi.setText(response.body().getAsalProposal());
                            etBantuan.setText(response.body().getBantuanDiajukan());
                            etNamaPengaju.setText(response.body().getNamaPihak());
                            etEmail.setText(response.body().getEmailPengaju());
                            etAlamat.setText(response.body().getAlamatPihak());
                            etNoTelp.setText(response.body().getNoTelpPihak());
                            etJabatan.setText(response.body().getJabatanPengaju());
                            etPdfPath.setText(response.body().getFileProposal());
                            et_loket.setText(response.body().getNamaLoketPengajuan());
                            fileProposal = response.body().getFileProposal();
                            etLatarBelakangProposal.setText(response.body().getLatarBelakangPengajuan());

                            if (response.body().getStatus().equals("Diterima")){
                                tvStatus.setText("Diterima");
                                btnDownloadSurat.setText("Download Surat Persetujuan");
                                btnDownloadSurat.setVisibility(View.VISIBLE);
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.green));
                                Dialog dialogSuccess = new Dialog(getContext());
                                dialogSuccess.setContentView(R.layout.dialog_proposal_diterima);
                                dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                dialogSuccess.setCanceledOnTouchOutside(false);
                                Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                                btnOke.setText("Oke");
                                btnOke.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogSuccess.dismiss();

                                    }
                                });
                                dialogSuccess.show();
                            }else if (response.body().getStatus().equals("Ditolak")){
                                tvStatus.setText("Ditolak");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                                btnDownloadSurat.setText("Download Surat Penolakan");
                                btnDownloadSurat.setVisibility(View.VISIBLE);
                            }else {
                                if (response.body().getVerified().equals("0")) {
                                    tvStatus.setText("Tidak lolos verifikasi");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                                    btnDownloadSurat.setVisibility(View.GONE);
                                }else {
                                    tvStatus.setText("Menunggu");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.yelllow));
                                    btnDownloadSurat.setVisibility(View.GONE);
                                }
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
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
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