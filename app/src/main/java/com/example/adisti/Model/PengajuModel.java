package com.example.adisti.Model;

import com.example.adisti.Util.DataApi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PengajuModel implements Serializable {
    @SerializedName("user_id")
    String userId;
    @SerializedName("username")
    String userName;
    @SerializedName("nama_lengkap")
    String namaLengkap;
    @SerializedName("email")
    String email;
    @SerializedName("user_status")
    Integer userStatus;
    @SerializedName("role")
    String role;
    @SerializedName("photo_profile")
    String photoProfile;
    @SerializedName("no_telepon")
    String noTelp;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("instansi")
    String instansi;
    @SerializedName("jabatan")
    String jabatan;

    public PengajuModel(String userId, String namaLengkap, String userName, String email, Integer userStatus, String role, String photoProfile, String noTelp, String alamat, String instansi, String jabatan) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.userStatus = userStatus;
        this.role = role;
        this.photoProfile = photoProfile;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.instansi = instansi;
        this.jabatan = jabatan;
        this.namaLengkap = namaLengkap;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoProfile() {
        return DataApi.URL_PHOTO_PROFILE + photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}


