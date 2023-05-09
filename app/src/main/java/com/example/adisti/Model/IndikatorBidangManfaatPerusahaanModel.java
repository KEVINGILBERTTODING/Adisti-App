package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IndikatorBidangManfaatPerusahaanModel implements Serializable {
    @SerializedName("indikator_manfaat_id")
    Integer indikatorId;
    @SerializedName("bidang_manfaat_id")
    Integer bidangManfaatId;
    @SerializedName("nama_indikator")
    String namaIndikator;

    public IndikatorBidangManfaatPerusahaanModel(Integer indikatorId, Integer bidangManfaatId, String namaIndikator) {
        this.indikatorId = indikatorId;
        this.bidangManfaatId = bidangManfaatId;
        this.namaIndikator = namaIndikator;
    }

    public Integer getIndikatorId() {
        return indikatorId;
    }

    public void setIndikatorId(Integer indikatorId) {
        this.indikatorId = indikatorId;
    }

    public Integer getBidangManfaatId() {
        return bidangManfaatId;
    }

    public void setBidangManfaatId(Integer bidangManfaatId) {
        this.bidangManfaatId = bidangManfaatId;
    }

    public String getNamaIndikator() {
        return namaIndikator;
    }

    public void setNamaIndikator(String namaIndikator) {
        this.namaIndikator = namaIndikator;
    }
}
