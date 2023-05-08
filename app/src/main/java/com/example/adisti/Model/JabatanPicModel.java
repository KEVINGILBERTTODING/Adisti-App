package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JabatanPicModel implements Serializable {
    @SerializedName("jabatan_id")
    String jabatanId;
    @SerializedName("jabatan")
    String namaJabatan;

    public JabatanPicModel(String jabatanId, String namaJabatan) {
        this.jabatanId = jabatanId;
        this.namaJabatan = namaJabatan;
    }

    public String getJabatanId() {
        return jabatanId;
    }

    public void setJabatanId(String jabatanId) {
        this.jabatanId = jabatanId;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
    }
}
