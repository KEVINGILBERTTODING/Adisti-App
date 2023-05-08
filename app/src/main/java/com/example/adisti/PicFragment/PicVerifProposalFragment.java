package com.example.adisti.PicFragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.JabatanPicModel;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuFragment.PengajuEditProposalFragment;
import com.example.adisti.PicAdapter.SpinnerJabatanPicAdapter;
import com.example.adisti.PicAdapter.SpinnerLoketAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.example.adisti.Util.PicInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicVerifProposalFragment extends Fragment {
    PicInterface picInterface;
    PengajuInterface pengajuInterface;
    List<JabatanPicModel>jabatanPicModelList;
    SpinnerJabatanPicAdapter spinnerJabatanPicAdapter;
    Button btnDownload;
    SharedPreferences sharedPreferences;
    String userID, proposalId, fileProposal, namaLoket, loket, jabatanPic;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, et_loket, etTanggalProposal, etLatarBelakangProposal;
    Button btnKembali, btnRefresh, btnVerified, btnReject;
    TextView tvStatus;
    List<LoketModel>loketModelList;
    Spinner spLoket, spJabatanPic;
    CardView cvStatus;
    SpinnerLoketAdapter spinnerLoketAdapter;
    private String TAG = "DAD";






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pic_verif_proposal, container, false);
        init(view);

       btnKembali.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().onBackPressed();
           }
       });

       getProposalDetail();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_PROPOSAL+proposalId;
                String title = fileProposal;
                String description = "Downloading PDF file";
                String fileName = fileProposal;


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 1000);
                    } else {

                        FileDownload fileDownload = new FileDownload(getContext());
                        fileDownload.downloadFile(url, title, description, fileName);

                    }
                } else {

                    FileDownload fileDownload = new FileDownload(getContext());
                    fileDownload.downloadFile(url, title, description, fileName);
                }
            }
        });

        btnVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogVerfiedProposal = new Dialog(getContext());
                dialogVerfiedProposal.setContentView(R.layout.layout_verified_proposal);
                dialogVerfiedProposal.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogVerfiedProposal.setCancelable(false);
                dialogVerfiedProposal.setCanceledOnTouchOutside(false);
                spLoket = dialogVerfiedProposal.findViewById(R.id.spLoket);
                EditText etNamaLoket = dialogVerfiedProposal.findViewById(R.id.et_namaLoket);
                Button btnVerifikasi2 = dialogVerfiedProposal.findViewById(R.id.btnVerifikasi2);
                Button btnBatal = dialogVerfiedProposal.findViewById(R.id.btnBatal);
                etNamaLoket.setEnabled(false);
                spJabatanPic = dialogVerfiedProposal.findViewById(R.id.spJabatanPic);
                etNamaLoket.setText(namaLoket);
                getLoket();
                getJabatanPic();
                dialogVerfiedProposal.show();

                spJabatanPic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        jabatanPic = spinnerJabatanPicAdapter.getNamaJabatanPic(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spLoket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        loket = spinnerLoketAdapter.getNamaLoket(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogVerfiedProposal.dismiss();
                    }
                });
                btnVerifikasi2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (loket == null) {
                            Toasty.error(getContext(), "Loket tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                        } else if (namaLoket == null) {
                            Toasty.error(getContext(), "Nama loket tidak boleh kosong", Toasty.LENGTH_SHORT).show();

                        }else if (jabatanPic == null) {
                            Toasty.error(getContext(), "Jabatan PIC boleh kosong", Toasty.LENGTH_SHORT).show();

                        }else {

                            Dialog progressDialog = new Dialog(getContext());
                            progressDialog.setCancelable(false);
                            progressDialog.setContentView(R.layout.dialog_progress_bar);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            progressDialog.show();

                            picInterface.verifiedProposal(
                                    proposalId, loket, namaLoket,jabatanPic
                            ).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getCode() == 200) {
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
                                        dialogVerfiedProposal.dismiss();
                                        getProposalDetail();
                                        progressDialog.dismiss();

                                    }else{
                                        Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {

                                    Dialog dialogNoConnection  = new Dialog(getContext());
                                    dialogNoConnection.setContentView(R.layout.dialog_no_connection_close);
                                    dialogNoConnection.setCanceledOnTouchOutside(false);
                                    dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    Button btnOke = dialogNoConnection.findViewById(R.id.btnOke);
                                    btnOke.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogNoConnection.dismiss();
                                        }
                                    });
                                    dialogNoConnection.show();

                                }
                            });

                        }


                    }
                });
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            Dialog progressDialog = new Dialog(getContext());
                            progressDialog.setCancelable(false);
                            progressDialog.setContentView(R.layout.dialog_progress_bar);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            progressDialog.show();

                            picInterface.rejectProposal(
                                    proposalId
                            ).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    ResponseModel responseModel = response.body();
                                    if (response.isSuccessful() && responseModel.getCode() == 200) {
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

                                        getProposalDetail();
                                        progressDialog.dismiss();

                                    }else{
                                        Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {

                                    Dialog dialogNoConnection  = new Dialog(getContext());
                                    dialogNoConnection.setContentView(R.layout.dialog_no_connection_close);
                                    dialogNoConnection.setCanceledOnTouchOutside(false);
                                    dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    Button btnOke = dialogNoConnection.findViewById(R.id.btnOke);
                                    btnOke.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogNoConnection.dismiss();
                                        }
                                    });
                                    dialogNoConnection.show();

                                }
                            });

            }
        });



       return view;
    }

    private void init(View view) {

        btnDownload = view.findViewById(R.id.btnDownload);
        picInterface = DataApi.getClient().create(PicInterface.class);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);
        proposalId = getArguments().getString("proposal_id");
        namaLoket = sharedPreferences.getString("nama_loket", null);
        etNoProposal = view.findViewById(R.id.et_noProposal);
        etInstansi = view.findViewById(R.id.et_instansi);
        etTanggalProposal = view.findViewById(R.id.et_tglProposal);
        etBantuan = view.findViewById(R.id.et_bantuan);
        etNamaPengaju = view.findViewById(R.id.et_namaPengaju);
        etEmail = view.findViewById(R.id.et_emailPengaju);
        etLatarBelakangProposal = view.findViewById(R.id.et_latarBelakangProposal);
        etAlamat = view.findViewById(R.id.et_alamat);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnReject = view.findViewById(R.id.btnReject);
        cvStatus = view.findViewById(R.id.cvStatus);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        et_loket = view.findViewById(R.id.et_loket);
        btnVerified = view.findViewById(R.id.btnVerif);
        btnKembali = view.findViewById(R.id.btnBack);
        etPdfPath.setEnabled(false);
        etTanggalProposal.setEnabled(false);
        etAlamat.setEnabled(false);
        etBantuan.setEnabled(false);
        etEmail.setEnabled(false);
        etInstansi.setEnabled(false);
        etJabatan.setEnabled(false);
        etNamaPengaju.setEnabled(false);
        etNoProposal.setEnabled(false);
        etNoTelp.setEnabled(false);
        et_loket.setEnabled(false);
        etLatarBelakangProposal.setEnabled(false);



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
                            etTanggalProposal.setText(response.body().getTglProposal());
                            etInstansi.setText(response.body().getAsalProposal());
                            etBantuan.setText(response.body().getBantuanDiajukan());
                            etNamaPengaju.setText(response.body().getNamaPihak());
                            etEmail.setText(response.body().getEmailPengaju());
                            etAlamat.setText(response.body().getAlamatPihak());
                            etNoTelp.setText(response.body().getNoTelpPihak());
                            etJabatan.setText(response.body().getJabatanPengaju());
                            etPdfPath.setText(response.body().getFileProposal());
                            et_loket.setText(response.body().getNamaLoketPengajuan());
                            fileProposal = response.body().getFileProposal();
                            etLatarBelakangProposal.setText(response.body().getLatarBelakangPengajuan());

                            if (response.body().getStatus().equals("Diterima")){
                                tvStatus.setText("Diterima");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.green));

                                Dialog dialogSuccess = new Dialog(getContext());
                                dialogSuccess.setContentView(R.layout.dialog_proposal_diterima);
                                dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                dialogSuccess.setCanceledOnTouchOutside(false);
                                Button btnOke = dialogSuccess.findViewById(R.id.btnOke);
                                btnOke.setText("Oke");
                                btnOke.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogSuccess.dismiss();

                                    }
                                });
                                dialogSuccess.show();
                            }else if (response.body().getStatus().equals("Ditolak")){
                                tvStatus.setText("Ditolak");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                            }else {
                                tvStatus.setText("Menunggu");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.yelllow));
                            }

                            if (response.body().getVerified().equals("1")) {
                                if (response.body().getKajianManfaat().equals("0")) {
                                    btnReject.setVisibility(View.VISIBLE);
                                    btnVerified.setVisibility(View.GONE);

                                }else {
                                    btnReject.setVisibility(View.GONE);
                                    btnVerified.setVisibility(View.GONE);


                                }

                            }else if (response.body().getVerified().equals("0")) {
                                btnVerified.setVisibility(View.VISIBLE);
                                btnReject.setVisibility(View.GONE);

                            }
                            else {
                                btnVerified.setVisibility(View.VISIBLE);
                                btnReject.setVisibility(View.VISIBLE);

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

    private void getLoket() {

        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        picInterface.getLoket().enqueue(new Callback<List<LoketModel>>() {
            @Override
            public void onResponse(Call<List<LoketModel>> call, Response<List<LoketModel>> response) {
                loketModelList = response.body();
                if (response.isSuccessful() && loketModelList.size() > 0) {
                    spinnerLoketAdapter = new SpinnerLoketAdapter(getContext(), loketModelList);
                    spLoket.setAdapter(spinnerLoketAdapter);
                    progressDialog.dismiss();


                }else {
                    progressDialog.dismiss();
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<List<LoketModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                dialogNoConnection.setCancelable(false);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLoket();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();

            }
        });
    }
    private void getJabatanPic() {

        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        picInterface.getJabatanPic().enqueue(new Callback<List<JabatanPicModel>>() {
            @Override
            public void onResponse(Call<List<JabatanPicModel>> call, Response<List<JabatanPicModel>> response) {
                jabatanPicModelList = response.body();
                if (response.isSuccessful() && loketModelList.size() > 0) {
                    spinnerJabatanPicAdapter = new SpinnerJabatanPicAdapter(getContext(), jabatanPicModelList);
                    spJabatanPic.setAdapter(spinnerJabatanPicAdapter);
                    progressDialog.dismiss();


                }else {
                    progressDialog.dismiss();
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<List<JabatanPicModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                dialogNoConnection.setCancelable(false);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getJabatanPic();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();

            }
        });
    }



}