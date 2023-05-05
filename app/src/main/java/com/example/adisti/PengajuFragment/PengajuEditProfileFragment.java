package com.example.adisti.PengajuFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adisti.LoginActivity;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.R;
import com.example.adisti.RegisterActivity;
import com.example.adisti.Util.AuthInterface;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuEditProfileFragment extends Fragment {

    EditText etNama, etUsername, etEmail, etNoTelp, etInstansi,
            etJabatan, etAlamat;
    Button btnSimpan;
    SharedPreferences sharedPreferences;
    String userId;
    PengajuInterface pengajuInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengaju_edit_profile, container, false);
        init(view);
        loadDataUser();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNama.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field nama lengkap tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etUsername.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field username tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etEmail.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field email tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }  else if (etNoTelp.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field no telepon tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etInstansi.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field instansi tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etJabatan.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field jabatan tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else if (etAlamat.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field alamat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                } else {

                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_progress_bar);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final TextView tvMainText = dialog.findViewById(R.id.tvMainText);
                    tvMainText.setText("Mengubah profile...");
                    dialog.show();


                    HashMap map = new HashMap();
                    map.put("username", RequestBody.create(MediaType.parse("text/plain"), etUsername.getText().toString()));
                    map.put("nama_lengkap", RequestBody.create(MediaType.parse("text/plain"), etNama.getText().toString()));
                    map.put("email", RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString()));
                    map.put("no_telepon", RequestBody.create(MediaType.parse("text/plain"), etNoTelp.getText().toString()));
                    map.put("instansi", RequestBody.create(MediaType.parse("text/plain"), etInstansi.getText().toString()));
                    map.put("jabatan", RequestBody.create(MediaType.parse("text/plain"), etJabatan.getText().toString()));
                    map.put("alamat", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));
                    map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));


                    PengajuInterface pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
                    pengajuInterface.updateProfile(map).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            ResponseModel responseModel = response.body();
                            if (response.isSuccessful() && responseModel.getCode() == 200) {
                                Dialog dialogSuccess = new Dialog(getContext());
                                dialogSuccess.setContentView(R.layout.dialog_success);
                                dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                dialogSuccess.setCanceledOnTouchOutside(false);
                                Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                                btnOke.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogSuccess.dismiss();
                                    }
                                });
                                dialogSuccess.show();
                                Toasty.success(getContext(), "Berhasil mengubah profile", Toasty.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                                dialog.dismiss();
                            }else {
                                Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Dialog dialogNoConnection = new Dialog(getContext());
                            dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                            dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            final Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                            btnRefresh.setText("Oke");
                            dialogNoConnection.show();
                            btnRefresh.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogNoConnection.dismiss();
                                }
                            });
                            dialog.dismiss();

                        }
                    });


                }
            }
        });






        return view;
    }

    private void init(View view) {
        etNama = view.findViewById(R.id.et_nama_lengkap);
        etUsername = view.findViewById(R.id.et_Username);
        etEmail = view.findViewById(R.id.et_email);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etInstansi = view.findViewById(R.id.et_instansi);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etAlamat = view.findViewById(R.id.et_Alamat);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
    }

    private void loadDataUser() {
        Dialog dialogLoading = new Dialog(getContext());
        dialogLoading.setContentView(R.layout.dialog_progress_bar);
        dialogLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogLoading.setCanceledOnTouchOutside(false);
        dialogLoading.setCancelable(false);
        dialogLoading.dismiss();

        pengajuInterface.getPengajuById(userId).enqueue(new Callback<PengajuModel>() {
            @Override
            public void onResponse(Call<PengajuModel> call, Response<PengajuModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    etNama.setText(response.body().getNamaLengkap());
                    etUsername.setText(response.body().getUserName());
                    etEmail.setText(response.body().getEmail());
                    etNoTelp.setText(response.body().getNoTelp());
                    etInstansi.setText(response.body().getInstansi());
                    etJabatan.setText(response.body().getJabatan());
                    etAlamat.setText(response.body().getAlamat());
                    dialogLoading.dismiss();


                }else {
                    Toasty.error(getContext(), "Gagal memuat data", Toasty.LENGTH_SHORT).show();
                    dialogLoading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PengajuModel> call, Throwable t) {
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                dialogLoading.dismiss();

            }
        });


    }
}