package com.example.adisti.DcmFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.Model.HasilSurveyModel;
import com.example.adisti.PtsFragment.PtsEditHasilSurveyFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PtsInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DcmDetailHasilSurveyFragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etNilaiPengajuan, etBarangDiajukan, etKelayakan, etBentukBantuan;

    Button  btnInsertKasubag, btnBatal, btnDetailPendapatKasubag, btnInsertKabag, btnDetailPendapatKabag,
            btnBatal2, btnInsertKacab, btnDetailPendapatKacab, btnBatal3;
    SharedPreferences sharedPreferences;
    String proposalId, kodeLoket, noUrutProposal, userId;
    PtsInterface ptsInterface;
    LinearLayout layoutBentukBantuan, layoutKabag, layoutKacab;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dcm_detail_hasil_survey, container, false);
        init(view);
        displayHasilSurvey();



        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnInsertKasubag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PtsEditHasilSurveyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                bundle.putString("no_urut_proposal", noUrutProposal);
                fragment.setArguments(bundle);
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framePts, fragment).addToBackStack(null).commit();


            }
        });

        // Hilangkan button

        if (userId.equals("27")) {
            layoutKabag.setVisibility(View.GONE);
            layoutKacab.setVisibility(View.GONE);
            btnDetailPendapatKasubag.setVisibility(View.GONE);
        }else if (userId.equals("28")) {
            btnInsertKasubag.setVisibility(View.GONE);
            btnBatal.setVisibility(View.GONE);
            btnDetailPendapatKasubag.setVisibility(View.VISIBLE);
            btnDetailPendapatKabag.setVisibility(View.GONE);
        }else {
            btnInsertKasubag.setVisibility(View.GONE);
            btnBatal.setVisibility(View.GONE);
            btnDetailPendapatKasubag.setVisibility(View.VISIBLE);
            btnInsertKabag.setVisibility(View.GONE);
            btnBatal2.setVisibility(View.GONE);
            btnDetailPendapatKabag.setVisibility(View.VISIBLE);
            btnInsertKacab.setVisibility(View.GONE);
        }




        return view;
    }

    private void init(View view) {
        etNamaPetugasSurvey = view.findViewById(R.id.etNamaPetugasSurvey);
        etJabatanPetugasSurvey = view.findViewById(R.id.etJabatanPetugasSurvey);
        btnInsertKasubag = view.findViewById(R.id.btnInsertKasubag);
        btnBatal = view.findViewById(R.id.btnBatal);
        etKelayakan = view.findViewById(R.id.spKelayakan);
        layoutKabag = view.findViewById(R.id.layoutKabag);
        layoutKacab = view.findViewById(R.id.layoutKacab);
        etNilaiPengajuan = view.findViewById(R.id.etNilaiPengajuan);
        etBarangDiajukan = view.findViewById(R.id.etBarangDiajukan);
        etBentukBantuan = view.findViewById(R.id.spBentukBantuan);
        layoutBentukBantuan = view.findViewById(R.id.layoutBantuan);
        btnDetailPendapatKasubag = view.findViewById(R.id.btnDetailPendapatKasubag);
        btnInsertKabag = view.findViewById(R.id.btnInsertKabag);
        btnDetailPendapatKabag = view.findViewById(R.id.btnDetailPendapatKabag);
        btnBatal2 = view.findViewById(R.id.btnBatal2);
        btnInsertKacab = view.findViewById(R.id.btnInsertKacab);
        btnDetailPendapatKacab = view.findViewById(R.id.btnDetailPendapatKacab);
        btnBatal3 = view.findViewById(R.id.btnBatal3);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        userId = sharedPreferences.getString("user_id", null);
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
                    btnInsertKasubag.setEnabled(false);
                    btnInsertKacab.setEnabled(false);
                    btnInsertKabag.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<HasilSurveyModel> call, Throwable t) {
                progressDialog.dismiss();
                btnInsertKasubag.setEnabled(false);
                btnInsertKacab.setEnabled(false);
                btnInsertKabag.setEnabled(false);
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