package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BidangManfaatPerusahaanModel implements Serializable {
    @SerializedName("bidang_manfaat_id")
    Integer bidangManfaatId;
    @SerializedName("nama_bidang")
    String namaBidang;

    public BidangManfaatPerusahaanModel(Integer bidangManfaatId, String namaBidang) {
        this.bidangManfaatId = bidangManfaatId;
        this.namaBidang = namaBidang;
    }

    public Integer getBidangManfaatId() {
        return bidangManfaatId;
    }

    public void setBidangManfaatId(Integer bidangManfaatId) {
        this.bidangManfaatId = bidangManfaatId;
    }

    public String getNamaBidang() {
        return namaBidang;
    }

    public void setNamaBidang(String namaBidang) {
        this.namaBidang = namaBidang;
    }
}
