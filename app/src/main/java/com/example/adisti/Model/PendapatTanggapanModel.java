package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PendapatTanggapanModel implements Serializable {
    @SerializedName("proposal_id")
    String proposalId;
    @SerializedName("nama_kasubag")
    String namaKasubag;
    @SerializedName("jabatan_kasubag")
    String jabatanKasubag;
    @SerializedName("pendapat_kasubag")
    String pendapatKasubag;
    @SerializedName("nilai_pengajuan_kasubag")
    String nilaiPengajuanKasubag;
    @SerializedName("nama_kabag")
    String namaKabag;
    @SerializedName("jabatan_kabag")
    String jabatanKabag;
    @SerializedName("pendapat_kabag")
    String pendapatKabag;
    @SerializedName("nilai_pengajuan_kabag")
    String nilaiPengajuanKabag;
    @SerializedName("nama_kacab")
    String namaKacab;
    @SerializedName("jabatan_kacab")
    String jabatanKacab;
    @SerializedName("pendapat_kacab")
    String pendapatKacab;
    @SerializedName("tanggapan_kacab")
    String tanggapanKacab;
    @SerializedName("nilai_pengajuan_kacab")
    String nilaiPengajuanKacab;
    @SerializedName("date_created")
    String dateCreated;

    public PendapatTanggapanModel(String proposalId, String namaKasubag, String jabatanKasubag, String pendapatKasubag, String nilaiPengajuanKasubag, String namaKabag, String jabatanKabag, String pendapatKabag, String nilaiPengajuanKabag, String namaKacab, String jabatanKacab, String pendapatKacab, String tanggapanKacab, String nilaiPengajuanKacab, String dateCreated) {
        this.proposalId = proposalId;
        this.namaKasubag = namaKasubag;
        this.jabatanKasubag = jabatanKasubag;
        this.pendapatKasubag = pendapatKasubag;
        this.nilaiPengajuanKasubag = nilaiPengajuanKasubag;
        this.namaKabag = namaKabag;
        this.jabatanKabag = jabatanKabag;
        this.pendapatKabag = pendapatKabag;
        this.nilaiPengajuanKabag = nilaiPengajuanKabag;
        this.namaKacab = namaKacab;
        this.jabatanKacab = jabatanKacab;
        this.pendapatKacab = pendapatKacab;
        this.tanggapanKacab = tanggapanKacab;
        this.nilaiPengajuanKacab = nilaiPengajuanKacab;
        this.dateCreated = dateCreated;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getNamaKasubag() {
        return namaKasubag;
    }

    public void setNamaKasubag(String namaKasubag) {
        this.namaKasubag = namaKasubag;
    }

    public String getJabatanKasubag() {
        return jabatanKasubag;
    }

    public void setJabatanKasubag(String jabatanKasubag) {
        this.jabatanKasubag = jabatanKasubag;
    }

    public String getPendapatKasubag() {
        return pendapatKasubag;
    }

    public void setPendapatKasubag(String pendapatKasubag) {
        this.pendapatKasubag = pendapatKasubag;
    }

    public String getNilaiPengajuanKasubag() {
        return nilaiPengajuanKasubag;
    }

    public void setNilaiPengajuanKasubag(String nilaiPengajuanKasubag) {
        this.nilaiPengajuanKasubag = nilaiPengajuanKasubag;
    }

    public String getNamaKabag() {
        return namaKabag;
    }

    public void setNamaKabag(String namaKabag) {
        this.namaKabag = namaKabag;
    }

    public String getJabatanKabag() {
        return jabatanKabag;
    }

    public void setJabatanKabag(String jabatanKabag) {
        this.jabatanKabag = jabatanKabag;
    }

    public String getPendapatKabag() {
        return pendapatKabag;
    }

    public void setPendapatKabag(String pendapatKabag) {
        this.pendapatKabag = pendapatKabag;
    }

    public String getNilaiPengajuanKabag() {
        return nilaiPengajuanKabag;
    }

    public void setNilaiPengajuanKabag(String nilaiPengajuanKabag) {
        this.nilaiPengajuanKabag = nilaiPengajuanKabag;
    }

    public String getNamaKacab() {
        return namaKacab;
    }

    public void setNamaKacab(String namaKacab) {
        this.namaKacab = namaKacab;
    }

    public String getJabatanKacab() {
        return jabatanKacab;
    }

    public void setJabatanKacab(String jabatanKacab) {
        this.jabatanKacab = jabatanKacab;
    }

    public String getPendapatKacab() {
        return pendapatKacab;
    }

    public void setPendapatKacab(String pendapatKacab) {
        this.pendapatKacab = pendapatKacab;
    }

    public String getTanggapanKacab() {
        return tanggapanKacab;
    }

    public void setTanggapanKacab(String tanggapanKacab) {
        this.tanggapanKacab = tanggapanKacab;
    }

    public String getNilaiPengajuanKacab() {
        return nilaiPengajuanKacab;
    }

    public void setNilaiPengajuanKacab(String nilaiPengajuanKacab) {
        this.nilaiPengajuanKacab = nilaiPengajuanKacab;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
