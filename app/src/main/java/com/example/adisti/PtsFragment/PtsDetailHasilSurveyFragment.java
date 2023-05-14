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

public class PtsDetailHasilSurveyFragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etNilaiPengajuan, etBarangDiajukan, etKelayakan, etBentukBantuan;

    Button  btnEdit, btnBatal;
    SharedPreferences sharedPreferences;
    String proposalId, kodeLoket, noUrutProposal;
    PtsInterface ptsInterface;
    LinearLayout layoutBentukBantuan;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_detail_hasil_survey, container, false);
        init(view);
        displayHasilSurvey();



        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        return view;
    }

    private void init(View view) {
        etNamaPetugasSurvey = view.findViewById(R.id.etNamaPetugasSurvey);
        etJabatanPetugasSurvey = view.findViewById(R.id.etJabatanPetugasSurvey);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnBatal = view.findViewById(R.id.btnBatal);
        etKelayakan = view.findViewById(R.id.spKelayakan);
        etNilaiPengajuan = view.findViewById(R.id.etNilaiPengajuan);
        etBarangDiajukan = view.findViewById(R.id.etBarangDiajukan);
        etBentukBantuan = view.findViewById(R.id.spBentukBantuan);
        layoutBentukBantuan = view.findViewById(R.id.layoutBantuan);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        noUrutProposal = getArguments().getString("no_urut_proposal");
        ptsInterface = DataApi.getClient().create(PtsInterface.class);


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
                    etKelayakan.setText(response.body().getKelayakan());
                    etBentukBantuan.setText(response.body().getBentukBantuan());
                    etNilaiPengajuan.setText(response.body().getNilaiPengajuan());


                    if (response.body().getBentukBantuan().equals("Barang")) {
                        layoutBentukBantuan.setVisibility(View.VISIBLE);
                        etBarangDiajukan.setText(response.body().getBarangDiajukan());
                    }else {
                        layoutBentukBantuan.setVisibility(View.GONE);
                    }

                }else {
                    progressDialog.dismiss();
                    Toasty.error(getContext(), "Terjadi Kesalahan", Toasty.LENGTH_SHORT).show();
                    btnEdit.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<HasilSurveyModel> call, Throwable t) {
                progressDialog.dismiss();
                btnEdit.setEnabled(false);
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