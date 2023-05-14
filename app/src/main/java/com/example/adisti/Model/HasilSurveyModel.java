package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HasilSurveyModel implements Serializable {
    @SerializedName("proposal_id")
    private String proposalId;

    @SerializedName("no_urut_proposal")
    private String noUrutProposal;

    @SerializedName("nama_petugas_survey")
    private String namaPetugasSurvey;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("kelayakan")
    private String kelayakan;

    @SerializedName("bentuk_bantuan")
    private String bentukBantuan;

    @SerializedName("nilai_pengajuan")
    private String nilaiPengajuan;

    @SerializedName("barang_diajukan")
    private String barangDiajukan;

    @SerializedName("kode_loket")
    private String kodeLoket;

    @SerializedName("date_created")
    private String dateCreated;


    public HasilSurveyModel(String proposalId, String noUrutProposal, String namaPetugasSurvey, String jabatan, String kelayakan, String bentukBantuan, String nilaiPengajuan, String barangDiajukan, String kodeLoket, String dateCreated) {
        this.proposalId = proposalId;
        this.noUrutProposal = noUrutProposal;
        this.namaPetugasSurvey = namaPetugasSurvey;
        this.jabatan = jabatan;
        this.kelayakan = kelayakan;
        this.bentukBantuan = bentukBantuan;
        this.nilaiPengajuan = nilaiPengajuan;
        this.barangDiajukan = barangDiajukan;
        this.kodeLoket = kodeLoket;
        this.dateCreated = dateCreated;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getNoUrutProposal() {
        return noUrutProposal;
    }

    public void setNoUrutProposal(String noUrutProposal) {
        this.noUrutProposal = noUrutProposal;
    }

    public String getNamaPetugasSurvey() {
        return namaPetugasSurvey;
    }

    public void setNamaPetugasSurvey(String namaPetugasSurvey) {
        this.namaPetugasSurvey = namaPetugasSurvey;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getKelayakan() {
        return kelayakan;
    }

    public void setKelayakan(String kelayakan) {
        this.kelayakan = kelayakan;
    }

    public String getBentukBantuan() {
        return bentukBantuan;
    }

    public void setBentukBantuan(String bentukBantuan) {
        this.bentukBantuan = bentukBantuan;
    }

    public String getNilaiPengajuan() {
        return nilaiPengajuan;
    }

    public void setNilaiPengajuan(String nilaiPengajuan) {
        this.nilaiPengajuan = nilaiPengajuan;
    }

    public String getBarangDiajukan() {
        return barangDiajukan;
    }

    public void setBarangDiajukan(String barangDiajukan) {
        this.barangDiajukan = barangDiajukan;
    }

    public String getKodeLoket() {
        return kodeLoket;
    }

    public void setKodeLoket(String kodeLoket) {
        this.kodeLoket = kodeLoket;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
