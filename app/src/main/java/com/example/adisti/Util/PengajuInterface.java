package com.example.adisti.Util;

import com.example.adisti.Model.ProposalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PengajuInterface {

    @GET("pengaju/getAllProposal")
    Call<List<ProposalModel>>getAllProposal(
            @Query("user_id") String userId
    );


}
