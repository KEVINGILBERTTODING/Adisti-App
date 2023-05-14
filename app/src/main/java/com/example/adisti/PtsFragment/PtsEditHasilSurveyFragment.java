package com.example.adisti.PtsFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.Model.HasilSurveyModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.SurveyModel;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PtsInterface;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PtsEditHasilSurveyFragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etNilaiPengajuan, etBarangDiajukan;
    Spinner spKelayakan, spBentukBantuan;
    Button  btnSubmit, btnBatal;
    String kelayakan, bentukBantuan;

    SharedPreferences sharedPreferences;
    String proposalId, kodeLoket, noUrutProposal;
    PtsInterface ptsInterface;
    LinearLayout layoutBentukBantuan;
    String [] opsiKelayakan = {"Layak", "Tidak Layak"};
    String [] opsiBentukBantuan = {"Uang Tunai", "Barang"};





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_edit_hasil_survey, container, false);
        init(view);
        displayHasilSurvey();

        spKelayakan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kelayakan = opsiKelayakan[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spBentukBantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bentukBantuan = opsiBentukBantuan[position];
                if (bentukBantuan.equals("Barang")) {
                    layoutBentukBantuan.setVisibility(View.VISIBLE);
                }else {
                    layoutBentukBantuan.setVisibility(View.GONE);
                    etBarangDiajukan.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                updateData();

            }
        });




        return view;
    }

    private void init(View view) {
        etNamaPetugasSurvey = view.findViewById(R.id.etNamaPetugasSurvey);
        etJabatanPetugasSurvey = view.findViewById(R.id.etJabatanPetugasSurvey);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnBatal = view.findViewById(R.id.btnBatal);
        spKelayakan = view.findViewById(R.id.spKelayakan);
        etNilaiPengajuan = view.findViewById(R.id.etNilaiPengajuan);
        etBarangDiajukan = view.findViewById(R.id.etBarangDiajukan);
        spBentukBantuan = view.findViewById(R.id.spBentukBantuan);
        layoutBentukBantuan = view.findViewById(R.id.layoutBantuan);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        noUrutProposal = getArguments().getString("no_urut_proposal");
        ptsInterface = DataApi.getClient().create(PtsInterface.class);

        ArrayAdapter kelayakanAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiKelayakan);
        kelayakanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKelayakan.setAdapter(kelayakanAdapter);

        ArrayAdapter bentukBantuanAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiBentukBantuan);
        bentukBantuanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBentukBantuan.setAdapter(bentukBantuanAdapter);
    }

    private void updateData() {
        if (etNamaPetugasSurvey.getText().toString().isEmpty()) {
            etNamaPetugasSurvey.setError("Nama Petugas Survey Tidak Boleh Kosong");
            etNamaPetugasSurvey.requestFocus();
            return;
        }else if (etJabatanPetugasSurvey.getText().toString().isEmpty()) {
            etJabatanPetugasSurvey.setError("Jabatan Petugas Survey Tidak Boleh Kosong");
            etJabatanPetugasSurvey.requestFocus();
            return;
        } else if (etNilaiPengajuan.getText().toString().isEmpty()) {
            etNilaiPengajuan.setError("Nilai Pengajuan Tidak Boleh Kosong");
            etNilaiPengajuan.requestFocus();
            return;

        } else if (Integer.parseInt(etNilaiPengajuan.getText().toString()) > 100) {
            etNilaiPengajuan.setError("Nilai Pengajuan Tidak Boleh Lebih Dari 100");
            etNilaiPengajuan.requestFocus();
            return;
        } else {
            Dialog progressDialog = new Dialog(getContext());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progress_bar);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.show();

            HashMap map = new HashMap();
            map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
            map.put("jabatan", RequestBody.create(MediaType.parse("text/plain"), etJabatanPetugasSurvey.getText().toString()));
            map.put("kelayakan", RequestBody.create(MediaType.parse("text/plain"), kelayakan));
            map.put("bentuk_bantuan", RequestBody.create(MediaType.parse("text/plain"), bentukBantuan));
            map.put("nilai_pengajuan", RequestBody.create(MediaType.parse("text/plain"), etNilaiPengajuan.getText().toString()));
            map.put("barang_diajukan", RequestBody.create(MediaType.parse("text/plain"), etBarangDiajukan.getText().toString()));

            ptsInterface.updateHasilSurvey(map).enqueue(new Callback<ResponseModel>() {
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
                        getActivity().onBackPressed();
                    }else {
                        progressDialog.dismiss();
                        Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
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
                            updateData();
                            dialogNoConnection.dismiss();
                        }
                    });
                    dialogNoConnection.show();

                }
            });


        }
    }



    private void displayHasilSurvey() {

        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        ptsInterface.getHasilSurveyById(proposalId).enqueue(new Callback<HasilSurveyModel>() {
            @Override
            public void onResponse(Call<HasilSurveyModel> call, Response<HasilSurveyModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    etNamaPetugasSurvey.setText(response.body().getNamaPetugasSurvey());
                    etJabatanPetugasSurvey.setText(response.body().getJabatan());
                    etNilaiPengajuan.setText(response.body().getNilaiPengajuan());

                    ArrayAdapter bentukBantuanAdapter = (ArrayAdapter) spBentukBantuan.getAdapter();
                    int bentukBantuanPosition = bentukBantuanAdapter.getPosition(response.body().getBentukBantuan());
                    spBentukBantuan.setSelection(bentukBantuanPosition);

                    ArrayAdapter kelayakanAdapter = (ArrayAdapter) spKelayakan.getAdapter();
                    int kelayakanPosition = kelayakanAdapter.getPosition(response.body().getKelayakan());
                    spKelayakan.setSelection(kelayakanPosition);


                    if (response.body().getBentukBantuan().equals("Barang")) {
                        layoutBentukBantuan.setVisibility(View.VISIBLE);
                        etBarangDiajukan.setText(response.body().getBarangDiajukan());
                    }else {
                        layoutBentukBantuan.setVisibility(View.GONE);
                    }

                }else {
                    progressDialog.dismiss();
                    Toasty.error(getContext(), "Terjadi Kesalahan", Toasty.LENGTH_SHORT).show();
                    btnSubmit.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<HasilSurveyModel> call, Throwable t) {
                progressDialog.dismiss();
                btnSubmit.setEnabled(false);
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayHasilSurvey();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();


            }
        });


    }




}