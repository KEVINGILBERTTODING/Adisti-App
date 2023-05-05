package com.example.adisti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Util.AuthInterface;
import com.example.adisti.Util.DataApi;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView tvMasuk;
    EditText etNama, etUsername, etEmail, etPassword, etNoTelp, etInstansi,
    etJabatan, etAlamat;
    Button btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                register();
            }
        });
    }

    private void init() {
        tvMasuk = findViewById(R.id.tvMasuk);
        etNama = findViewById(R.id.et_nama_lengkap);
        etUsername = findViewById(R.id.et_Username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etNoTelp = findViewById(R.id.et_no_telepon);
        etInstansi = findViewById(R.id.et_instansi);
        etJabatan = findViewById(R.id.et_jabatan);
        etAlamat = findViewById(R.id.et_Alamat);
        btnDaftar = findViewById(R.id.btndaftar);

    }
    private void register() {

        if (etNama.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field nama lengkap tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etUsername.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field username tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etEmail.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field email tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field password tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etNoTelp.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field no telepon tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etInstansi.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field instansi tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etJabatan.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field jabatan tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else if (etAlamat.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), "Field alamat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        } else {

            Dialog dialog = new Dialog(RegisterActivity.this);
            dialog.setContentView(R.layout.dialog_progress_bar);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final TextView tvMainText = dialog.findViewById(R.id.tvMainText);
            tvMainText.setText("Mendaftar Akun...");
            dialog.show();


            HashMap map = new HashMap();
            map.put("username", RequestBody.create(MediaType.parse("text/plain"), etUsername.getText().toString()));
            map.put("password", RequestBody.create(MediaType.parse("text/plain"), etPassword.getText().toString()));
            map.put("nama_lengkap", RequestBody.create(MediaType.parse("text/plain"), etNama.getText().toString()));
            map.put("email", RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString()));
            map.put("no_telepon", RequestBody.create(MediaType.parse("text/plain"), etNoTelp.getText().toString()));
            map.put("instansi", RequestBody.create(MediaType.parse("text/plain"), etInstansi.getText().toString()));
            map.put("jabatan", RequestBody.create(MediaType.parse("text/plain"), etJabatan.getText().toString()));
            map.put("alamat", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));


            AuthInterface authInterface = DataApi.getClient().create(AuthInterface.class);
            authInterface.register(map).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    ResponseModel responseModel = response.body();
                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                        Toasty.success(getApplicationContext(), "Berhasil mendaftar", Toasty.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                        dialog.dismiss();

                    }else {
                        Toasty.error(getApplicationContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel>call, Throwable t) {
                    Dialog dialogNoConnection = new Dialog(RegisterActivity.this);
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
}