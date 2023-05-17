package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RealisasiBantuanModel implements Serializable {

    @SerializedName("proposal_id")
    private String proposalId;

    @SerializedName("tanggal_kegiatan")
    private String tanggalKegiatan;

    @SerializedName("tempat_kegiatan")
    private String tempatKegiatan;

    @SerializedName("nominal_bantuan")
    private String nominalBantuan;

    @SerializedName("jenis_bantuan")
    private String jenisBantuan;

    @SerializedName("barang_berupa")
    private String barangBerupa;

    @SerializedName("foto_kegiatan")
    private String fotoKegiatan;

    @SerializedName("link_berita")
    private String linkBerita;

    @SerializedName("kuitansi")
    private String kuitansi;

    @SerializedName("bast")
    private String bast;

    @SerializedName("spt")
    private String spt;

    @SerializedName("erp")
    private String erp;

    @SerializedName("format_foto")
    private String formatFoto;

    @SerializedName("format_kuitansi")
    private String formatKuitansi;

    @SerializedName("format_bast")
    private String formatBast;

    @SerializedName("format_spt")
    private String formatSpt;

    @SerializedName("format_erp")
    private String formatErp;

    @SerializedName("date_created")
    private String dateCreated;

    public RealisasiBantuanModel(String proposalId, String tanggalKegiatan, String tempatKegiatan, String nominalBantuan, String jenisBantuan, String barangBerupa, String fotoKegiatan, String linkBerita, String kuitansi, String bast, String spt, String erp, String formatFoto, String formatKuitansi, String formatBast, String formatSpt, String formatErp, String dateCreated) {
        this.proposalId = proposalId;
        this.tanggalKegiatan = tanggalKegiatan;
        this.tempatKegiatan = tempatKegiatan;
        this.nominalBantuan = nominalBantuan;
        this.jenisBantuan = jenisBantuan;
        this.barangBerupa = barangBerupa;
        this.fotoKegiatan = fotoKegiatan;
        this.linkBerita = linkBerita;
        this.kuitansi = kuitansi;
        this.bast = bast;
        this.spt = spt;
        this.erp = erp;
        this.formatFoto = formatFoto;
        this.formatKuitansi = formatKuitansi;
        this.formatBast = formatBast;
        this.formatSpt = formatSpt;
        this.formatErp = formatErp;
        this.dateCreated = dateCreated;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getTanggalKegiatan() {
        return tanggalKegiatan;
    }

    public void setTanggalKegiatan(String tanggalKegiatan) {
        this.tanggalKegiatan = tanggalKegiatan;
    }

    public String getTempatKegiatan() {
        return tempatKegiatan;
    }

    public void setTempatKegiatan(String tempatKegiatan) {
        this.tempatKegiatan = tempatKegiatan;
    }

    public String getNominalBantuan() {
        return nominalBantuan;
    }

    public void setNominalBantuan(String nominalBantuan) {
        this.nominalBantuan = nominalBantuan;
    }

    public String getJenisBantuan() {
        return jenisBantuan;
    }

    public void setJenisBantuan(String jenisBantuan) {
        this.jenisBantuan = jenisBantuan;
    }

    public String getBarangBerupa() {
        return barangBerupa;
    }

    public void setBarangBerupa(String barangBerupa) {
        this.barangBerupa = barangBerupa;
    }

    public String getFotoKegiatan() {
        return fotoKegiatan;
    }

    public void setFotoKegiatan(String fotoKegiatan) {
        this.fotoKegiatan = fotoKegiatan;
    }

    public String getLinkBerita() {
        return linkBerita;
    }

    public void setLinkBerita(String linkBerita) {
        this.linkBerita = linkBerita;
    }

    public String getKuitansi() {
        return kuitansi;
    }

    public void setKuitansi(String kuitansi) {
        this.kuitansi = kuitansi;
    }

    public String getBast() {
        return bast;
    }

    public void setBast(String bast) {
        this.bast = bast;
    }

    public String getSpt() {
        return spt;
    }

    public void setSpt(String spt) {
        this.spt = spt;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }

    public String getFormatFoto() {
        return formatFoto;
    }

    public void setFormatFoto(String formatFoto) {
        this.formatFoto = formatFoto;
    }

    public String getFormatKuitansi() {
        return formatKuitansi;
    }

    public void setFormatKuitansi(String formatKuitansi) {
        this.formatKuitansi = formatKuitansi;
    }

    public String getFormatBast() {
        return formatBast;
    }

    public void setFormatBast(String formatBast) {
        this.formatBast = formatBast;
    }

    public String getFormatSpt() {
        return formatSpt;
    }

    public void setFormatSpt(String formatSpt) {
        this.formatSpt = formatSpt;
    }

    public String getFormatErp() {
        return formatErp;
    }

    public void setFormatErp(String formatErp) {
        this.formatErp = formatErp;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}