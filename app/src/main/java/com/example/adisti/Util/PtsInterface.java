package com.example.adisti.Util;

import com.example.adisti.Model.HasilSurveyModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.SurveyModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface PtsInterface {


    @GET("pts/getProposalSurvey")
    Call<List<ProposalModel>> getProposalSurvey(
            @Query("kode_loket") String kodeLoket,
            @Query("survey") Integer survey
    );

    @Multipart
    @POST("pts/insertSurvey")
    Call<ResponseModel>insertSurvey(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part ktp,
            @Part MultipartBody.Part butab,
            @Part MultipartBody.Part fotoSurvey
            );

    @GET("pts/getSurveyById")
    Call<SurveyModel>getSurveyById(
            @Query("proposal_id") String proposalId
    );

    @Multipart
    @POST("pts/updateSurvey")
    Call<ResponseModel>editSurveyAll(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part ktp,
            @Part MultipartBody.Part butab,
            @Part MultipartBody.Part fotoSurvey
    );

    @Multipart
    @POST("pts/updateSurvey")
    Call<ResponseModel>editSurveyKtp(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part ktp
    );

    @Multipart
    @POST("pts/updateSurvey")
    Call<ResponseModel>editSurveyButab(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part butab
    );

    @Multipart
    @POST("pts/updateSurvey")
    Call<ResponseModel>editSurveyFotoSurvey(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part fotoSurvey
    );

    @Multipart
    @POST("pts/updateSurvey")
    Call<ResponseModel>editSurveyTextOnly(
            @PartMap Map<String, RequestBody>textData
    );

    @GET("pts/getProposalHasilSurvey")
    Call<List<ProposalModel>> getProposalHasilSurvey(
            @Query("kode_loket") String kodeLoket,
            @Query("hasil_survey") Integer survey
    );

    @Multipart
    @POST("pts/inserthasilsurvey")
    Call<ResponseModel>insertHasilSurvey(
            @PartMap Map<String, RequestBody> textData
    );

    @GET("pts/getHasilSurveyById/")
    Call<HasilSurveyModel>getHasilSurveyById(
            @Query("proposal_id") String proposalId
    );

    @Multipart
    @POST("pts/updateHasilSurvey")
    Call<ResponseModel>updateHasilSurvey(
            @PartMap Map<String, RequestBody>textData
    );

}
