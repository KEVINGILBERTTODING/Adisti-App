package com.example.adisti.PtsFragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.SurveyModel;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PtsInterface;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PtsDetailSurvey2Fragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etUnitKerja, etKetuaPanitia, etNamaPanitia,
            etAlamatPanitia, etNoTelpPanitia, etNamaBendahara, etNotelpBendahara, etAlamatBendahara, etSumberDana,
            etNominalDanaKegiatan, etSumberPrioritas, etSumberPrioritas2, etSumberPrioritas3, etNominalPrioritas, etNominalPrioritas2, etNominalPrioritas3, etKtpPath, etButabPath, etFotoSurveyPath,
            etSumberDana2, etSumberDana3, etNominalDanaKegiatan2, etNominalDanaKegiatan3;

    Button btnDownloadKtp, btnDownloadButab, btnDownloadFotoSurvey, btnEntryHasilSurvey, btnKembali;
    ImageView ivKtp, ivButab, ivFotoSurvey;
    SharedPreferences sharedPreferences;
    String proposalId, kodeLoket, noUrutProposal;
    PtsInterface ptsInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_detail_survey2, container, false);
        init(view);

        displaySurvey();
        btnDownloadKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = DataApi.URL_DOWNLOAD_FILE_SURVEY + proposalId +"/" + "file_ktp";
                String title = "FileKtp";
                String description = "Downloading PDF file";
                String fileName = "File ktp";


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
        btnDownloadButab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = DataApi.URL_DOWNLOAD_FILE_SURVEY + proposalId +"/" + "file_butab";
                String title = "FileButab";
                String description = "Downloading PDF file";
                String fileName = "File Butab";


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
        btnDownloadFotoSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = DataApi.URL_DOWNLOAD_FILE_SURVEY + proposalId +"/" + "file_foto_survey";
                String title = "FileFotoSurvey";
                String description = "Downloading PDF file";
                String fileName = "File Foto Survey";


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
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnEntryHasilSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PtsInsertHasilSurveyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                bundle.putString("no_urut_proposal", noUrutProposal);
                fragment.setArguments(bundle);
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framePts, fragment).addToBackStack(null).commit();
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
        etSumberPrioritas2 = view.findViewById(R.id.etSumberPrioritas2);
        etNominalPrioritas2 = view.findViewById(R.id.etNominalPrioritas2);
        etSumberPrioritas3 = view.findViewById(R.id.etSumberPrioritas3);
        etNominalPrioritas3 = view.findViewById(R.id.etNominalPrioritas3);
        etKtpPath = view.findViewById(R.id.etKtpPath);
        etButabPath = view.findViewById(R.id.etButabPath);
        etFotoSurveyPath = view.findViewById(R.id.etFotoSurveyPath);
        btnDownloadKtp = view.findViewById(R.id.btnDownloadKtp);
        btnDownloadButab = view.findViewById(R.id.btnDownloadButab);
        btnDownloadFotoSurvey = view.findViewById(R.id.btnDownloadFotoSurvey);
        etNominalDanaKegiatan2 = view.findViewById(R.id.etNominalDanaKegiatan2);
        etNominalDanaKegiatan3 = view.findViewById(R.id.etNominalDanaKegiatan3);
        btnEntryHasilSurvey = view.findViewById(R.id.btnEntryHasilSurvey);
        etSumberDana2 = view.findViewById(R.id.etSumberDana2);
        etSumberDana3 = view.findViewById(R.id.etSumberDana3);
        btnKembali = view.findViewById(R.id.btnKembali);
        ivKtp = view.findViewById(R.id.ivKtp);
        ivButab = view.findViewById(R.id.ivButab);
        ivFotoSurvey = view.findViewById(R.id.ivFotoSurvey);
        etNotelpBendahara = view.findViewById(R.id.etNoTelpBendahara);

        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        ptsInterface = DataApi.getClient().create(PtsInterface.class);
    }


    private void displaySurvey() {

        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        ptsInterface.getSurveyById(proposalId).enqueue(new Callback<SurveyModel>() {
            @Override
            public void onResponse(Call<SurveyModel> call, Response<SurveyModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                    decimalFormat.setGroupingUsed(true);
                    decimalFormat.setGroupingSize(3);

                    progressDialog.dismiss();
                    btnEntryHasilSurvey.setEnabled(true);
                    etNamaPetugasSurvey.setText(response.body().getNamaPetugasSurvey());
                    etJabatanPetugasSurvey.setText(response.body().getJabatanPetugasSurvey());
                    etUnitKerja.setText(response.body().getUnitKerja());
                    etKetuaPanitia.setText(response.body().getKetuaPanitia());
                    etNamaPanitia.setText(response.body().getNamaPanitia());
                    etAlamatPanitia.setText(response.body().getAlamatPanitia());
                    etNoTelpPanitia.setText(response.body().getNoTelpPanitia());
                    etNamaBendahara.setText(response.body().getNamaBendahara());
                    etAlamatBendahara.setText(response.body().getAlamatBendahara());
                    etNotelpBendahara.setText(response.body().getNoTelpBendahara());
                    etSumberDana.setText(response.body().getSdk1());
                    etNominalDanaKegiatan.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalSdk1())));
                    etSumberDana2.setText(response.body().getSdk2());
                    etNominalDanaKegiatan2.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalSdk2())));
                    etSumberDana3.setText(response.body().getSdk3());
                    etNominalDanaKegiatan3.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalSdk3())));
                    etSumberPrioritas.setText(response.body().getPk1());
                    etNominalPrioritas.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalPk1())));
                    noUrutProposal = response.body().getNoUrutProposal();
                    etSumberPrioritas2.setText(response.body().getPk2());
                    etNominalPrioritas2.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalPk2())));
                    etSumberPrioritas3.setText(response.body().getPk3());
                    etNominalPrioritas3.setText("Rp. " + decimalFormat.format(Integer.parseInt(response.body().getNominalPk3())));
                    etKtpPath.setText(response.body().getFileKtp());
                    etButabPath.setText(response.body().getFileButab());
                    etFotoSurveyPath.setText(response.body().getFileFotoSurvey());

                    if (response.body().getFileKtp() != null) {
                        ivKtp.setVisibility(View.VISIBLE);
                    }else {
                        ivKtp.setVisibility(View.GONE);
                    }

                    if (response.body().getFileButab() != null) {
                        ivButab.setVisibility(View.VISIBLE);
                    }else {
                        ivButab.setVisibility(View.GONE);
                    }

                    if (response.body().getFormatFotoSurvey() != null) {
                        ivFotoSurvey.setVisibility(View.VISIBLE);
                    }else {
                        ivFotoSurvey.setVisibility(View.GONE);
                    }



                }else {
                    progressDialog.dismiss();
                    Toasty.error(getContext(), "Terjadi Kesalahan", Toasty.LENGTH_SHORT).show();
                    btnEntryHasilSurvey.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<SurveyModel> call, Throwable t) {
                progressDialog.dismiss();
                btnEntryHasilSurvey.setEnabled(false);
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displaySurvey();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();


            }
        });


    }


}