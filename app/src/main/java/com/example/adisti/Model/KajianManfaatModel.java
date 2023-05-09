package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KajianManfaatModel implements Serializable {
    @SerializedName("proposal_id")
    private String proposalId;

    @SerializedName("no_urut_proposal")
    private String noUrutProposal;

    @SerializedName("latar_belakang_pengajuan")
    private String latarBelakangPengajuan;

    @SerializedName("kategori_pemohon_bantuan")
    private String kategoriPemohonBantuan;

    @SerializedName("pihak_penerima_bantuan")
    private String pihakPenerimaBantuan;

    @SerializedName("manfaat_penerima_bantuan")
    private String manfaatPenerimaBantuan;

    @SerializedName("bidang_manfaat_perusahaan")
    private String bidangManfaatPerusahaan;

    @SerializedName("indikator_manfaat_perusahaan")
    private String indikatorManfaatPerusahaan;

    @SerializedName("penjelasan_indikator")
    private String penjelasanIndikator;

    @SerializedName("pilar")
    private String pilar;

    @SerializedName("tpb")
    private String tpb;

    @SerializedName("ran")
    private String ran;

    @SerializedName("kode_loket")
    private String kodeLoket;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("nama_ran")
    String namaRan;


    public KajianManfaatModel(String proposalId, String namaRan, String noUrutProposal, String latarBelakangPengajuan, String kategoriPemohonBantuan, String pihakPenerimaBantuan, String manfaatPenerimaBantuan, String bidangManfaatPerusahaan, String indikatorManfaatPerusahaan, String penjelasanIndikator, String pilar, String tpb, String ran, String kodeLoket, String dateCreated) {
        this.proposalId = proposalId;
        this.noUrutProposal = noUrutProposal;
        this.latarBelakangPengajuan = latarBelakangPengajuan;
        this.kategoriPemohonBantuan = kategoriPemohonBantuan;
        this.pihakPenerimaBantuan = pihakPenerimaBantuan;
        this.manfaatPenerimaBantuan = manfaatPenerimaBantuan;
        this.bidangManfaatPerusahaan = bidangManfaatPerusahaan;
        this.indikatorManfaatPerusahaan = indikatorManfaatPerusahaan;
        this.penjelasanIndikator = penjelasanIndikator;
        this.pilar = pilar;
        this.namaRan = namaRan;
        this.tpb = tpb;
        this.ran = ran;
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

    public String getLatarBelakangPengajuan() {
        return latarBelakangPengajuan;
    }

    public void setLatarBelakangPengajuan(String latarBelakangPengajuan) {
        this.latarBelakangPengajuan = latarBelakangPengajuan;
    }

    public String getKategoriPemohonBantuan() {
        return kategoriPemohonBantuan;
    }

    public void setKategoriPemohonBantuan(String kategoriPemohonBantuan) {
        this.kategoriPemohonBantuan = kategoriPemohonBantuan;
    }

    public String getPihakPenerimaBantuan() {
        return pihakPenerimaBantuan;
    }

    public void setPihakPenerimaBantuan(String pihakPenerimaBantuan) {
        this.pihakPenerimaBantuan = pihakPenerimaBantuan;
    }

    public String getManfaatPenerimaBantuan() {
        return manfaatPenerimaBantuan;
    }

    public void setManfaatPenerimaBantuan(String manfaatPenerimaBantuan) {
        this.manfaatPenerimaBantuan = manfaatPenerimaBantuan;
    }

    public String getBidangManfaatPerusahaan() {
        return bidangManfaatPerusahaan;
    }

    public void setBidangManfaatPerusahaan(String bidangManfaatPerusahaan) {
        this.bidangManfaatPerusahaan = bidangManfaatPerusahaan;
    }

    public String getIndikatorManfaatPerusahaan() {
        return indikatorManfaatPerusahaan;
    }

    public void setIndikatorManfaatPerusahaan(String indikatorManfaatPerusahaan) {
        this.indikatorManfaatPerusahaan = indikatorManfaatPerusahaan;
    }

    public String getPenjelasanIndikator() {
        return penjelasanIndikator;
    }

    public void setPenjelasanIndikator(String penjelasanIndikator) {
        this.penjelasanIndikator = penjelasanIndikator;
    }

    public String getPilar() {
        return pilar;
    }

    public void setPilar(String pilar) {
        this.pilar = pilar;
    }

    public String getTpb() {
        return tpb;
    }

    public void setTpb(String tpb) {
        this.tpb = tpb;
    }

    public String getRan() {
        return ran;
    }

    public void setRan(String ran) {
        this.ran = ran;
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

    public String getNamaRan() {
        return namaRan;
    }

    public void setNamaRan(String namaRan) {
        this.namaRan = namaRan;
    }




}
