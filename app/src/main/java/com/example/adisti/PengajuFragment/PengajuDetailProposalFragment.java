package com.example.adisti.PengajuFragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.adisti.FileDownload;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PengajuAdapter.PengajuProposalAdapter;
import com.example.adisti.PicAdapter.SpinnerKodeLoketAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.shuhart.stepview.StepView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuDetailProposalFragment extends Fragment {
    PengajuInterface pengajuInterface;
    Button btnDownload;
    SharedPreferences sharedPreferences;
    String userID, proposalId, fileProposal;
    private ViewPager viewPager;
    EditText etNoProposal,  etInstansi, etBantuan, etNamaPengaju,
    etEmail, etAlamat, etNoTelp, etJabatan, etPdfPath, et_loket, etTanggalProposal, etLatarBelakangProposal;
    Button btnKembali, btnRefresh, btnUbah, btnDownloadSurat;
    TextView tvStatus;
    CardView cvStatus;
    private StepView stepView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_pengaju_detail_proposal, container, false);
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
                downloadProposal();







            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PengajuEditProposalFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                fragment.setArguments(bundle);
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framePengaju, fragment).addToBackStack(null)
                        .commit();
            }
        });

        btnDownloadSurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_SURAT_KACAB + proposalId;

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);



            }
        });



       return view;
    }


    private void downloadProposal() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Download...");
        dialog.show();


        if (proposalId != null) {
            pengajuInterface.getProposalPath(proposalId).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    String url = DataApi.PATH_PROPOSAL+response.body().getMessage();

                    pengajuInterface.downloadFileProposal(url).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            dialog.dismiss();
                            Log.d("KEVIN", "onResponse: " + url);
                            if (response.isSuccessful()) {

                                if (response.body()  != null) {
                                    boolean download = writeResponseBodyToDisk(response.body());
                                    Toasty.success(getContext(), "Berhasil mengunduh proposal", Toasty.LENGTH_SHORT).show();

                                }else {
                                    Toasty.error(getContext(), "Response body null", Toasty.LENGTH_SHORT).show();

                                }




                            }else {
                                Toasty.error(getContext(), "Gagal mengunduh proposal", Toasty.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toasty.error(getContext(), "Gagal mengunduh proposal", Toasty.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }
                    });



                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    dialog.dismiss();

                    Toasty.error(getContext(), "Gagal mengunduh proposal", Toasty.LENGTH_SHORT).show();

                }
            });
        }else {
            dialog.dismiss();
            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void init(View view) {

        btnDownload = view.findViewById(R.id.btnDownload);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user_id", null);
        proposalId = getArguments().getString("proposal_id");
        etNoProposal = view.findViewById(R.id.et_noProposal);
        etInstansi = view.findViewById(R.id.et_instansi);
        etTanggalProposal = view.findViewById(R.id.et_tglProposal);
        etBantuan = view.findViewById(R.id.et_bantuan);
        btnDownloadSurat = view.findViewById(R.id.btnDownloadSurat);
        etNamaPengaju = view.findViewById(R.id.et_namaPengaju);
        etEmail = view.findViewById(R.id.et_emailPengaju);
        etLatarBelakangProposal = view.findViewById(R.id.et_latarBelakangProposal);
        etAlamat = view.findViewById(R.id.et_alamat);
        tvStatus = view.findViewById(R.id.tvStatus);
        btnUbah = view.findViewById(R.id.btnUbah);
        cvStatus = view.findViewById(R.id.cvStatus);
        etNoTelp = view.findViewById(R.id.et_no_telepon);
        etJabatan = view.findViewById(R.id.et_jabatan);
        etPdfPath = view.findViewById(R.id.etPdfPath);
        et_loket = view.findViewById(R.id.et_loket);
        btnKembali = view.findViewById(R.id.btnKembali);
        etPdfPath.setEnabled(false);
        viewPager = view.findViewById(R.id.viewpager);
        stepView = view.findViewById(R.id.step_view);
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

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(requireContext().getExternalFilesDir(null) + File.separator + "kevin.pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("KEVIN", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
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
                                stepProposal(2);
                                tvStatus.setText("Proposal Diterima");
                                btnDownloadSurat.setText("Download Surat Persetujuan");
                                btnDownloadSurat.setVisibility(View.VISIBLE);
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
                                tvStatus.setText("Proposal Ditolak");
                                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                                btnDownloadSurat.setText("Download Surat Penolakan");
                                btnDownloadSurat.setVisibility(View.VISIBLE);
                                stepProposal(2);

                            }else {
                                if (response.body().getVerified().equals("0")) {
                                    tvStatus.setText("Tidak lolos verifikasi");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
                                    btnDownloadSurat.setVisibility(View.GONE);
                                    stepProposal(1);

                                }else if (response.body().getVerified().equals("1")){
                                    tvStatus.setText("Lolos verifikasi");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.green));
                                    btnDownloadSurat.setVisibility(View.GONE);
                                    stepProposal(1);

                                } else {
                                    tvStatus.setText("Menunggu");
                                    cvStatus.setCardBackgroundColor(getContext().getColor(R.color.yelllow));
                                    btnDownloadSurat.setVisibility(View.GONE);
                                    stepProposal(0);

                                }
                            }

                            if (response.body().getVerified().equals("1")) {
                                btnUbah.setVisibility(View.GONE);
                            }else if (response.body().getVerified().equals("0")) {
                                btnUbah.setVisibility(View.GONE);
                            }
                            else {
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
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
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

    private void stepProposal(Integer step) {

        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(getContext(), R.color.main))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getContext(), R.color.main))
                .selectedStepNumberColor(ContextCompat.getColor(getContext(), R.color.white))
                // set name steps
                .steps(new ArrayList<String>() {{
                    add("Pengajuan Proposal");
                    add("Verifikasi Proposal");
                    add("Keputusan Akhir");
                }})

                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        stepView.go(step, false);


    }

}