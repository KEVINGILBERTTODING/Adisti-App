package com.example.adisti.PengajuFragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.LoginActivity;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuFragment.UserGuide.UserGuideFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;

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

public class PengajuProfileFragment extends Fragment {
    ImageView ivProfile, ivProfileImage;
    TextView tvEmail, tvNamaLengkap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userId;
    PengajuInterface pengajuInterface;
    Button btnRefresh;
    String photoProfile, realImagePath;
    ImageButton btnEditPhotoProfile;
    private File file;

    RelativeLayout menuLogOut, menuUbahProfile, menuUbahPassword, menuAbout, menuUserGuide;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengaju_profile, container, false);
        init(view);
        loadProfile();
        btnEditPhotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogPhotoProfile = new Dialog(getContext());
                dialogPhotoProfile.setContentView(R.layout.layout_edit_photo_profile);
                dialogPhotoProfile.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogPhotoProfile.setCanceledOnTouchOutside(false);
                final Button btnBatal, btnSimpan, btnImagePicker;
                btnBatal = dialogPhotoProfile.findViewById(R.id.btnBatal);
                btnSimpan = dialogPhotoProfile.findViewById(R.id.btnSimpan);

                ivProfileImage = dialogPhotoProfile.findViewById(R.id.ivProfileImage);
                btnImagePicker = dialogPhotoProfile.findViewById(R.id.btnImagePicker);

                Glide.with(getContext()).load(photoProfile)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .fitCenter()
                        .placeholder(R.drawable.photo_default)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ivProfileImage);
                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPhotoProfile.dismiss();
                    }
                });
                btnImagePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                });
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (realImagePath !=null) {
                            Dialog progressBar = new Dialog(getContext());
                            progressBar.setContentView(R.layout.dialog_progress_bar);
                            progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            progressBar.setCanceledOnTouchOutside(false);
                            progressBar.show();

                            HashMap map = new HashMap();
                            map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));

                            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                            MultipartBody.Part image = MultipartBody.Part.createFormData("photo_profile", file.getName(), requestBody);
                            pengajuInterface.uploadPhotoProfile(map, image).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                                        Dialog dialogSuccess = new Dialog(getContext());
                                        dialogSuccess.setContentView(R.layout.dialog_success);
                                        dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        dialogSuccess.setCanceledOnTouchOutside(false);
                                        final Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                                        dialogSuccess.show();
                                        btnOke.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialogSuccess.dismiss();
                                            }
                                        });
