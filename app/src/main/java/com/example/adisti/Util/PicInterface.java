package com.example.adisti.Util;

import com.example.adisti.Model.JabatanPicModel;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PicInterface {

    @GET("pic/getAllProposal")
    Call<List<ProposalModel>>getAllTipeVerified(
            @Query("tipe") Integer tipe,
            @Query("kode_loket") String kodeLoket
    );

    @GET("pic/getAllLoket")
    Call<List<LoketModel>>getLoket();

    @GET("pic/getAllJabatanPic")
    Call<List<JabatanPicModel>>getJabatanPic();

    @FormUrlEncoded
    @POST("pic/verifiedProposal")
    Call<ResponseModel>verifiedProposal(
            @Field("proposal_id") String propoalId,
            @Field("loket") String loket,
            @Field("nama_loket") String namaLoket,
            @Field("jabatan_pic") String jabatanPic
    );

    @FormUrlEncoded
    @POST("pic/rejectedProposal")
    Call<ResponseModel>rejectProposal(
            @Field("proposal_id") String propoalId
    );



}
