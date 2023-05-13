package com.example.adisti.Util;

import com.example.adisti.Model.ProposalModel;
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
}
