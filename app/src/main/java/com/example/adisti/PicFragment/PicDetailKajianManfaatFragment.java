package com.example.adisti.PicFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.adisti.Model.BidangManfaatPerusahaanModel;
import com.example.adisti.Model.IndikatorBidangManfaatPerusahaanModel;
import com.example.adisti.Model.KajianManfaatModel;
import com.example.adisti.Model.KategoriPemohonBantuanModel;
import com.example.adisti.Model.PihakPenerimaBantuanModel;
import com.example.adisti.Model.PilarModel;
import com.example.adisti.Model.RanModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.TpbModel;
import com.example.adisti.PicAdapter.SpinnerBidangManfaatPerusahaanAdapter;
import com.example.adisti.PicAdapter.SpinnerIndikatorBidangManfaatAdapter;
import com.example.adisti.PicAdapter.SpinnerKategoriPemohonBantuanAdapter;
import com.example.adisti.PicAdapter.SpinnerPemohonBantuanAdapter;
import com.example.adisti.PicAdapter.SpinnerPilarAdapter;
import com.example.adisti.PicAdapter.SpinnerRanAdapter;
import com.example.adisti.PicAdapter.SpinnerTpbAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicDetailKajianManfaatFragment extends Fragment {
    String proposalId, kodeLoket;
    SharedPreferences sharedPreferences;

    PicInterface picInterface;
    Button btnEdit, btnBatal;
    EditText etManfaatPenerimaBantuan, etIndikator, etPenjelasanIndikator, etKategoriPemohonBantuan, etPihakPenerimaBantuan,
             etPilar, etTpb, etRan, etManfaatPerusahaan;
    LinearLayout layoutCustomeIndikator;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_detail_kajian_manfaat, container, false);
        init(view);
        getKajianManfaatDetail();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PicUpdateKajianManfaatFragment();
                Bundle bundle = new Bundle();
                bundle.putString("proposal_id", proposalId);
                fragment.setArguments(bundle);
                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framePic, fragment).addToBackStack(null).commit();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
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
        btnEdit = view.findViewById(R.id.btnEdit);
        btnBatal = view.findViewById(R.id.btnBatal);
        picInterface = DataApi.getClient().create(PicInterface.class);



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
                        Log.e("asd", "onFailure: ",t );

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