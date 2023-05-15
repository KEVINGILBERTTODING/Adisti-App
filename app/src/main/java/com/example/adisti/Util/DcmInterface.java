package com.example.adisti.Util;

import com.example.adisti.Model.PendapatTanggapanModel;
import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface DcmInterface {


    // get proposal kasubag belum insert pendapat
    @GET("dcm/getProposalKasubag")
    Call<List<ProposalModel>>getProposalKasubag();


    // get proposal kabag belum insert pendapat
    @GET("dcm/getProposalKabag")
    Call<List<ProposalModel>>getProposalKabag();


    // get proposal kacab belum insert pendapat
    @GET("dcm/getProposalKacab")
    Call<List<ProposalModel>>getProposalKacab();

    @GET("dcm/getDetailUserById")
    Call<UserModel>getDetailUserById(
            @Query("user_id") String userId
    );

    @Multipart
    @POST("dcm/insertPendapatKasubag")
    Call<ResponseModel>insertPendapatKasubag(
            @PartMap Map<String, RequestBody> textData
            );

    @GET("dcm/getPendapatTanggapanById")
    Call<PendapatTanggapanModel>getPendapatById(
            @Query("proposal_id") String proposalId
    );

    // Get all proposal yang telah diinput
    // pendapat kasubag
    @GET("dcm/getProposalKasubagPendapat")
    Call<List<ProposalModel>>getProposalKasubagPendapat();

    @Multipart
    @POST("dcm/updatePendapatTanggapanKasubag")
    Call<ResponseModel>updatePendapatKasubag(
            @PartMap Map<String, RequestBody> textData
    );

}
