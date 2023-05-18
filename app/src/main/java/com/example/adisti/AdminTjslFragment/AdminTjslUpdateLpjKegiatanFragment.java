package com.example.adisti.AdminTjslFragment;

import static android.app.Activity.RESULT_OK;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class AdminTjslUpdateLpjKegiatanFragment extends Fragment {


    Button  btnSubmit, btnBatal, btnFotoKegiatanPicker, btnLpjKegiatanPicker;

    SharedPreferences sharedPreferences;
    EditText etFotoKegiatanPath, etLpjKegiatanPath;
    String proposalId, userId, sFotoKegiatan, sFileLpj;
    AdminTjslInterface adminTjslInterface;
    private File fotoKegiatan, fileLpj;

    ImageView ivLpj, ivFotoKegiatan;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_update_lpj_kegiatan, container, false);
        init(view);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
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
                selectedFile(1, "image/*");
            }
        });
        btnLpjKegiatanPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFile(2, "application/pdf");
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

        btnFotoKegiatanPicker = view.findViewById(R.id.btnFotoKegiatanPicker);
        btnLpjKegiatanPicker = view.findViewById(R.id.btnLpjKegiatanPicker);
        etFotoKegiatanPath  = view.findViewById(R.id.etFotoKegiatanPath);
        etLpjKegiatanPath  = view.findViewById(R.id.etLpjKegiatanPath);
        ivLpj = view.findViewById(R.id.ivLpj);
        ivFotoKegiatan = view.findViewById(R.id.ivFotoKegiatan);

        adminTjslInterface = DataApi.getClient().create(AdminTjslInterface.class);

        loadData();





    }

    private void updateData() {

        if (etFotoKegiatanPath.getText().toString().isEmpty()) {
            etFotoKegiatanPath.setError("Foto Kegiatan Tidak Boleh Kosong");
            etFotoKegiatanPath.requestFocus();
            return;
        } else if (etLpjKegiatanPath.getText().toString().isEmpty()) {
            etLpjKegiatanPath.setError("File LPJ Tidak Boleh Kosong");
            etLpjKegiatanPath.requestFocus();
            return;
        }else {

            if (sFotoKegiatan != null && sFileLpj !=null) {
                Dialog progressDialog = new Dialog(getContext());
                progressDialog.setContentView(R.layout.dialog_progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                HashMap map = new HashMap();
                map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
                map.put("file_fk", RequestBody.create(MediaType.parse("text/plain"), "true"));
                map.put("file_lpj", RequestBody.create(MediaType.parse("text/plain"), "true"));

                RequestBody rbFotoKegiatan, rbLpjKegiatan;
                rbFotoKegiatan = RequestBody.create(MediaType.parse("image/*"), fotoKegiatan);
                rbLpjKegiatan = RequestBody.create(MediaType.parse("application/pdf"), fileLpj);

                MultipartBody.Part mpFotoKegiatan, mpFileLpj;
                mpFotoKegiatan = MultipartBody.Part.createFormData("foto_kegiatan", fotoKegiatan.getName(), rbFotoKegiatan);
                mpFileLpj = MultipartBody.Part.createFormData("lpj", fileLpj.getName(), rbLpjKegiatan);



                adminTjslInterface.updateAllFileLpjKegiatan(map, mpFotoKegiatan, mpFileLpj).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getCode() == 200) {

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
                            getActivity().onBackPressed();
                            progressDialog.dismiss();

                        }else {
                            progressDialog.dismiss();
                            Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
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
                                updateData();
                                dialogNoConnection.dismiss();
                            }
                        });

                        dialogNoConnection.show();

                    }
                });


            }
            else if (sFotoKegiatan != null && sFileLpj == null) {
                Dialog progressDialog = new Dialog(getContext());
                progressDialog.setContentView(R.layout.dialog_progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                HashMap map = new HashMap();
                map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
                map.put("file_fk", RequestBody.create(MediaType.parse("text/plain"), "true"));
                map.put("file_lpj", RequestBody.create(MediaType.parse("text/plain"), "false"));

                RequestBody rbFotoKegiatan;
                rbFotoKegiatan = RequestBody.create(MediaType.parse("image/*"), fotoKegiatan);
                MultipartBody.Part mpFotoKegiatan;
                mpFotoKegiatan = MultipartBody.Part.createFormData("foto_kegiatan", fotoKegiatan.getName(), rbFotoKegiatan);



                adminTjslInterface.updateFileFotoKegiatanLpj(map, mpFotoKegiatan).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getCode() == 200) {

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
                            getActivity().onBackPressed();
                            progressDialog.dismiss();

                        }else {
                            progressDialog.dismiss();
                            Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
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
                                updateData();
                                dialogNoConnection.dismiss();
                            }
                        });

                        dialogNoConnection.show();

                    }
                });
            }
            else if (sFileLpj != null && sFotoKegiatan == null) {
                Dialog progressDialog = new Dialog(getContext());
                progressDialog.setContentView(R.layout.dialog_progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                HashMap map = new HashMap();
                map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
                map.put("file_fk", RequestBody.create(MediaType.parse("text/plain"), "false"));
                map.put("file_lpj", RequestBody.create(MediaType.parse("text/plain"), "true"));

                RequestBody rbFileLpj;
                rbFileLpj = RequestBody.create(MediaType.parse("application/pdf"), fileLpj);
                MultipartBody.Part mpFileLpj;
                mpFileLpj = MultipartBody.Part.createFormData("lpj", fileLpj.getName(), rbFileLpj);



                adminTjslInterface.updateLpjKegiatanLpj(map,mpFileLpj).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getCode() == 200) {

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
                            getActivity().onBackPressed();
                            progressDialog.dismiss();

                        }else {
                            progressDialog.dismiss();
                            Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
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
                                updateData();
                                dialogNoConnection.dismiss();
                            }
                        });

                        dialogNoConnection.show();

                    }
                });
            }
            else {
                getActivity().onBackPressed();
            }








        }

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


    private void selectedFile (Integer code, String tipe) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(tipe);
        startActivityForResult(intent, code);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fotoKegiatan = new File(pdfPath);
                ivFotoKegiatan.setVisibility(View.VISIBLE);
                sFotoKegiatan = fotoKegiatan.getName();
                etFotoKegiatanPath.setText(fotoKegiatan.getName());

            }else if (requestCode == 2) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                ivLpj.setVisibility(View.VISIBLE);
                fileLpj = new File(pdfPath);
                sFileLpj = fileLpj.getName();
                etLpjKegiatanPath.setText(fileLpj.getName());

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









}