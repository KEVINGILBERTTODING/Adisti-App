package com.example.adisti.PengajuFragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.ProposalModel;
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

public class PengajuEditProposalFragment extends Fragment {
    PengajuInterface pengajuInterface;
    List<LoketModel> loketModelList;

    SharedPreferences sharedPreferences;
    private Button btnRefresh, btnRefresh1;
    String userID, proposalId, fileProposal, kodeLoket, realPdfPath;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, etLatarBelakang, etNamaLoket;
    Button btnKembali, btnUbah, btnOke, btnPdfPicker;
    TextView tvStatus, tvTanggalProposal;
    SpinnerKodeLoketAdapter spinnerKodeLoketAdapter;
    private File file;

    CardView cvStatus;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pengaju_edit_proposal, container, false);
       init(view);
       getProposalDetail();



       btnKembali.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });
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
                            monthFormatted = String.format("%02d", month + 1);
                        }else {
                            monthFormatted = String.valueOf(month + 1);
                        }

                        tvTanggalProposal.setText(year+"/"+monthFormatted+"/"+dateFormatted);

                    }
                });
                datePickerDialog.show();
            }
        });

       btnPdfPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);
            }
        });
       btnUbah.setOnClickListener(new View.OnClickListener() {
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
                }else if (etLatarBelakang.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field latar belakang tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }
                else if (etEmail.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field email tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etAlamat.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field alamat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etNoTelp.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field no telp tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else if (etJabatan.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field jabatan tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else {
                    updateProposal();

                }
            }
        });



        return view;
    }

    private void init(View view) {

        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);
        proposalId = getArguments().getString("proposal_id");
        etNoProposal = view.findViewById(R.id.et_noProposal);
        etInstansi = view.findViewById(R.id.et_instansi);
        etBantuan = view.findViewById(R.id.et_bantuan);
        etNamaPengaju = view.findViewById(R.id.et_namaPengaju);
        etEmail = view.findViewById(R.id.et_emailPengaju);
        etAlamat = view.findViewById(R.id.et_alamat);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnPdfPicker = view.findViewById(R.id.btnPdfPicker);
        tvTanggalProposal = view.findViewById(R.id.tvTglProposal);
        btnUbah = view.findViewById(R.id.btnUbah);
        etNamaLoket =view.findViewById(R.id.etNamaLoket);
        cvStatus = view.findViewById(R.id.cvStatus);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        etLatarBelakang = view.findViewById(R.id.et_latarBelakangProposal);
        btnKembali = view.findViewById(R.id.btnKembali);
        etPdfPath.setEnabled(false);





    }

    private void getProposalDetail() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();



        pengajuInterface.getProposalById(proposalId)
                .enqueue(new Callback<ProposalModel>() {
                    @Override
                    public void onResponse(Call<ProposalModel> call, Response<ProposalModel> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            etNoProposal.setText(response.body().getNoProposal());
                            tvTanggalProposal.setText(response.body().getTglProposal());
                            etInstansi.setText(response.body().getAsalProposal());
                            etBantuan.setText(response.body().getBantuanDiajukan());
                            etNamaPengaju.setText(response.body().getNamaPihak());
                            etEmail.setText(response.body().getEmailPengaju());
                            etAlamat.setText(response.body().getAlamatPihak());
                            etNoTelp.setText(response.body().getNoTelpPihak());
                            kodeLoket = response.body().getKodeLoket();
                            etJabatan.setText(response.body().getJabatanPengaju());
                            etPdfPath.setText(response.body().getFileProposal());
                            fileProposal = response.body().getFileProposal();
                            etLatarBelakang.setText(response.body().getLatarBelakangPengajuan());
                            getKodeLoket(response.body().getKodeLoket());

                            if (response.body().getStatus().equals("Diterima")){
                                tvStatus.setText("Diterima");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.green));
                            }else if (response.body().getStatus().equals("Ditolak")){
                                tvStatus.setText("Ditolak");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                            }else {

                                if (response.body().getVerified().equals("0")) {
                                    tvStatus.setText("Tidak lolos verifikasi");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                                }else {
                                    tvStatus.setText("Menunggu");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.yelllow));
                                }
                            }

                            if (response.body().getVerified().equals("1")) {
                                btnUbah.setVisibility(View.GONE);
                            }else {
                                btnUbah.setVisibility(View.VISIBLE);
                            }





                            dialog.dismiss();

                        }else {
                           Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<ProposalModel> call, Throwable t) {

                        dialog.dismiss();
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getProposalDetail();
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.show();



                    }
                });

    }


    private void updateProposal() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Menyimpan Data...");
        dialog.show();



        if (realPdfPath != null) {
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
            map.put("kode_loket", RequestBody.create(MediaType.parse("text/plain"), kodeLoket));
            map.put("pdf_path", RequestBody.create(MediaType.parse("text/plain"), realPdfPath));
            map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
            map.put("latar_belakang_proposal", RequestBody.create(MediaType.parse("text/plain"), etLatarBelakang.getText().toString()));
            RequestBody requestBody = RequestBody.create(MediaType.parse("pdf/*"), file);
            MultipartBody.Part proposal = MultipartBody.Part.createFormData("file_proposal", file.getName(), requestBody);



            pengajuInterface.updateProposal(map, proposal).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    ResponseModel responseModel = response.body();
                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                        dialog.dismiss();
                        Dialog dialogSuccess = new Dialog(getContext());
                        dialogSuccess.setContentView(R.layout.dialog_success);
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
                    btnUbah.setEnabled(false);
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
        else {

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
            map.put("kode_loket", RequestBody.create(MediaType.parse("text/plain"), kodeLoket));
            map.put("latar_belakang_proposal", RequestBody.create(MediaType.parse("text/plain"), etLatarBelakang.getText().toString()));
            map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
            map.put("pdf_path", RequestBody.create(MediaType.parse("text/plain"), ""));


            pengajuInterface.updateProposalOnlyTextData(map).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    ResponseModel responseModel = response.body();
                    if (response.isSuccessful() && responseModel.getCode() == 200) {
                        dialog.dismiss();
                        Dialog dialogSuccess = new Dialog(getContext());
                        dialogSuccess.setContentView(R.layout.dialog_success);
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
                    btnUbah.setEnabled(false);
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

    }
    private void getKodeLoket(String kl) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();
        pengajuInterface.getLoketById(kl).enqueue(new Callback<LoketModel>() {
            @Override
            public void onResponse(Call<LoketModel> call, Response<LoketModel> response) {

                if (response.isSuccessful() && response.body() != null) {
                    etNamaLoket.setText(response.body().getNamaLoket());
                    dialog.dismiss();

                }else {
                    btnUbah.setEnabled(false);
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<LoketModel> call, Throwable t) {
                dialog.dismiss();
                btnUbah.setEnabled(false);
                dialog.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                btnRefresh1 = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getKodeLoket(kl);
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();


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
                etPdfPath.setText(file.getName());
                realPdfPath = file.getName();
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