//
                                        progressBar.dismiss();
                                        dialogPhotoProfile.dismiss();
                                        loadProfile();
                                    }else {
                                        progressBar.dismiss();
                                        Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    progressBar.dismiss();
                                    Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                                }
                            });

                        }else {
                            Toasty.error(getContext(), "Anda belum memilih gambar", Toasty.LENGTH_SHORT).show();
                        }







                    }
                });
                dialogPhotoProfile.show();
            }
        });
        menuLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogLogOut = new Dialog(getContext());
                dialogLogOut.setContentView(R.layout.layout_log_out);
                dialogLogOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final Button btnTidak, btnKeluar;
                btnTidak = dialogLogOut.findViewById(R.id.btnBatal);
                btnKeluar = dialogLogOut.findViewById(R.id.btnKeluar);
                btnKeluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logOut();
                        dialogLogOut.dismiss();
                    }
                });
                btnTidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLogOut.dismiss();
                    }
                });
                dialogLogOut.show();
            }
        });
        menuUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogResetPassword = new Dialog(getContext());
                dialogResetPassword.setContentView(R.layout.layout_ubah_password);
                dialogResetPassword.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                final EditText etOldPass, etNewPass, etKonfirPass;
                Button btnSimpan, btnBatal;
                etOldPass = dialogResetPassword.findViewById(R.id.et_old_pass);
                etNewPass = dialogResetPassword.findViewById(R.id.et_new_pass);
                etKonfirPass = dialogResetPassword.findViewById(R.id.et_password_konfir);
                btnSimpan = dialogResetPassword.findViewById(R.id.btnSimpan);
                btnBatal = dialogResetPassword.findViewById(R.id.btnCancel);

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogResetPassword.dismiss();
                    }
                });
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etOldPass.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field password lama tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        }else if (etNewPass.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field password baru tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                        } else if (etKonfirPass.getText().toString().isEmpty()) {
                            Toasty.error(getContext(), "Field password konfirmasi tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        }else {
                            if (!etNewPass.getText().toString().equals(etKonfirPass.getText().toString().toString())) {
                                Toasty.error(getContext(), "Field password tidak cocok", Toasty.LENGTH_SHORT).show();
                            }else {
                                Dialog progressDialog = new Dialog(getContext());
                                progressDialog.setContentView(R.layout.dialog_progress_bar);
                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();

                                pengajuInterface.updatePassword(
                                        userId, etOldPass.getText().toString(), etKonfirPass.getText().toString()
                                ).enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if (response.isSuccessful() && responseModel.getCode() == 200) {
                                            Dialog dialogSuccess = new Dialog(getContext());
                                            dialogSuccess.setContentView(R.layout.dialog_success);
                                            dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            dialogSuccess.setCanceledOnTouchOutside(false);
                                            final Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                                            dialogSuccess.show();
                                            btnOke.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialogSuccess.dismiss();
                                                }
                                            });
                                            dialogSuccess.show();
                                            dialogResetPassword.dismiss();
                                            progressDialog.dismiss();
                                        }else {
                                            Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                                        Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    }
                                });
                            }
                        }
                    }
                });
                dialogResetPassword.show();
            }
        });
        menuUbahProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null).replace(R.id.framePengaju, new PengajuEditProfileFragment()).commit();
            }
        });

        menuAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogAbout = new Dialog(getContext());
                dialogAbout.setContentView(R.layout.layout_about);
                dialogAbout.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnOke = dialogAbout.findViewById(R.id.btnOke);
                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogAbout.dismiss();
                    }
                });
                dialogAbout.show();
            }
        });

        menuUserGuide.setOnClickListener(View-> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePengaju, new UserGuideFragment())
                    .addToBackStack(null).commit();
        });








        return view;
    }

    private void init(View view) {


        tvEmail = view.findViewById(R.id.tvEmail);
        tvNamaLengkap = view.findViewById(R.id.tvNamaLengkap);
        ivProfile = view.findViewById(R.id.ivProfile);
        menuUbahPassword = view.findViewById(R.id.menuUbahPassword);
        menuUbahProfile = view.findViewById(R.id.menuUbahProfile);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        btnEditPhotoProfile = view.findViewById(R.id.btnImageEdit);
        menuLogOut = view.findViewById(R.id.menuLogOut);
        menuAbout = view.findViewById(R.id.menuAbout);
        menuUserGuide = view.findViewById(R.id.menuUserGuide);
        editor = sharedPreferences.edit();


    }
    private void loadProfile(){
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        pengajuInterface.getPengajuById(userId).enqueue(new Callback<PengajuModel>() {
            @Override
            public void onResponse(Call<PengajuModel> call, Response<PengajuModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvNamaLengkap.setText(response.body().getNamaLengkap());
                    tvEmail.setText(response.body().getEmail());
                    Glide.with(getContext()).load(response.body().getPhotoProfile())
                            .skipMemoryCache(false)
                            .dontAnimate()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivProfile);
                    photoProfile = response.body().getPhotoProfile();


                    dialog.dismiss();


                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PengajuModel> call, Throwable t) {
                Dialog dialog1 = new Dialog(getContext());
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setContentView(R.layout.dialog_no_connection);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                btnRefresh = dialog1.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadProfile();
                        dialog1.dismiss();
                    }
                });
                dialog1.show();

            }
        });



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                ivProfileImage.setImageURI(uri);
                file = new File(pdfPath);
                realImagePath = "sdsd";
            }
        }
    }


    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {

                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private void logOut () {
        editor.clear().apply();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }




}