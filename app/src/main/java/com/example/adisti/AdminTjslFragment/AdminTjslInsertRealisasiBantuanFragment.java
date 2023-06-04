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
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.DcmFragment.DcmPendapatTanggapanFragment;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.UserModel;
import com.example.adisti.R;
import com.example.adisti.Util.AdminTjslInterface;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.DcmInterface;

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

public class AdminTjslInsertRealisasiBantuanFragment extends Fragment {


    Button  btnSubmit, btnBatal, btnFotoKegiatanPicker, btnKuitansiPicker, btnBastPicker,
            btnSptPicker, btnBuktiPembayaranPicker;
    TextView tvTglKegiatan;

    SharedPreferences sharedPreferences;
    String proposalId, userId;
    Spinner spBantuan;
    AdminTjslInterface adminTjslInterface;
    LinearLayout layoutBarangBerupa;
    EditText etTempatKegiatan, etNominalBantua, etLinkBerita, etFotoKegiatanPath, etKuitansiPath,
            etBastPath, etSptPath, etBuktiPembayaranPath, etBarangBerupa;

    String [] jenisBantuan = {"Uang Tunai", "Barang"};
    String jb;
    private File fotoKegiatan, kuitansi, bast, spt, buktiPembayaran;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_insert_realisasi_bantuan, container, false);
        init(view);

        ArrayAdapter jenisBantuanAdapter =  new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, jenisBantuan);
        jenisBantuanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBantuan.setAdapter(jenisBantuanAdapter);

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

        spBantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jb = jenisBantuan[position];

                if (jb.equals("Barang")) {
                    layoutBarangBerupa.setVisibility(View.VISIBLE);

                }else {
                    layoutBarangBerupa.setVisibility(View.GONE);
                    etBarangBerupa.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvTglKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dateFormatted, monthFormatted;

                        if (month < 10) {
                            monthFormatted = String.format("%02d", month + 1);
                        }else {
                            monthFormatted = String.valueOf(month + 1);
                        }

                        if (dayOfMonth < 10) {
                            dateFormatted = String.format("%02d", dayOfMonth);
                        }else {
                            dateFormatted = String.valueOf(dayOfMonth);
                        }

                        tvTglKegiatan.setText(year+"-" + monthFormatted +"-" + dateFormatted);
                    }
                });

                datePickerDialog.show();
            }
        });

        btnFotoKegiatanPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFile(1, "image/*");
            }
        });
        btnKuitansiPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFile(2, "application/pdf");
            }
        });
        btnBastPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFile(3, "application/pdf");
            }
        });
        btnSptPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFile(4, "application/pdf");
            }
        });
        btnBuktiPembayaranPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFile(5, "application/pdf");
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
        tvTglKegiatan = view.findViewById(R.id.tvTglKegiatan);
        etBarangBerupa = view.findViewById(R.id.etBarangBerupa);
        spBantuan = view.findViewById(R.id.spBantuan);
        layoutBarangBerupa = view.findViewById(R.id.layoutBarangBerupa);
        etTempatKegiatan = view.findViewById(R.id.etTempatKegiatan);
        etNominalBantua = view.findViewById(R.id.etNominalBantuan);
        etLinkBerita = view.findViewById(R.id.etLinkBerita);
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





    }

    private void insertData() {

        if (etTempatKegiatan.getText().toString().isEmpty()) {
            etTempatKegiatan.setError("Tempat Kegiatan Tidak Boleh Kosong");
            etTempatKegiatan.requestFocus();
            return;
        } else if (etNominalBantua.getText().toString().isEmpty()) {
            etNominalBantua.setError("Nominal Bantuan Tidak Boleh Kosong");
            etNominalBantua.requestFocus();
            return;
        } else if (etLinkBerita.getText().toString().isEmpty()) {
            etLinkBerita.setError("Link Berita Tidak Boleh Kosong");
            etLinkBerita.requestFocus();
            return;
        } else if (etFotoKegiatanPath.getText().toString().isEmpty()) {
            etFotoKegiatanPath.setError("Foto Kegiatan Tidak Boleh Kosong");
            etFotoKegiatanPath.requestFocus();
            return;
        }else if (etKuitansiPath.getText().toString().isEmpty()) {
            etKuitansiPath.setError("File Kuitansi Tidak Boleh Kosong");
            etKuitansiPath.requestFocus();
            return;
        }else if (etBastPath.getText().toString().isEmpty()) {
            etBastPath.setError("File Bast Kegiatan Tidak Boleh Kosong");
            etBastPath.requestFocus();
            return;
        }else if (etSptPath.getText().toString().isEmpty()) {
            etSptPath.setError("File Spt Tidak Boleh Kosong");
            etSptPath.requestFocus();
            return;
        }else if (etBuktiPembayaranPath.getText().toString().isEmpty()) {
            etBuktiPembayaranPath.setError("Foto Kegiatan Tidak Boleh Kosong");
            etBuktiPembayaranPath.requestFocus();
            return;
        } else {

            Dialog progressDialog = new Dialog(getContext());
            progressDialog.setContentView(R.layout.dialog_progress_bar);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            HashMap map = new HashMap();
            map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
            map.put("tanggal_kegiatan", RequestBody.create(MediaType.parse("text/plain"), tvTglKegiatan.getText().toString()));
            map.put("tempat_kegiatan", RequestBody.create(MediaType.parse("text/plain"), etTempatKegiatan.getText().toString()));
            map.put("jenis_bantuan", RequestBody.create(MediaType.parse("text/plain"), jb));
            map.put("nominal_bantuan", RequestBody.create(MediaType.parse("text/plain"), etNominalBantua.getText().toString()));
            map.put("barang_berupa", RequestBody.create(MediaType.parse("text/plain"), etBarangBerupa.getText().toString()));
            map.put("link_berita", RequestBody.create(MediaType.parse("text/plain"), etLinkBerita.getText().toString()));

            RequestBody rbFotoKegiatan = RequestBody.create(MediaType.parse("image/*"), fotoKegiatan);
            RequestBody rbKuitansi = RequestBody.create(MediaType.parse("application/pdf"), kuitansi);
            RequestBody rbBast = RequestBody.create(MediaType.parse("application/pdf"), bast);
            RequestBody rbSpt = RequestBody.create(MediaType.parse("application/pdf"), bast);
            RequestBody rbBuktiBayar = RequestBody.create(MediaType.parse("application/pdf"), buktiPembayaran);


            // create mulitpart form

            MultipartBody.Part mbFotoKegiatan = MultipartBody.Part.createFormData("foto_kegiatan", fotoKegiatan.getName(), rbFotoKegiatan);
            MultipartBody.Part mbKuitansi = MultipartBody.Part.createFormData("kuitansi", kuitansi.getName(), rbKuitansi);
            MultipartBody.Part mbBast = MultipartBody.Part.createFormData("bast", bast.getName(), rbBast);
            MultipartBody.Part mbSpt = MultipartBody.Part.createFormData("spt", spt.getName(), rbSpt);
            MultipartBody.Part mbBuktiPembayaran = MultipartBody.Part.createFormData("buktipembayaran", buktiPembayaran.getName(), rbBuktiBayar);



            adminTjslInterface.insertRealisasiBantuan(map, mbFotoKegiatan, mbKuitansi, mbBast, mbSpt, mbBuktiPembayaran).enqueue(new Callback<ResponseModel>() {
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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, new AdminTjslInsertRealisasiBantuanFragment())
                                        .commit();
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
                            insertData();
                            dialogNoConnection.dismiss();
                        }
                    });

                    dialogNoConnection.show();

                }
            });







        }

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
                etFotoKegiatanPath.setText(fotoKegiatan.getName());

            }else if (requestCode == 2) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                kuitansi = new File(pdfPath);
                etKuitansiPath.setText(kuitansi.getName());

            } else if (requestCode == 3) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                bast = new File(pdfPath);
                etBastPath.setText(bast.getName());

            } else if (requestCode == 4) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                spt = new File(pdfPath);
                etSptPath.setText(spt.getName());

            } else if (requestCode == 5) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                buktiPembayaran = new File(pdfPath);
                etBuktiPembayaranPath.setText(buktiPembayaran.getName());
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
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
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