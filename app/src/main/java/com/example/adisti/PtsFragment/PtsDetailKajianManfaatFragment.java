package com.example.adisti.PtsFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.Model.KajianManfaatModel;
import com.example.adisti.PicFragment.PicUpdateKajianManfaatFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PtsDetailKajianManfaatFragment extends Fragment {
    String proposalId, kodeLoket, noUrutProposal;
    SharedPreferences sharedPreferences;

    PicInterface picInterface;
    Button btnEntry, btnKembali;
    EditText etManfaatPenerimaBantuan, etIndikator, etPenjelasanIndikator, etKategoriPemohonBantuan, etPihakPenerimaBantuan,
             etPilar, etTpb, etRan, etManfaatPerusahaan;
    LinearLayout layoutCustomeIndikator;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_detail_kajian_manfaat, container, false);
        init(view);
        getKajianManfaatDetail();

        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PtsInsertSurveyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                bundle.putString("no_urut_proposal", noUrutProposal);
                fragment.setArguments(bundle);
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framePts, fragment).addToBackStack(null).commit();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void init(View view) {
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        kodeLoket = sharedPreferences.getString("kode_loket", null);

        layoutCustomeIndikator = view.findViewById(R.id.layoutCustomeIndikator);
        etManfaatPenerimaBantuan = view.findViewById(R.id.etManfaatPenerimaBantuan);
        etIndikator = view.findViewById(R.id.etIndikator);
        etPenjelasanIndikator = view.findViewById(R.id.etPenjelasanIndikator);
        etKategoriPemohonBantuan = view.findViewById(R.id.etKategoriPemohonBantuan);
        etPihakPenerimaBantuan = view.findViewById(R.id.etPihakPenerimaBantuan);
        etPilar = view.findViewById(R.id.etPilar);
        etTpb = view.findViewById(R.id.etTpb);
        etRan = view.findViewById(R.id.etRan);
        etManfaatPerusahaan = view.findViewById(R.id.etManfaatPerusahaan);
        btnEntry = view.findViewById(R.id.btnEntrySurvey);
        btnKembali = view.findViewById(R.id.btnKembali);
        picInterface = DataApi.getClient().create(PicInterface.class);
        noUrutProposal = getArguments().getString("no_urut_proposal");



    }

    private void getKajianManfaatDetail() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final TextView tvMain;
        tvMain = dialog.findViewById(R.id.tvMainText);
        tvMain.setText("Memuat Data...");
        dialog.show();



        picInterface.getKajianManfaatById(proposalId)
                .enqueue(new Callback<KajianManfaatModel>() {
                    @Override
                    public void onResponse(Call<KajianManfaatModel> call, Response<KajianManfaatModel> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            etManfaatPenerimaBantuan.setText(response.body().getManfaatPenerimaBantuan());
                            etKategoriPemohonBantuan.setText(response.body().getKategoriPemohonBantuan());
                            etPihakPenerimaBantuan.setText(response.body().getPihakPenerimaBantuan());
                            etManfaatPerusahaan.setText(response.body().getBidangManfaatPerusahaan());
                            etIndikator.setText(response.body().getIndikatorManfaatPerusahaan());
                            etPilar.setText(response.body().getPilar());
                            etTpb.setText(response.body().getTpb());
                            etRan.setText(response.body().getNamaRan());
                            etManfaatPerusahaan.setEnabled(false);
                            etIndikator.setEnabled(false);
                            etPilar.setEnabled(false);
                            etTpb.setEnabled(false);
                            etRan.setEnabled(false);
                            etManfaatPenerimaBantuan.setEnabled(false);
                            etKategoriPemohonBantuan.setEnabled(false);
                            etPihakPenerimaBantuan.setEnabled(false);


                            if (response.body().getBidangManfaatPerusahaan().equals("Isian bebas")) {
                                layoutCustomeIndikator.setVisibility(View.VISIBLE);
                                etPenjelasanIndikator.setText(response.body().getPenjelasanIndikator());
                            }else {
                                layoutCustomeIndikator.setVisibility(View.GONE);
                            }


                            dialog.dismiss();

                        }else {
                            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<KajianManfaatModel> call, Throwable t) {


                        dialog.dismiss();
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getKajianManfaatDetail();
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.show();



                    }
                });

    }


}