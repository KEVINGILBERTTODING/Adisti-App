package com.example.adisti.Util;

import com.example.adisti.Model.ProposalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PicInterface {

    @GET("pic/getallproposal")
    Call<List<ProposalModel>>getAllProposal(
            @Query("kode_loket") String kodeLoket
    );
}
