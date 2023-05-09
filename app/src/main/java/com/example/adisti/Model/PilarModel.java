package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PilarModel implements Serializable {
    @SerializedName("pilar_id")
    Integer pilarId;
    @SerializedName("nama_pilar")
    String namaPilar;

    public PilarModel(Integer pilarId, String namaPilar) {
        this.pilarId = pilarId;
        this.namaPilar = namaPilar;
    }

    public Integer getPilarId() {
        return pilarId;
    }

    public void setPilarId(Integer pilarId) {
        this.pilarId = pilarId;
    }

    public String getNamaPilar() {
        return namaPilar;
    }

    public void setNamaPilar(String namaPilar) {
        this.namaPilar = namaPilar;
    }
}
