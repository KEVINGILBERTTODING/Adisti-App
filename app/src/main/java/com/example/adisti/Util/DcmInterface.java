package com.example.adisti.Util;

import com.example.adisti.Model.ProposalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

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
}
