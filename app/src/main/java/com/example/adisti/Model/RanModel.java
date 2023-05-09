package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RanModel implements Serializable {
    @SerializedName("ran_id")
    String ranId;
    @SerializedName("tpb_id")
    Integer tpbId;
    @SerializedName("nama_ran")
    String namaRan;

    public RanModel(String ranId, Integer tpbId, String namaRan) {
        this.ranId = ranId;
        this.tpbId = tpbId;
        this.namaRan = namaRan;
    }

    public String getRanId() {
        return ranId;
    }

    public void setRanId(String ranId) {
        this.ranId = ranId;
    }

    public Integer getTpbId() {
        return tpbId;
    }

    public void setTpbId(Integer tpbId) {
        this.tpbId = tpbId;
    }

    public String getNamaRan() {
        return namaRan;
    }

    public void setNamaRan(String namaRan) {
        this.namaRan = namaRan;
    }
}
