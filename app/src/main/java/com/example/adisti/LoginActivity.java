package com.example.adisti;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adisti.Model.UserModel;
import com.example.adisti.Util.AuthInterface;
import com.example.adisti.Util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        if (sharedPreferences.getBoolean("logged_in", false)) {
            if (sharedPreferences.getString("role", null).equals("pic")) {
                startActivity(new Intent(LoginActivity.this, PicMainActivity.class));
                finish();

            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void init() {
        etPassword = findViewById(R.id.et_password);
        etUsername = findViewById(R.id.et_username);
        btnLogin = findViewById(R.id.btnLogin);
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void login() {
        if (etUsername.getText().toString().isEmpty()){
            Toasty.error(LoginActivity.this, "Field username tidak boleh kosong", Toasty.LENGTH_SHORT).show();

        }else if (etPassword.getText().toString().isEmpty()) {
            Toasty.error(LoginActivity.this, "Field password tidak boleh kosong", Toasty.LENGTH_SHORT).show();
        }else {
            Dialog dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.dialog_progress_bar);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
            final TextView tvMainText;
            tvMainText = dialog.findViewById(R.id.tvMainText);
            tvMainText.setText("Athentifikasi");
            dialog.show();

            AuthInterface authInterface = DataApi.getClient().create(AuthInterface.class);
            authInterface.login(etUsername.getText().toString(), etPassword.getText().toString())
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            UserModel userModel = response.body();
                            if (response.isSuccessful() && userModel.getCode() == 200) {
                                editor.putBoolean("logged_in", true);
                                editor.putString("user_id", userModel.getUserId());
                                editor.putString("nama", userModel.getNama());
                                editor.putString("role", userModel.getRole());
                                editor.putString("kode_loket", userModel.getKodeLoket());
                                editor.apply();
                                Toasty.success(LoginActivity.this, "Hai "+ userModel.getNama(), Toasty.LENGTH_SHORT).show();

                                if (userModel.getRole().equals("pic")) {
                                    startActivity(new Intent(LoginActivity.this, PicMainActivity.class));
                                    finish();
                                }
                                dialog.dismiss();

                            }else {
                                Toasty.error(LoginActivity.this, userModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toasty.error(LoginActivity.this, "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                    });
        }
    }
}