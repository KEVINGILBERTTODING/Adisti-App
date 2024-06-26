package com.example.adisti.Util;

import com.example.adisti.Model.LoketModel;
import com.example.adisti.Model.NotificationModel;
import com.example.adisti.Model.PengajuModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface PengajuInterface {

    @GET("pengaju/getAllProposal")
    Call<List<ProposalModel>>getAllProposal(
            @Query("user_id") String userId
    );

    @GET("pengaju/getAllProposalPengajuStatus")
    Call<List<ProposalModel>>getAllProposalPengajuStatus(
            @Query("user_id") String userId,
            @Query("status") String status
    );

    @GET("pengaju/getAllLoket")
    Call<List<LoketModel>>getAllLoket();

    @GET("pengaju/getPengajuById")
    Call<PengajuModel>getPengajuById(
            @Query("user_id") String userId
    );

    @Multipart
    @POST("pengaju/insertProposal")
    Call<ResponseModel>insertProposal(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part proposal
            );

    @FormUrlEncoded
    @POST("pengaju/deleteproposal")
    Call<ResponseModel>deleteProposal(
            @Field("proposal_id") String proposalId
    );

    @GET("pengaju/filterProposal")
    Call<List<ProposalModel>>getAllFilterProposal(
            @Query("user_id") String userId,
            @Query("date_start") String dateStart,
            @Query("date_end") String dateEnd
    );

    @GET("pengaju/getProposalPengajuById")
    Call<ProposalModel>getProposalById(
            @Query("proposal_id") String userId
    );

    @Multipart
    @POST("pengaju/updateProposal")
    Call<ResponseModel>updateProposalOnlyTextData(
            @PartMap Map<String, RequestBody>textData
    );

    @Multipart
    @POST("pengaju/updateProposal")
    Call<ResponseModel>updateProposal(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part proposal
    );

    @Multipart
    @POST("pengaju/uploadphotoprofile")
    Call<ResponseModel>uploadPhotoProfile(
            @PartMap Map<String, RequestBody>textData,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("pengaju/updatepassword")
    Call<ResponseModel>updatePassword(
            @Field("user_id") String userId,
            @Field("old_pass") String oldPass,
            @Field("new_pass") String newPass
    );

    @GET("pengaju/downloadProposal/{id}")
    Call<ResponseModel>  getProposalPath(
            @Path("id") String proposalId
    );


    @GET
    Call<ResponseBody> downloadFileProposal(@Url  String url);

    @Multipart
    @POST("pengaju/updateProfile")
    Call<ResponseModel>updateProfile(
            @PartMap Map<String, RequestBody>textData
    );


    @GET("pengaju/getAllNotification")
    Call<List<NotificationModel>>getAllNotification(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("pengaju/deletenotification")
    Call<ResponseModel>deleteNotifcation(
            @Field("notif_id") Integer notifId
    );

    @GET("pengaju/countNotification")
    Call<List<NotificationModel>>countNotification (
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("pengaju/updateNotificaton")
    Call<ResponseModel>updateNotification(
            @Field("status") String status
    );

    @GET("pengaju/getLoket")
    Call<LoketModel> getLoketById(
            @Query("id") String id
    );

}
