package com.example.adisti.AdminTjslFragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.RealisasiBantuanModel;
import com.example.adisti.R;
import com.example.adisti.Util.AdminTjslInterface;
import com.example.adisti.Util.DataApi;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTjslDetailRealisasiBantuan2Fragment extends Fragment {


    Button  btnEntry, btnBatal, btnFotoKegiatanPicker, btnKuitansiPicker, btnBastPicker,
            btnSptPicker, btnBuktiPembayaranPicker;
    TextView tvTglKegiatan, btnLinkBerita;

    SharedPreferences sharedPreferences;
    String proposalId, userId, linkBerita, fotoKegiatan;
    AdminTjslInterface adminTjslInterface;
    LinearLayout layoutBarangBerupa;
    EditText etTempatKegiatan, etNominalBantua, etFotoKegiatanPath, etKuitansiPath,
            etBastPath, etSptPath, etBuktiPembayaranPath, etBarangBerupa, etJenisBantuan;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_detail_realisasi_bantuan2, container, false);
        init(view);


        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new AdminTjslInsertLpjKegiatanFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, fragment)
                        .addToBackStack(null).commit();

            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnFotoKegiatanPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("image", DataApi.URL_FILE_REALISASI+fotoKegiatan);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, fragment)
                        .addToBackStack(null).commit();
            }
        });
        btnKuitansiPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFileRealisasiBantuan("kuitansi");

            }
        });
        btnBastPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFileRealisasiBantuan("bast");

            }
        });
        btnSptPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFileRealisasiBantuan("spt");

            }
        });
        btnBuktiPembayaranPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFileRealisasiBantuan("erp");

            }
        });

        btnLinkBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open browser
                String url = linkBerita;
                Uri uri = Uri.parse(url);

                String packageName = "com.android.chrome";

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                browserIntent.setPackage(packageName);
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);



            }
        });




        return view;
    }

    private void init(View view) {

        btnEntry = view.findViewById(R.id.btnEntry);
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
        btnLinkBerita = view.findViewById(R.id.btnLinkBerita);
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
                    linkBerita = response.body().getLinkBerita();
                    fotoKegiatan = response.body().getFotoKegiatan();
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
                    btnEntry.setEnabled(false);
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

    private void downloadFileRealisasiBantuan(String jenis) {
        String url = DataApi.URL_DOWLOAD_FILE_REALISASI+proposalId + "/" + jenis;
        String title = "File_" + jenis + "_" + proposalId;
        String description = "Downloading PDF file";
        String fileName = "File_" + jenis + "_" + proposalId;


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



}