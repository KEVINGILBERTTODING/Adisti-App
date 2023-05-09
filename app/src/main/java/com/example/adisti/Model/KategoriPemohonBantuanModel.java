package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KategoriPemohonBantuanModel implements Serializable{

    @SerializedName("pemohon_bantuan")
    String pemohonBantuan;
    @SerializedName("pemohon_bantuan_id")
    Integer pemohonBantuanId;

    public KategoriPemohonBantuanModel(String pemohonBantuan, Integer pemohonBantuanId) {
        this.pemohonBantuan = pemohonBantuan;
        this.pemohonBantuanId = pemohonBantuanId;
    }

    public String getPemohonBantuan() {
        return pemohonBantuan;
    }

    public void setPemohonBantuan(String pemohonBantuan) {
        this.pemohonBantuan = pemohonBantuan;
    }

    public Integer getPemohonBantuanId() {
        return pemohonBantuanId;
    }

    public void setPemohonBantuanId(Integer pemohonBantuanId) {
        this.pemohonBantuanId = pemohonBantuanId;
    }
}
