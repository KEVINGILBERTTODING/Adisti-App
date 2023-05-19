package com.example.adisti.Util;

import android.app.VoiceInteractor;

import com.airbnb.lottie.L;
import com.example.adisti.Model.BidangManfaatPerusahaanModel;
import com.example.adisti.Model.IndikatorBidangManfaatPerusahaanModel;
import com.example.adisti.Model.JabatanPicModel;
import com.example.adisti.Model.KajianManfaatModel;
import com.example.adisti.Model.KategoriPemohonBantuanModel;
import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.PihakPenerimaBantuanModel;
import com.example.adisti.Model.PilarModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.RanModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.TpbModel;
import com.example.adisti.Model.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @GET("pic/getAllProposalKajianManfaat")
    Call<List<ProposalModel>>getAllProposalKajianManfaat(
            @Query("kode_loket") String kodeLoket,
            @Query("tipe") Integer tipe,
            @Query("code_kajian_manfaat") Integer codeKajianManfaat
    );


    @Multipart
    @POST("pic/insertKajianManfaat")
    Call<ResponseModel>updateKajianManfaat(
            @PartMap Map<String, RequestBody> textData
            );


    @Multipart
    @POST("pic/updateKajianManfaat")
    Call<ResponseModel>updateKajianManfaat2(
            @PartMap Map<String, RequestBody> textData
    );


    @GET("pic/getKategoriPemohonBantuan")
    Call<List<KategoriPemohonBantuanModel>>getKategoriPemohonBantuan();

    @GET("pic/getPenerimaBantuan")
    Call<List<PihakPenerimaBantuanModel>>getPenerimaBantuan(
            @Query("kategori_penerima_id") Integer kategori_penerima_id
    );


    @GET("pic/getBidangManfaatPerusahaan")
    Call<List<BidangManfaatPerusahaanModel>>getBidangManfaatPerusahaan(
    );

    @GET("pic/getIndikatorBidangManfaatPerusahaan")
    Call<List<IndikatorBidangManfaatPerusahaanModel>>getIndikatorBidangManfaat(
            @Query("bidang_manfaat_id") Integer bidangManfaatId
    );

    @GET("pic/getallpilar")
    Call<List<PilarModel>>getAllPilar(
    );

    @GET("pic/getTpb")
    Call<List<TpbModel>>getTpb(
            @Query("pilar_id") Integer pilarId
    );

    @GET("pic/getAllRan")
    Call<List<RanModel>>getRan(
            @Query("tpb_id") Integer tpbId
    );


    @GET("pic/getKajianManfaatById")
    Call<KajianManfaatModel>getKajianManfaatById(
            @Query("proposal_id") String proposalId
    );

    @GET("pic/getuserbyid")
    Call<UserModel>getUserById(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("pic/updatepassword")
    Call<ResponseModel>updatePassword(
            @Field("user_id") String userId,
            @Field("old_pass") String oldPass,
            @Field("new_pass") String newPass
    );

    @Multipart
    @POST("pic/uploadphotoprofile")
    Call<ResponseModel>uploadPhotoProfile(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("pic/updateProfile")
    Call<ResponseModel>updateProfile(
            @Field("user_id") String userId,
            @Field("usernamee") String username,
            @Field("nama") String name
    );

    @GET("pic/getAllProposalEvaluasiKegiatan")
    Call<List<ProposalModel>> getAllProposalEvaluasiKegiatan();




}
