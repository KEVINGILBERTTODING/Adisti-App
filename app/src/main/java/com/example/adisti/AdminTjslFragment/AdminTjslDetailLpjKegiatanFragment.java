package com.example.adisti.AdminTjslFragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.LpjKegiatanModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.R;
import com.example.adisti.Util.AdminTjslInterface;
import com.example.adisti.Util.DataApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTjslDetailLpjKegiatanFragment extends Fragment {


    Button  btnEdit, btnBatal, btnFotoKegiatanPicker, btnLpjKegiatanPicker;

    SharedPreferences sharedPreferences;
    EditText etFotoKegiatanPath, etLpjKegiatanPath;
    String proposalId, userId, fotoKegiatan, fileLpj;
    AdminTjslInterface adminTjslInterface;


    ImageView ivLpj, ivFotoKegiatan;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_detail_lpj_kegiatan, container, false);
        init(view);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new AdminTjslUpdateLpjKegiatanFragment();
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
                bundle.putString("image", DataApi.URL_FILE_LPJ_KEGIATAN + fotoKegiatan);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, fragment)
                        .addToBackStack(null).commit();
            }
        });
        btnLpjKegiatanPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = DataApi.URL_DOWNLOAD_FILE_LPJ_KEGIATAN + proposalId;
                String title = fileLpj;
                String description = "Downloading PDF file";
                String fileName = fileLpj;


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

        btnEdit = view.findViewById(R.id.btnEdit);
        btnBatal = view.findViewById(R.id.btnBatal);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        userId = sharedPreferences.getString("user_id", null);

        btnFotoKegiatanPicker = view.findViewById(R.id.btnFotoKegiatanPicker);
        btnLpjKegiatanPicker = view.findViewById(R.id.btnLpjKegiatanPicker);
        etFotoKegiatanPath  = view.findViewById(R.id.etFotoKegiatanPath);
        etLpjKegiatanPath  = view.findViewById(R.id.etLpjKegiatanPath);
        ivLpj = view.findViewById(R.id.ivLpj);
        ivFotoKegiatan = view.findViewById(R.id.ivFotoKegiatan);

        adminTjslInterface = DataApi.getClient().create(AdminTjslInterface.class);

        loadData();





    }

    private void loadData() {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        adminTjslInterface.getlPjKegiatanById(proposalId).enqueue(new Callback<LpjKegiatanModel>() {
            @Override
            public void onResponse(Call<LpjKegiatanModel> call, Response<LpjKegiatanModel> response) {
                if (response.isSuccessful() && response.body() != null){

                    etFotoKegiatanPath.setText(response.body().getFotoKegiatan());
                    etLpjKegiatanPath.setText(response.body().getLpj());
                    fotoKegiatan = response.body().getFotoKegiatan();
                    fileLpj = response.body().getLpj();

                    progressDialog.dismiss();

                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LpjKegiatanModel> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCancelable(false);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.dismiss();
                        dialogNoConnection.dismiss();
                        loadData();
                    }
                });

                dialogNoConnection.show();

            }
        });
    }




}