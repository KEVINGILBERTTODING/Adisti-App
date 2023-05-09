package com.example.adisti.PicFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.adisti.Model.BidangManfaatPerusahaanModel;
import com.example.adisti.Model.IndikatorBidangManfaatPerusahaanModel;
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

public class PicInsertKajianManfaatFragment extends Fragment {
    String proposalId, kodeLoket, kategoriPemohonBantuan, pihakPenerimaBantuan, bidangManfaatPerusahaan,
    indikatorBidangManfaat, pilar, tpb, ran ;
    SharedPreferences sharedPreferences;
    Spinner spKategoriPemohonBantuan, spPihakPenerimaBantuan, spManfaatPerusahaan, spIndikator, spPilar, spTpb, spRan;
    SpinnerKategoriPemohonBantuanAdapter spinnerKategoriPemohonBantuanAdapter;
    List<KategoriPemohonBantuanModel>kategoriPemohonBantuanModelList;
    SpinnerPemohonBantuanAdapter spinnerPemohonBantuanAdapter;
    SpinnerIndikatorBidangManfaatAdapter spinnerIndikatorBidangManfaatAdapter;
    SpinnerBidangManfaatPerusahaanAdapter spinnerBidangManfaatPerusahaanAdapter;
    SpinnerRanAdapter spinnerRanAdapter;
    SpinnerTpbAdapter spinnerTpbAdapter;
    PicInterface picInterface;
    Button btnSubmit, btnBatal;
    EditText etManfaatPenerimaBantuan, etCustomIndikator, etPenjelasanIndikator;
    LinearLayout layoutCustomeIndikator;
    List<PihakPenerimaBantuanModel>pihakPenerimaBantuanModelList;
    List<BidangManfaatPerusahaanModel>bidangManfaatPerusahaanModelList;
    List<IndikatorBidangManfaatPerusahaanModel>indikatorBidangManfaatPerusahaanModelList;
    List<PilarModel>pilarModelList;
    List<TpbModel> tpbModelList;
    List<RanModel>ranModelList;
    SpinnerPilarAdapter spinnerPilarAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_insert_kajian_manfaat, container, false);
        init(view);
        getKategoriPemohonBantuan();
        getBidangManfaatPerusahaan();
        getAllPilar();

        spKategoriPemohonBantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategoriPemohonBantuan = spinnerKategoriPemohonBantuanAdapter.getNamaKategoriPemohonBantuan(position);
                getPihakPenerimaBantuan(spinnerKategoriPemohonBantuanAdapter.getKategoriPemohonBantuanId(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spPihakPenerimaBantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pihakPenerimaBantuan = spinnerPemohonBantuanAdapter.getNamaPihak(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spManfaatPerusahaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getIndikatorBidangMafaat(spinnerBidangManfaatPerusahaanAdapter.getBidangManfaatId(position));
                bidangManfaatPerusahaan = spinnerBidangManfaatPerusahaanAdapter.getNamaBidang(position);
                if (spinnerBidangManfaatPerusahaanAdapter.getNamaBidang(position).equals("Isian bebas")) {
                    spIndikator.setVisibility(View.GONE);
                    layoutCustomeIndikator.setVisibility(View.VISIBLE);

                }else {
                    spIndikator.setVisibility(View.VISIBLE);
                    layoutCustomeIndikator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spIndikator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indikatorBidangManfaat = spinnerIndikatorBidangManfaatAdapter.getNamaIndikator(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spPilar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pilar = spinnerPilarAdapter.getNamaPilar(position);
                getTpb(spinnerPilarAdapter.getPilarId(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTpb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tpb = spinnerTpbAdapter.getNamaTpb(position);
                getRan(spinnerTpbAdapter.getTpbId(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spRan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ran = spinnerRanAdapter.getRanId(position);
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
                if (etManfaatPenerimaBantuan.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Field manfaat tidak boleh kosong", Toasty.LENGTH_SHORT).show();
                }else {
                    updateKajianManfaat();
                }




            }
        });


        return view;
    }

    private void init(View view) {
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        proposalId = getArguments().getString("proposal_id");
        kodeLoket = sharedPreferences.getString("kode_loket", null);
        spKategoriPemohonBantuan = view.findViewById(R.id.spkategoriPemohonBantuan);
        spPihakPenerimaBantuan = view.findViewById(R.id.spPihakPenerimaBantuan);
        spManfaatPerusahaan = view.findViewById(R.id.spManfaatPerusahaan);
        spIndikator = view.findViewById(R.id.spIndikator);
        layoutCustomeIndikator = view.findViewById(R.id.layoutCustomeIndikator);
        etManfaatPenerimaBantuan = view.findViewById(R.id.etManfaatPenerimaBantuan);
        etCustomIndikator = view.findViewById(R.id.etCustomIndikator);
        etPenjelasanIndikator = view.findViewById(R.id.etPenjelasanIndikator);
        spPilar = view.findViewById(R.id.spPilar);
        spTpb = view.findViewById(R.id.spTpb);
        spRan = view.findViewById(R.id.spRan);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnBatal = view.findViewById(R.id.btnBatal);
        picInterface = DataApi.getClient().create(PicInterface.class);



    }

    private void getKategoriPemohonBantuan() {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getKategoriPemohonBantuan().enqueue(new Callback<List<KategoriPemohonBantuanModel>>() {
            @Override
            public void onResponse(Call<List<KategoriPemohonBantuanModel>> call, Response<List<KategoriPemohonBantuanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    kategoriPemohonBantuanModelList = response.body();
                    spinnerKategoriPemohonBantuanAdapter = new SpinnerKategoriPemohonBantuanAdapter(getContext(), kategoriPemohonBantuanModelList);
                    spKategoriPemohonBantuan.setAdapter(spinnerKategoriPemohonBantuanAdapter);

                    progressDialog.dismiss();
                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<KategoriPemohonBantuanModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setCancelable(false);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getKategoriPemohonBantuan();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.dismiss();


            }
        });


    }
    private void getPihakPenerimaBantuan(Integer kategoriID) {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getPenerimaBantuan(kategoriID).enqueue(new Callback<List<PihakPenerimaBantuanModel>>() {
            @Override
            public void onResponse(Call<List<PihakPenerimaBantuanModel>> call, Response<List<PihakPenerimaBantuanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    pihakPenerimaBantuanModelList = response.body();
                    spinnerPemohonBantuanAdapter = new SpinnerPemohonBantuanAdapter(getContext(), pihakPenerimaBantuanModelList);
                    spPihakPenerimaBantuan.setAdapter(spinnerPemohonBantuanAdapter);
                    progressDialog.dismiss();
                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<PihakPenerimaBantuanModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setCancelable(false);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPihakPenerimaBantuan(kategoriID);
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.dismiss();


            }
        });


    }

    private void getBidangManfaatPerusahaan() {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getBidangManfaatPerusahaan()
                .enqueue(new Callback<List<BidangManfaatPerusahaanModel>>() {
            @Override
            public void onResponse(Call<List<BidangManfaatPerusahaanModel>> call, Response<List<BidangManfaatPerusahaanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    bidangManfaatPerusahaanModelList = response.body();
                    spinnerBidangManfaatPerusahaanAdapter = new SpinnerBidangManfaatPerusahaanAdapter(getContext(), bidangManfaatPerusahaanModelList
                    );
                    spManfaatPerusahaan.setAdapter(spinnerBidangManfaatPerusahaanAdapter);
                    progressDialog.dismiss();
                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<BidangManfaatPerusahaanModel>> call, Throwable t) {
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setCancelable(false);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getBidangManfaatPerusahaan();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.dismiss();


            }
        });


    }


    private void getIndikatorBidangMafaat(Integer manfaatId) {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getIndikatorBidangManfaat(manfaatId)
                .enqueue(new Callback<List<IndikatorBidangManfaatPerusahaanModel>>() {
                    @Override
                    public void onResponse(Call<List<IndikatorBidangManfaatPerusahaanModel>> call, Response<List<IndikatorBidangManfaatPerusahaanModel>> response) {
                        if (response.isSuccessful() && response.body().size() > 0) {
                            indikatorBidangManfaatPerusahaanModelList = response.body();
                            spinnerIndikatorBidangManfaatAdapter = new SpinnerIndikatorBidangManfaatAdapter(getContext(), indikatorBidangManfaatPerusahaanModelList);
                            spIndikator.setAdapter(spinnerIndikatorBidangManfaatAdapter);
                            progressDialog.dismiss();
                        }else {
                            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<IndikatorBidangManfaatPerusahaanModel>> call, Throwable t) {
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.setCancelable(false);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getIndikatorBidangMafaat(manfaatId);
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.dismiss();


                    }
                });


    }

    private void getAllPilar() {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getAllPilar()
                .enqueue(new Callback<List<PilarModel>>() {
                    @Override
                    public void onResponse(Call<List<PilarModel>> call, Response<List<PilarModel>> response) {
                        if (response.isSuccessful() && response.body().size() > 0) {
                            pilarModelList = response.body();
                            spinnerPilarAdapter = new SpinnerPilarAdapter(getContext(), pilarModelList);
                            spPilar.setAdapter(spinnerPilarAdapter);
                            progressDialog.dismiss();
                        }else {
                            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PilarModel>> call, Throwable t) {
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.setCancelable(false);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getAllPilar();
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.dismiss();


                    }
                });


    }
    private void getTpb(Integer pilarId) {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getTpb(pilarId)
                .enqueue(new Callback<List<TpbModel>>() {
                    @Override
                    public void onResponse(Call<List<TpbModel>> call, Response<List<TpbModel>> response) {
                        if (response.isSuccessful() && response.body().size() > 0) {
                            tpbModelList = response.body();
                            spinnerTpbAdapter = new SpinnerTpbAdapter(getContext(), tpbModelList);
                            spTpb.setAdapter(spinnerTpbAdapter);
                            progressDialog.dismiss();
                        }else {
                            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TpbModel>> call, Throwable t) {
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.setCancelable(false);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getTpb(pilarId);
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.dismiss();


                    }
                });


    }
    private void getRan(Integer tpbId) {
        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();

        picInterface.getRan(tpbId)
                .enqueue(new Callback<List<RanModel>>() {
                    @Override
                    public void onResponse(Call<List<RanModel>> call, Response<List<RanModel>> response) {
                        if (response.isSuccessful() && response.body().size() > 0) {
                            ranModelList = response.body();
                            spinnerRanAdapter = new SpinnerRanAdapter(getContext(), ranModelList);
                            spRan.setAdapter(spinnerRanAdapter);
                            progressDialog.dismiss();
                            Log.d("asdsad", "onResponse: " +"berhaslll");
                        }else {
                            Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Log.d("asdsad", "onResponse: " +"agagal");

                        }
                    }

                    @Override
                    public void onFailure(Call<List<RanModel>> call, Throwable t) {
                        Dialog dialogNoConnection = new Dialog(getContext());
                        dialogNoConnection.setCancelable(false);
                        dialogNoConnection.setCanceledOnTouchOutside(false);
                        dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                        dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                        btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getRan(tpbId);
                                dialogNoConnection.dismiss();
                            }
                        });
                        dialogNoConnection.dismiss();
                        Log.e("ASDdas", "onFailure: " , t );


                    }
                });


    }

    private void updateKajianManfaat() {

        Dialog progressDialog = new Dialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.dialog_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.dismiss();



        HashMap map = new HashMap();
        map.put("proposal_id", RequestBody.create(MediaType.parse("text/plain"), proposalId));
        map.put("indikator_manfaat_perusahaan", RequestBody.create(MediaType.parse("text/plain"), indikatorBidangManfaat));
        map.put("bidang_manfaat_perusahaan", RequestBody.create(MediaType.parse("text/plain"), bidangManfaatPerusahaan));
        map.put("kategori_pemohon_bantuan", RequestBody.create(MediaType.parse("text/plain"), kategoriPemohonBantuan));
        map.put("pihak_penerima_bantuan", RequestBody.create(MediaType.parse("text/plain"), pihakPenerimaBantuan));
        map.put("manfaat_penerima_bantuan", RequestBody.create(MediaType.parse("text/plain"), etManfaatPenerimaBantuan.getText().toString()));
        map.put("indikator", RequestBody.create(MediaType.parse("text/plain"), etCustomIndikator.getText().toString()));
        map.put("penjelasan_indikator", RequestBody.create(MediaType.parse("text/plain"),etPenjelasanIndikator.getText().toString()));
        map.put("pilar", RequestBody.create(MediaType.parse("text/plain"), pilar));
        map.put("tpb", RequestBody.create(MediaType.parse("text/plain"), tpb));
        map.put("ran", RequestBody.create(MediaType.parse("text/plain"), ran));
        map.put("kode_loket", RequestBody.create(MediaType.parse("text/plain"), kodeLoket));

        picInterface.updateKajianManfaat(map).enqueue(new Callback<ResponseModel>() {
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
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internett", Toasty.LENGTH_SHORT).show();

            }
        });


    }


}