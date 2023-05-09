package com.example.adisti.PicFragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

public class PicDetailProposalKajianManfaatFragment extends Fragment {
    PicInterface picInterface;
    PengajuInterface pengajuInterface;
    List<JabatanPicModel>jabatanPicModelList;
    SpinnerJabatanPicAdapter spinnerJabatanPicAdapter;
    Button btnDownload, btnEntryKajianManfaat;
    SharedPreferences sharedPreferences;
    String userID, proposalId, fileProposal, namaLoket;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, et_loket, etTanggalProposal, etLatarBelakangProposal;
    Button btnKembali, btnRefresh;
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
       View view = inflater.inflate(R.layout.fragment_pic_detail_proposal_kajian_manfaat, container, false);
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

        btnEntryKajianManfaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PicInsertKajianManfaatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                fragment.setArguments(bundle);
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framePic, fragment)
                        .addToBackStack(null).commit();
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
        cvStatus = view.findViewById(R.id.cvStatus);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        et_loket = view.findViewById(R.id.et_loket);
        btnEntryKajianManfaat = view.findViewById(R.id.btnEntryKajianManfaat);
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

                            if (response.body().getKajianManfaat().equals("1")) {
                                btnEntryKajianManfaat.setVisibility(View.GONE);
                            }else{
                                btnEntryKajianManfaat.setVisibility(View.VISIBLE);
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