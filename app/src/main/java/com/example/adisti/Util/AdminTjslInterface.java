package com.example.adisti.Util;

import com.example.adisti.Model.LpjKegiatanModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.RealisasiBantuanModel;
import com.example.adisti.Model.ResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface AdminTjslInterface {

    // get all proposal yang belum input realisasi bantuan
    @GET("admintjsl/getProposalPendapatTanggapan")
    Call<List<ProposalModel>> getProposalPendapatTanggapan();


    // get all proposal yang telah realisasi bantuan
    @GET("admintjsl/getProposalRealisasiBantuan")
    Call<List<ProposalModel>> getProposalrealisasiBantuan();

    @Multipart
    @POST("adminTjsl/insertRealisasiBantuan")
    Call<ResponseModel> insertRealisasiBantuan (
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part fileFotoKegiatan,
            @Part MultipartBody.Part fileKuitansi,
            @Part MultipartBody.Part fileBast,
            @Part MultipartBody.Part fileSpt,
            @Part MultipartBody.Part buktiPembayaran
            );

    @GET("adminTjsl/getRealisasiById")
    Call<RealisasiBantuanModel>getRealisasiBantuanById(
            @Query("proposal_id") String proposalId
    );


    @Multipart
    @POST("adminTjsl/updateRealisasiTextData")
    Call<ResponseModel> updateRealisasiTextData(
            @PartMap Map<String, RequestBody> textData
    );


    @Multipart
    @POST("adminTjsl/updateFileRealisasiBantuan")
    Call<ResponseModel> updateFileRealisasiBantuan(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part fileFotoKegiatan
    );


    // belum input lpj kegiatan
    @GET("adminTjsl/getProposalRealisasiBantuan2")
    Call<List<ProposalModel>> getProposalRealisasiBantuan2();


    // telah input lpj kegiatan

    @GET("adminTjsl/getProposalLpjKegiatan")
    Call<List<ProposalModel>> getProposalLpj();

    @Multipart
    @POST("adminTjsl/insertLpjKegiatan")
    Call<ResponseModel> insertLpjKegiatan(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part fileFotoKegiatan,
            @Part MultipartBody.Part fileLpjKegiatan
    );

    @GET("adminTjsl/getLpjKegiatan")
    Call<LpjKegiatanModel>getlPjKegiatanById(
            @Query("proposal_id") String propossalId
    );

    @Multipart
    @POST("admintjsl/updateLpjKegiatan")
    Call<ResponseModel> updateFileFotoKegiatanLpj (
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("admintjsl/updateLpjKegiatan")
    Call<ResponseModel> updateLpjKegiatanLpj (
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part fileLpj
    );

    @Multipart
    @POST("admintjsl/updateLpjKegiatan")
    Call<ResponseModel> updateAllFileLpjKegiatan (
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part fotoKegiatan,
            @Part MultipartBody.Part fileLpj
    );

    @GET("admintjsl/filterLaporanProposal")
    Call<List<ProposalModel>> filterLaporanProposal(
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd,
            @Query("kode_loket") String kodeLoket,
            @Query("status") String status
    );

    @GET("adminTjsl/getAllProposal")
    Call<List<ProposalModel>> getAllProposal();
}
