package com.example.adisti.AdminTjslFragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adisti.Model.RealisasiBantuanModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.R;
import com.example.adisti.Util.AdminTjslInterface;
import com.example.adisti.Util.DataApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTjslDetailRealisasiBantuanFragment extends Fragment {


    Button  btnSubmit, btnBatal, btnFotoKegiatanPicker, btnKuitansiPicker, btnBastPicker,
            btnSptPicker, btnBuktiPembayaranPicker;
    TextView tvTglKegiatan, tvLinkBerita;

    SharedPreferences sharedPreferences;
    String proposalId, userId;
    AdminTjslInterface adminTjslInterface;
    LinearLayout layoutBarangBerupa;
    EditText etTempatKegiatan, etNominalBantua, etFotoKegiatanPath, etKuitansiPath,
            etBastPath, etSptPath, etBuktiPembayaranPath, etBarangBerupa, etJenisBantuan;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_detail_realisasi_bantuan, container, false);
        init(view);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insertData();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

//
//
//        btnFotoKegiatanPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFile(1, "image/*");
//            }
//        });
//        btnKuitansiPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFile(2, "application/pdf");
//            }
//        });
//        btnBastPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFile(3, "application/pdf");
//            }
//        });
//        btnSptPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFile(4, "application/pdf");
//            }
//        });
//        btnBuktiPembayaranPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFile(5, "application/pdf");
//            }
//        });







        return view;
    }

    private void init(View view) {

        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnBatal = view.findViewById(R.id.btnBatal);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        userId = sharedPreferences.getString("user_id", null);
        tvTglKegiatan = view.findViewById(R.id.tvTglKegiatan);
        etBarangBerupa = view.findViewById(R.id.etBarangBerupa);
        etJenisBantuan = view.findViewById(R.id.etJenisBantuan);
        layoutBarangBerupa = view.findViewById(R.id.layoutBarangBerupa);
        etTempatKegiatan = view.findViewById(R.id.etTempatKegiatan);
        etNominalBantua = view.findViewById(R.id.etNominalBantuan);
        tvLinkBerita = view.findViewById(R.id.tvLinkBerita);
        etFotoKegiatanPath = view.findViewById(R.id.etFotoKegiatanPath);
        etKuitansiPath = view.findViewById(R.id.etKuitansiPath);
        etBastPath = view.findViewById(R.id.etBastPath);
        etSptPath = view.findViewById(R.id.etSptPath);
        etBuktiPembayaranPath = view.findViewById(R.id.etBuktiPembayaranPath);
        btnFotoKegiatanPicker = view.findViewById(R.id.btnFotoKegiatanPicker);
        btnKuitansiPicker = view.findViewById(R.id.btnKuitansiPicker);
        btnBastPicker = view.findViewById(R.id.btnBastPicker);
        btnSptPicker = view.findViewById(R.id.btnSptPicker);
        btnBuktiPembayaranPicker = view.findViewById(R.id.btnBuktiPembayaranPicker);
        adminTjslInterface = DataApi.getClient().create(AdminTjslInterface.class);

        loadDataRealisasiBantuan();


    }

    private void loadDataRealisasiBantuan() {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        adminTjslInterface.getRealisasiBantuanById(proposalId).enqueue(new Callback<RealisasiBantuanModel>() {
            @Override
            public void onResponse(Call<RealisasiBantuanModel> call, Response<RealisasiBantuanModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // set format rupiah
                    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                    decimalFormat.setGroupingUsed(true);
                    decimalFormat.setGroupingSize(3);


                    progressDialog.dismiss();
                    tvTglKegiatan.setText(response.body().getTanggalKegiatan());
                    etTempatKegiatan.setText(response.body().getTempatKegiatan());
                    etNominalBantua.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalBantuan())));
                    etJenisBantuan.setText(response.body().getJenisBantuan());
                    etBarangBerupa.setText(response.body().getBarangBerupa());
                    tvLinkBerita.setText(response.body().getLinkBerita());
                    etFotoKegiatanPath.setText(response.body().getFotoKegiatan());
                    etKuitansiPath.setText(response.body().getKuitansi());
                    etBastPath.setText(response.body().getBast());
                    etSptPath.setText(response.body().getSpt());
                    etBuktiPembayaranPath.setText(response.body().getErp());



                    if (response.body().getJenisBantuan().equals("Uang Tunai")) {
                        layoutBarangBerupa.setVisibility(View.GONE);
                    }else {
                        layoutBarangBerupa.setVisibility(View.VISIBLE);
                    }


                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    btnSubmit.setEnabled(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RealisasiBantuanModel> call, Throwable t) {
                progressDialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setCancelable(false);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDataRealisasiBantuan();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.show();

            }
        });
    }










}