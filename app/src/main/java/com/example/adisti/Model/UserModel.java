package com.example.adisti.Model;

import com.example.adisti.Util.DataApi;
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
    @SerializedName("username")
    String username;
    @SerializedName("photo_profile")
    String photoProfile;
    @SerializedName("nama_lengkap")
    String namaLengkap;
    @SerializedName("jabatan")
    String jabatan;
    @SerializedName("file_qrcode")
    String fileQrCode;

    public UserModel(String userId, String nama, String kodeLoket, String namaLengkap, String jabatan, String fileQrCode, String role, String message, Integer code, String namaLoket, String username, String photoProfile) {
        this.userId = userId;
        this.nama = nama;
        this.kodeLoket = kodeLoket;
        this.role = role;
        this.message = message;
        this.code = code;
        this.namaLoket = namaLoket;
        this.username = username;
        this.photoProfile = photoProfile;
        this.namaLengkap = namaLengkap;
        this.jabatan = jabatan;
        this.fileQrCode = fileQrCode;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoProfile() {
        return DataApi.URL_PHOTO_PROFILE+photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getFileQrCode() {
        return DataApi.URL_QR_CODE + fileQrCode;
    }

    public void setFileQrCode(String fileQrCode) {
        this.fileQrCode = fileQrCode;
    }
}
