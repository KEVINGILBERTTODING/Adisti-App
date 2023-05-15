package com.example.adisti.DcmFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.UserModel;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.DcmInterface;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DcmKasubagInsertPendapatFragment extends Fragment {


    Button  btnSubmit, btnBatal;
    ImageView ivQrcode;
    DcmInterface dcmInterface;

    SharedPreferences sharedPreferences;
    String proposalId, userId;
    EditText etNamaKasubag, etJabatanKasubag, etPendapatKasubag, etNilaiPengajuan;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dcm_kasubag_insert_pendapat, container, false);
        init(view);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });




        return view;
    }

    private void init(View view) {

        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnBatal = view.findViewById(R.id.btnBatal);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        userId = sharedPreferences.getString("user_id", null);
        etJabatanKasubag = view.findViewById(R.id.etJabatanKasubag);
        etNamaKasubag = view.findViewById(R.id.etNamaKasubag);
        etPendapatKasubag = view.findViewById(R.id.etPendapatKasubag);
        etNilaiPengajuan = view.findViewById(R.id.etNilaiPengajuan);
        ivQrcode = view.findViewById(R.id.ivQrCode);
        dcmInterface = DataApi.getClient().create(DcmInterface.class);

        loadUser();

    }

    private void insertData() {

        if (etNamaKasubag.getText().toString().isEmpty()) {
            etNamaKasubag.setError("Nama Tidak Boleh Kosong");
            etNamaKasubag.requestFocus();
            return;
        } else  if (etJabatanKasubag.getText().toString().isEmpty()) {
            etJabatanKasubag.setError("Jabatan Tidak Boleh Kosong");
            etJabatanKasubag.requestFocus();
            return;
        }else  if (etPendapatKasubag.getText().toString().isEmpty()) {
            etPendapatKasubag.setError("Pendapat Tidak Boleh Kosong");
            etPendapatKasubag.requestFocus();
            return;
        }else  if (etNilaiPengajuan.getText().toString().isEmpty()) {
            etNilaiPengajuan.setError("Nilai Pengajuan Tidak Boleh Kosong");
            etNilaiPengajuan.requestFocus();
            return;
        }else  if (Integer.parseInt(etNilaiPengajuan.getText().toString()) > 100) {
            etNilaiPengajuan.setError("Nilai Pengajuan Tidak Boleh Lebih Dari 100");
            etNilaiPengajuan.requestFocus();
            return;
        }else {

            Dialog progressDialog = new Dialog(getContext());
            progressDialog.setContentView(R.layout.dialog_progress_bar);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            HashMap map = new HashMap();
            map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
            map.put("nama_kasubag", RequestBody.create(MediaType.parse("text/plain"), etNamaKasubag.getText().toString()));
            map.put("jabatan_kasubag", RequestBody.create(MediaType.parse("text/plain"), etJabatanKasubag.getText().toString()));
            map.put("pendapat_kasubag", RequestBody.create(MediaType.parse("text/plain"), etPendapatKasubag.getText().toString()));
            map.put("nilai_pengajuan_kasubag", RequestBody.create(MediaType.parse("text/plain"), etNilaiPengajuan.getText().toString()));

            dcmInterface.insertPendapatKasubag(map).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body().getCode() == 200) {
                        progressDialog.dismiss();
                        Dialog dialogSuccess = new Dialog(getContext());
                        dialogSuccess.setCanceledOnTouchOutside(false);
                        dialogSuccess.setCancelable(false);
                        dialogSuccess.setContentView(R.layout.dialog_success);
                        dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                        btnOke.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogSuccess.dismiss();
                            }
                        });
                        dialogSuccess.show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameDcm, new DcmPendapatTanggapanFragment())
                                .commit();
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(getContext(), "Terjadi Kesalahan", Toasty.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Dialog dialogNoConnection = new Dialog(getContext());
                    dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                    dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialogNoConnection.setCancelable(false);
                    dialogNoConnection.setCanceledOnTouchOutside(false);
                    Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                    btnRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            insertData();
                            dialogNoConnection.dismiss();
                        }
                    });

                    dialogNoConnection.show();

                }
            });







        }

    }

    private void loadUser() {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        dcmInterface.getDetailUserById(userId).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    etNamaKasubag.setText(response.body().getNamaLengkap());
                    etJabatanKasubag.setText(response.body().getJabatan());
                    Glide.with(getContext())
                            .load(response.body().getFileQrCode())
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .fitCenter()
                            .into(ivQrcode);

                }else {
                    progressDialog.dismiss();
                    btnSubmit.setEnabled(false);
                    Toasty.error(getContext(), "Terjadi Kesalahan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                progressDialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCancelable(false);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadUser();
                        dialogNoConnection.dismiss();
                    }
                });

                dialogNoConnection.show();

            }
        });

    }







}