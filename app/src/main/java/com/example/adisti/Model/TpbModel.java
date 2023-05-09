package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TpbModel implements Serializable {
    @SerializedName("tpb_id")
    Integer tpbId;
    @SerializedName("pilar_id")
    Integer pilarId;
    @SerializedName("nama_tpb")
    String namaTpb;

    public TpbModel(Integer tpbId, Integer pilarId, String namaTpb) {
        this.tpbId = tpbId;
        this.pilarId = pilarId;
        this.namaTpb = namaTpb;
    }

    public Integer getTpbId() {
        return tpbId;
    }

    public void setTpbId(Integer tpbId) {
        this.tpbId = tpbId;
    }

    public Integer getPilarId() {
        return pilarId;
    }

    public void setPilarId(Integer pilarId) {
        this.pilarId = pilarId;
    }

    public String getNamaTpb() {
        return namaTpb;
    }

    public void setNamaTpb(String namaTpb) {
        this.namaTpb = namaTpb;
    }
}
