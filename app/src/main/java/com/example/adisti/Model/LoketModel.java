package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoketModel implements Serializable {
    @SerializedName("loket_id")
    String loketId;
    @SerializedName("nama_loket")
    String namaLoket;



    public LoketModel(String loketId, String namaLoket) {
        this.loketId = loketId;
        this.namaLoket = namaLoket;
    }

    public String getLoketId() {
        return loketId;
    }

    public void setLoketId(String loketId) {
        this.loketId = loketId;
    }

    public String getNamaLoket() {
        return namaLoket;
    }

    public void setNamaLoket(String namaLoket) {
        this.namaLoket = namaLoket;
    }
}
