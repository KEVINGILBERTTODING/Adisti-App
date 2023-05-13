package com.example.adisti.PtsFragment;

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
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.Model.ResponseModel;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PtsInterface;

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

public class PtsInsertSurveyFragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etUnitKerja, etKetuaPanitia, etNamaPanitia,
            etAlamatPanitia, etNoTelpPanitia, etNamaBendahara, etNotelpBendahara, etAlamatBendahara, etSumberDana,
            etNominalDanaKegiatan, etSumberPrioritas, etNominalPrioritas, etKtpPath, etButabPath, etFotoSurveyPath;

    Button btnKtpPicker, btnButabPicker, btnFotoSurveyPicker, btnSubmit, btnBatal;
    ImageView ivKtp, ivButab, ivFotoSurvey;
    private File fileKtp, fileButab, fileFotoSurvey;
    SharedPreferences sharedPreferences;
    String proposalId, kodeLoket;
    PtsInterface ptsInterface;
    String noUrutProposal;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_insert_survey, container, false);
        init(view);

        btnKtpPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfFilePicker(1);
            }
        });
        btnButabPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfFilePicker(2);
            }
        });
        btnFotoSurveyPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfFilePicker(3);
            }
        });
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();

            }
        });




        return view;
    }

    private void init(View view) {
        etNamaPetugasSurvey = view.findViewById(R.id.etNamaPetugasSurvey);
        etJabatanPetugasSurvey = view.findViewById(R.id.etJabatanPetugasSurvey);
        etUnitKerja = view.findViewById(R.id.etUnitKerja);
        etKetuaPanitia = view.findViewById(R.id.etKetuaPanitia);
        etNamaPanitia = view.findViewById(R.id.etNamaPanitia);
        etAlamatPanitia = view.findViewById(R.id.etAlamatPanitia);
        etNoTelpPanitia = view.findViewById(R.id.etNoTelpPanitia);
        etNamaBendahara = view.findViewById(R.id.etNamaBendahara);
        etAlamatBendahara = view.findViewById(R.id.etAlamatBendahara);
        etSumberDana = view.findViewById(R.id.etSumberDana);
        etNominalDanaKegiatan = view.findViewById(R.id.etNominalDanaKegiatan);
        etSumberPrioritas = view.findViewById(R.id.etSumberPrioritas);
        etNominalPrioritas = view.findViewById(R.id.etNominalPrioritas);
        etKtpPath = view.findViewById(R.id.etKtpPath);
        etButabPath = view.findViewById(R.id.etButabPath);
        etFotoSurveyPath = view.findViewById(R.id.etFotoSurveyPath);
        btnKtpPicker = view.findViewById(R.id.btnKtpPicker);
        btnButabPicker = view.findViewById(R.id.btnButabPicker);
        btnFotoSurveyPicker = view.findViewById(R.id.btnFotoSurveyPicker);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnBatal = view.findViewById(R.id.btnBatal);
        ivKtp = view.findViewById(R.id.ivKtp);
        ivButab = view.findViewById(R.id.ivButab);
        ivFotoSurvey = view.findViewById(R.id.ivFotoSurvey);
        etNotelpBendahara = view.findViewById(R.id.etNoTelpBendahara);

        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        noUrutProposal = getArguments().getString("no_urut_proposal");
        ptsInterface = DataApi.getClient().create(PtsInterface.class);
    }

    private void insertData() {
        if (etNamaPetugasSurvey.getText().toString().isEmpty()) {
            etNamaPetugasSurvey.setError("Nama Petugas Survey Tidak Boleh Kosong");
            etNamaPetugasSurvey.requestFocus();
            return;
        }else if (etJabatanPetugasSurvey.getText().toString().isEmpty()) {
            etJabatanPetugasSurvey.setError("Jabatan Petugas Survey Tidak Boleh Kosong");
            etJabatanPetugasSurvey.requestFocus();
            return;
        }else if (etUnitKerja.getText().toString().isEmpty()) {
            etUnitKerja.setError("Unit Kerja Tidak Boleh Kosong");
            etUnitKerja.requestFocus();
            return;
        }else if (etKetuaPanitia.getText().toString().isEmpty()) {
            etKetuaPanitia.setError("Nama Ketua Panitia Tidak Boleh Kosong");
            etKetuaPanitia.requestFocus();
            return;
        }else if (etNamaPanitia.getText().toString().isEmpty()) {
            etNamaPanitia.setError("Nama Panitia Tidak Boleh Kosong");
            etNamaPanitia.requestFocus();
            return;
        }else if (etAlamatPanitia.getText().toString().isEmpty()) {
            etAlamatPanitia.setError("Alamat Panitia Tidak Boleh Kosong");
            etAlamatPanitia.requestFocus();
            return;
        }else if (etNoTelpPanitia.getText().toString().isEmpty()) {
            etNoTelpPanitia.setError("No Telp Panitia Tidak Boleh Kosong");
            etNoTelpPanitia.requestFocus();
            return;
        }else if (etNamaBendahara.getText().toString().isEmpty()) {
            etNamaBendahara.setError("Nama Bendahara Tidak Boleh Kosong");
            etNamaBendahara.requestFocus();
            return;
        }else if (etAlamatBendahara.getText().toString().isEmpty()) {
            etAlamatBendahara.setError("Alamat Bendahara Tidak Boleh Kosong");
            etAlamatBendahara.requestFocus();
            return;
        }else if (etSumberDana.getText().toString().isEmpty()) {
            etSumberDana.setError("Sumber Dana Tidak Boleh Kosong");
            etSumberDana.requestFocus();
            return;
        }else if (etNominalDanaKegiatan.getText().toString().isEmpty()) {
            etNominalDanaKegiatan.setError("Nominal Dana Kegiatan Tidak Boleh Kosong");
            etNominalDanaKegiatan.requestFocus();
            return;
        }else if (etSumberPrioritas.getText().toString().isEmpty()) {
            etSumberPrioritas.setError("Sumber Prioritas Tidak Boleh Kosong");
            etSumberPrioritas.requestFocus();
            return;
        }else if (etNominalPrioritas.getText().toString().isEmpty()) {
            etNominalPrioritas.setError("Nominal Prioritas Tidak Boleh Kosong");
            etNominalPrioritas.requestFocus();
            return;
        }else if (etKtpPath.getText().toString().isEmpty()) {
            etKtpPath.setError("KTP Tidak Boleh Kosong");
            etKtpPath.requestFocus();
            return;
        }else if (etButabPath.getText().toString().isEmpty()) {
            etButabPath.setError("Buku Tabungan Tidak Boleh Kosong");
            etButabPath.requestFocus();
            return;
        }else if (etNotelpBendahara.getText().toString().isEmpty()) {
            etNotelpBendahara.setError("No. Telepon Bendahara Tidak Boleh Kosong");
            etNotelpBendahara.requestFocus();
            return;
        }
        else if (etFotoSurveyPath.getText().toString().isEmpty()) {
            etFotoSurveyPath.setError("Foto Survey Tidak Boleh Kosong");
            etFotoSurveyPath.requestFocus();
            return;
        }else {
            Dialog progressDialog = new Dialog(getContext());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progress_bar);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.show();


            HashMap map = new HashMap();
            map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
            map.put("kode_loket", RequestBody.create(MediaType.parse("text/plain"), kodeLoket));
            map.put("no_urut_proposal", RequestBody.create(MediaType.parse("text/plain"), noUrutProposal));
            map.put("nama_petugas_survey", RequestBody.create(MediaType.parse("text/plain"), etNamaPetugasSurvey.getText().toString()));
            map.put("jabatan_petugas_survey", RequestBody.create(MediaType.parse("text/plain"), etJabatanPetugasSurvey.getText().toString()));
            map.put("unit_kerja", RequestBody.create(MediaType.parse("text/plain"), etUnitKerja.getText().toString()));
            map.put("ketua_panitia", RequestBody.create(MediaType.parse("text/plain"), etKetuaPanitia.getText().toString()));
            map.put("nama_panitia", RequestBody.create(MediaType.parse("text/plain"), etNamaPanitia.getText().toString()));
            map.put("alamat_panitia", RequestBody.create(MediaType.parse("text/plain"), etAlamatPanitia.getText().toString()));
            map.put("no_telp_panitia", RequestBody.create(MediaType.parse("text/plain"), etNoTelpPanitia.getText().toString()));
            map.put("nama_bendahara", RequestBody.create(MediaType.parse("text/plain"), etNamaBendahara.getText().toString()));
            map.put("alamat_bendahara", RequestBody.create(MediaType.parse("text/plain"), etAlamatBendahara.getText().toString()));
            map.put("no_telp_bendahara", RequestBody.create(MediaType.parse("text/plain"), etNotelpBendahara.getText().toString()));
            map.put("sdk_1", RequestBody.create(MediaType.parse("text/plain"), etSumberDana.getText().toString()));
            map.put("nominal_sdk_1", RequestBody.create(MediaType.parse("text/plain"), etNominalDanaKegiatan.getText().toString()));
            map.put("pk_1", RequestBody.create(MediaType.parse("text/plain"), etSumberPrioritas.getText().toString()));
            map.put("nominal_pk_1", RequestBody.create(MediaType.parse("text/plain"), etNominalPrioritas.getText().toString()));

            RequestBody requestBodyKtp, requestBodyButab, requestBodyFotoSurvey;
            requestBodyKtp = RequestBody.create(MediaType.parse("application/pdf"), fileKtp);
            requestBodyButab = RequestBody.create(MediaType.parse("application/pdf"), fileButab);
            requestBodyFotoSurvey = RequestBody.create(MediaType.parse("application/pdf"), fileFotoSurvey);

            MultipartBody.Part ktp, butab, fotoSurvey;
            ktp = MultipartBody.Part.createFormData("file_ktp", fileKtp.getName(), requestBodyKtp);
            butab = MultipartBody.Part.createFormData("file_butab", fileButab.getName(), requestBodyButab);
            fotoSurvey = MultipartBody.Part.createFormData("file_foto_survey", fileFotoSurvey.getName(), requestBodyFotoSurvey);





            ptsInterface.insertSurvey(map, ktp, butab, fotoSurvey).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    ResponseModel responseModel = response.body();
                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                        progressDialog.dismiss();
                        Dialog dialogSuccess = new Dialog(getContext());
                        dialogSuccess.setContentView(R.layout.dialog_success);
                        dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogSuccess.setCancelable(false);
                        dialogSuccess.setCanceledOnTouchOutside(false);
                        Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                        btnOke.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogSuccess.dismiss();
                            }
                        });
                        dialogSuccess.show();
                        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framePts, new PtsSurveyFragment()).commit();
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseModel>call, Throwable t) {
                    Dialog dialogNoConnection = new Dialog(getContext());
                    dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                    dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
    private void pdfFilePicker(Integer code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, code);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fileKtp = new File(pdfPath);
                ivKtp.setVisibility(View.VISIBLE);
                etKtpPath.setText(fileKtp.getName());
            }else if (requestCode == 2) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fileButab = new File(pdfPath);
                ivButab.setVisibility(View.VISIBLE);
                etButabPath.setText(fileButab.getName());
            }
            else if (requestCode == 3){
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fileFotoSurvey = new File(pdfPath);
                ivFotoSurvey.setVisibility(View.VISIBLE);
                etFotoSurveyPath.setText(fileFotoSurvey.getName());
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