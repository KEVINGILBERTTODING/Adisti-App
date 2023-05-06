package com.example.adisti.PengajuFragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PicAdapter.SpinnerKodeLoketAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuAddProposalFragment extends Fragment {
    List<LoketModel> loketModelList;
    SpinnerKodeLoketAdapter spinnerKodeLoketAdapter;
    Spinner spLoket;
    PengajuInterface pengajuInterface;
    Button btnSubmit;
    Button btnRefresh, btnRefresh1;
    SharedPreferences sharedPreferences;
    String userID;
    PengajuModel pengajuModel;
    TextView tvTanggalProposal;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, etLatarBelakang;
    Button btnKembali, btnFilePicker, btnOke;
    String kodeLoket;
    private File file;
    ImageView ivPdf;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pengaju_add_proposal, container, false);
        init(view);
       getKodeLoket();
       getUserData();


       tvTanggalProposal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
               datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       String dateFormatted, monthFormatted;

                       if (dayOfMonth <10) {
                           dateFormatted = String.format("%02d", dayOfMonth);
                       }else {
                           dateFormatted = String.valueOf(dayOfMonth);
                       }


                       if (month <10) {
                           monthFormatted = String.format("%02d", dayOfMonth + 1);
                       }else {
                           monthFormatted = String.valueOf(month + 1);
                       }

                       tvTanggalProposal.setText(year+"-"+monthFormatted+"-"+dateFormatted);

                   }
               });
               datePickerDialog.show();
           }
       });
       btnKembali.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });
       spLoket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               kodeLoket = spinnerKodeLoketAdapter.getLoketId(position);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
       btnFilePicker.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
               intent.setType("application/pdf");
               startActivityForResult(intent, 1);
           }
       });
       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (etNoProposal.getText().toString().isEmpty()) {
                   Toasty.error(getContext(), "Field no proposal tidak boleh kosong", Toasty.LENGTH_SHORT).show();
               }else if (tvTanggalProposal.getText().toString().isEmpty()) {
                   Toasty.error(getContext(), "Field tanggal proposal tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etInstansi.getText().toString().isEmpty()) {
                     Toasty.error(getContext(), "Field instansi tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etBantuan.getText().toString().isEmpty()) {
                   Toasty.error(getContext(), "Field bantuan tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etNamaPengaju.getText().toString().isEmpty()) {
                     Toasty.error(getContext(), "Field nama pengaju tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etEmail.getText().toString().isEmpty()) {
                     Toasty.error(getContext(), "Field email tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etLatarBelakang.getText().toString().isEmpty()) {
                   Toasty.error(getContext(), "Field latar belakang tidak boleh kosong", Toasty.LENGTH_SHORT).show();
               }
               else if (etAlamat.getText().toString().isEmpty()) {
                     Toasty.error(getContext(), "Field alamat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etNoTelp.getText().toString().isEmpty()) {
                        Toasty.error(getContext(), "Field no telp tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etJabatan.getText().toString().isEmpty()) {
                        Toasty.error(getContext(), "Field jabatan tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etPdfPath.getText().toString().isEmpty()) {
                        Toasty.error(getContext(), "Field pdf tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else {
                   uploadProposal();

               }
           }
       });




       return view;
    }

    private void init(View view) {

        spLoket = view.findViewById(R.id.spLoket);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);
        etNoProposal = view.findViewById(R.id.et_noProposal);
        tvTanggalProposal = view.findViewById(R.id.tvTglProposal);
        etInstansi = view.findViewById(R.id.et_instansi);
        etBantuan = view.findViewById(R.id.et_bantuan);
        etNamaPengaju = view.findViewById(R.id.et_namaPengaju);
        etEmail = view.findViewById(R.id.et_emailPengaju);
        etAlamat = view.findViewById(R.id.et_alamat);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        btnKembali = view.findViewById(R.id.btnKembali);
        btnFilePicker = view.findViewById(R.id.btnPdfPicker);
        ivPdf = view.findViewById(R.id.ivPdf);
        etLatarBelakang = view.findViewById(R.id.et_latarBelakangProposal);

        etPdfPath.setEnabled(false);


    }

    private void getKodeLoket() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();

        pengajuInterface.getAllLoket().enqueue(new Callback<List<LoketModel>>() {
            @Override
            public void onResponse(Call<List<LoketModel>> call, Response<List<LoketModel>> response) {
                loketModelList = response.body();
                if (response.isSuccessful() && response.body().size() > 0) {
                    spinnerKodeLoketAdapter = new SpinnerKodeLoketAdapter(getContext(), loketModelList);
                    spLoket.setAdapter(spinnerKodeLoketAdapter);
                    dialog.dismiss();

                }else {
                    dialog.dismiss();
                    btnSubmit.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<List<LoketModel>> call, Throwable t) {
                dialog.dismiss();
                btnSubmit.setEnabled(false);
                dialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getKodeLoket();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();



            }
        });




    }

    private void getUserData() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();
        pengajuInterface.getPengajuById(userID).enqueue(new Callback<PengajuModel>() {
            @Override
            public void onResponse(Call<PengajuModel> call, Response<PengajuModel> response) {
                pengajuModel = response.body();
                if (response.isSuccessful()) {
                    etInstansi.setText(pengajuModel.getInstansi());
                    etNamaPengaju.setText(pengajuModel.getNamaLengkap());
                    etEmail.setText(pengajuModel.getEmail());
                    etAlamat.setText(pengajuModel.getAlamat());
                    etNoTelp.setText(pengajuModel.getNoTelp());
                    etJabatan.setText(pengajuModel.getJabatan());

                    etInstansi.setEnabled(false);
                    etNamaPengaju.setEnabled(false);
                    etEmail.setEnabled(false);
                    etAlamat.setEnabled(false);
                    etNoTelp.setEnabled(false);
                    etJabatan.setEnabled(false);


                    dialog.dismiss();

                }else {
                    btnSubmit.setEnabled(false);
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PengajuModel> call, Throwable t) {
                dialog.dismiss();
                btnSubmit.setEnabled(false);
                dialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                btnRefresh1 = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getUserData();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();


            }
        });

    }

    private void uploadProposal() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Menyimpan Data...");
        dialog.show();

        HashMap map = new HashMap();
        map.put("no_proposal", RequestBody.create(MediaType.parse("text/plain"), etNoProposal.getText().toString()));
        map.put("tgl_proposal", RequestBody.create(MediaType.parse("text/plain"), tvTanggalProposal.getText().toString()));
        map.put("asal_proposal", RequestBody.create(MediaType.parse("text/plain"), etInstansi.getText().toString()));
        map.put("bantuan_proposal", RequestBody.create(MediaType.parse("text/plain"), etBantuan.getText().toString()));
        map.put("nama_pihak", RequestBody.create(MediaType.parse("text/plain"), etNamaPengaju.getText().toString()));
        map.put("email_pengaju", RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString()));
        map.put("alamat_pihak", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));
        map.put("no_telp_pihak", RequestBody.create(MediaType.parse("text/plain"), etNoTelp.getText().toString()));
        map.put("jabatan_proposal", RequestBody.create(MediaType.parse("text/plain"), etJabatan.getText().toString()));
        map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userID));
        map.put("kode_loket", RequestBody.create(MediaType.parse("text/plain"), kodeLoket));
        map.put("latar_belakang_proposal", RequestBody.create(MediaType.parse("text/plain"), etLatarBelakang.getText().toString()));


        RequestBody requestBody = RequestBody.create(MediaType.parse("pdf/*"), file);
        MultipartBody.Part proposal = MultipartBody.Part.createFormData("file_proposal", file.getName(), requestBody);



        pengajuInterface.insertProposal(map, proposal).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();
                if (response.isSuccessful() && responseModel.getCode() == 200) {
                    dialog.dismiss();
                    Dialog dialogSuccess = new Dialog(getContext());
                    dialogSuccess.setContentView(R.layout.dialog_success_insert_proposal);
                    dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialogSuccess.setCanceledOnTouchOutside(false);
                    btnOke = dialogSuccess.findViewById(R.id.btnOke);
                    btnOke.setText("Oke");
                    btnOke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSuccess.dismiss();

                        }
                    });
                    dialogSuccess.show();
                    getActivity().onBackPressed();

                }else {
                    Toasty.error(getContext(), responseModel.getMessage(), Toasty.LENGTH_SHORT).show();
                    dialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                dialog.dismiss();
                btnSubmit.setEnabled(false);
                dialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection_close);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                btnOke = dialogNoConnection.findViewById(R.id.btnOke);
                btnOke.setText("Oke");
                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogNoConnection.dismiss();

                    }
                });

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
                file = new File(pdfPath);
                ivPdf.setVisibility(View.VISIBLE);
                etPdfPath.setText(file.getName());
            }else{
                ivPdf.setVisibility(View.GONE);
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