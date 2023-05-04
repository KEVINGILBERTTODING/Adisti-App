package com.example.adisti.PengajuFragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PicAdapter.SpinnerKodeLoketAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuDetailProposalFragment extends Fragment {
    PengajuInterface pengajuInterface;
    Button btnDownload;
    SharedPreferences sharedPreferences;
    String userID, proposalId, fileProposal;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, et_loket, etTanggalProposal;
    Button btnKembali, btnRefresh;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pengaju_detail_proposal, container, false);
        init(view);



       btnKembali.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });

       getProposalDetail();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_PROPOSAL+proposalId;
                String title = fileProposal;
                String description = "Downloading PDF file";
                String fileName = fileProposal;
                Log.d("dasd", "onClick: " +url);


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







       return view;
    }

    private void init(View view) {

        btnDownload = view.findViewById(R.id.btnDownload);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);
        proposalId = getArguments().getString("proposal_id");
        etNoProposal = view.findViewById(R.id.et_noProposal);
        etInstansi = view.findViewById(R.id.et_instansi);
        etTanggalProposal = view.findViewById(R.id.et_tglProposal);
        etBantuan = view.findViewById(R.id.et_bantuan);
        etNamaPengaju = view.findViewById(R.id.et_namaPengaju);
        etEmail = view.findViewById(R.id.et_emailPengaju);
        etAlamat = view.findViewById(R.id.et_alamat);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        et_loket = view.findViewById(R.id.et_loket);
        btnKembali = view.findViewById(R.id.btnKembali);
        etPdfPath.setEnabled(false);
        etAlamat.setEnabled(false);
        etBantuan.setEnabled(false);
        etEmail.setEnabled(false);
        etInstansi.setEnabled(false);
        etJabatan.setEnabled(false);
        etNamaPengaju.setEnabled(false);
        etNoProposal.setEnabled(false);
        etNoTelp.setEnabled(false);
        et_loket.setEnabled(false);



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