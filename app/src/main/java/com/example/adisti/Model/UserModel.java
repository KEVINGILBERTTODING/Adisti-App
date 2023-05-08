package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {
    @SerializedName("user_id")
    String userId;
    @SerializedName("nama")
    String nama;
    @SerializedName("kode_loket")
    String kodeLoket;
    @SerializedName("role")
    String role;
    @SerializedName("message")
    String message;
    @SerializedName("code")
    Integer code;
    @SerializedName("nama_loket")
    String namaLoket;

    public UserModel(String userId, String nama, String namaLoket, String kodeLoket, String role, String message, Integer code) {
        this.userId = userId;
        this.nama = nama;
        this.kodeLoket = kodeLoket;
        this.role = role;
        this.message = message;
        this.code = code;
        this.namaLoket = namaLoket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKodeLoket() {
        return kodeLoket;
    }

    public void setKodeLoket(String kodeLoket) {
        this.kodeLoket = kodeLoket;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getNamaLoket() {
        return namaLoket;
    }

    public void setNamaLoket(String namaLoket) {
        this.namaLoket = namaLoket;
    }
}
