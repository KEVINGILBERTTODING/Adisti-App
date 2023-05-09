package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PihakPenerimaBantuanModel implements Serializable {
    @SerializedName("pihak_pemohon_id")
    Integer pihakPemohonId;
    @SerializedName("kategori_pemohon_bantuan_id")
    Integer kategoriPemohonBantuanId;
    @SerializedName("nama_pihak")
    String namaPihak;

    public PihakPenerimaBantuanModel(Integer pihakPemohonId, Integer kategoriPemohonBantuanId, String namaPihak) {
        this.pihakPemohonId = pihakPemohonId;
        this.kategoriPemohonBantuanId = kategoriPemohonBantuanId;
        this.namaPihak = namaPihak;
    }

    public Integer getPihakPemohonId() {
        return pihakPemohonId;
    }

    public void setPihakPemohonId(Integer pihakPemohonId) {
        this.pihakPemohonId = pihakPemohonId;
    }

    public Integer getKategoriPemohonBantuanId() {
        return kategoriPemohonBantuanId;
    }

    public void setKategoriPemohonBantuanId(Integer kategoriPemohonBantuanId) {
        this.kategoriPemohonBantuanId = kategoriPemohonBantuanId;
    }

    public String getNamaPihak() {
        return namaPihak;
    }

    public void setNamaPihak(String namaPihak) {
        this.namaPihak = namaPihak;
    }
}
