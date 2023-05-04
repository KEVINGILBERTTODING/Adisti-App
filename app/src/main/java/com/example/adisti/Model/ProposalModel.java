package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProposalModel implements Serializable {
    @SerializedName("proposal_id")
    String proposalId;
    @SerializedName("no_proposal")
    String noProposal;
    @SerializedName("no_urut_proposal")
    String noUrutProposal;
    @SerializedName("tgl_proposal")
    String tglProposal;
    @SerializedName("asal_proposal")
    String asalProposal;
    @SerializedName("bantuan_diajukan")
    String bantuanDiajukan;
    @SerializedName("nama_pihak")
    String namaPihak;
    @SerializedName("email_pengaju")
    String emailPengaju;
    @SerializedName("alamat_pihak")
    String alamatPihak;
    @SerializedName("no_telp_pihak")
    String noTelpPihak;
    @SerializedName("jabatan_pengaju")
    String jabatanPengaju;
    @SerializedName("loket")
    String loket;
    @SerializedName("nama_loket")
    String namaLoket;
    @SerializedName("jabatan_pic")
    String jabatanPic;
    @SerializedName("file_proposal")
    String fileProposal;
    @SerializedName("date_created")
    String dateCreated;
    @SerializedName("kode_loket")
    String kodeLoket;
    @SerializedName("progress")
    String progress;
    @SerializedName("kajian_manfaat")
    String kajianManfaat;
    @SerializedName("survey")
    String survey;
    @SerializedName("hasil_survey")
    String hasilSurvey;
    @SerializedName("pendapat_kasubag")
    String pendapatKasubag;
    @SerializedName("pendapat_kabag")
    String pendapatKabag;
    @SerializedName("pendapat_kacab")
    String pendapatKacab;
    @SerializedName("realisasi_bantuan")
    String realisasiBantuan;
    @SerializedName("lpj")
    String lpj;
    @SerializedName("evaluasi_kegiatan")
    String evaluasiKegiatan;
    @SerializedName("status")
    String status;
    @SerializedName("verified")
    String verified;

    public ProposalModel(String proposalId, String verified, String noProposal, String noUrutProposal, String tglProposal, String asalProposal, String bantuanDiajukan, String namaPihak, String emailPengaju, String alamatPihak, String noTelpPihak, String jabatanPengaju, String loket, String namaLoket, String jabatanPic, String fileProposal, String dateCreated, String kodeLoket, String progress, String kajianManfaat, String survey, String hasilSurvey, String pendapatKasubag, String pendapatKabag, String pendapatKacab, String realisasiBantuan, String lpj, String evaluasiKegiatan, String status) {
        this.proposalId = proposalId;
        this.noProposal = noProposal;
        this.noUrutProposal = noUrutProposal;
        this.tglProposal = tglProposal;
        this.asalProposal = asalProposal;
        this.bantuanDiajukan = bantuanDiajukan;
        this.namaPihak = namaPihak;
        this.emailPengaju = emailPengaju;
        this.alamatPihak = alamatPihak;
        this.noTelpPihak = noTelpPihak;
        this.jabatanPengaju = jabatanPengaju;
        this.loket = loket;
        this.namaLoket = namaLoket;
        this.jabatanPic = jabatanPic;
        this.fileProposal = fileProposal;
        this.dateCreated = dateCreated;
        this.kodeLoket = kodeLoket;
        this.progress = progress;
        this.verified = verified;
        this.kajianManfaat = kajianManfaat;
        this.survey = survey;
        this.hasilSurvey = hasilSurvey;
        this.pendapatKasubag = pendapatKasubag;
        this.pendapatKabag = pendapatKabag;
        this.pendapatKacab = pendapatKacab;
        this.realisasiBantuan = realisasiBantuan;
        this.lpj = lpj;
        this.evaluasiKegiatan = evaluasiKegiatan;
        this.status = status;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getNoProposal() {
        return noProposal;
    }

    public void setNoProposal(String noProposal) {
        this.noProposal = noProposal;
    }

    public String getNoUrutProposal() {
        return noUrutProposal;
    }

    public void setNoUrutProposal(String noUrutProposal) {
        this.noUrutProposal = noUrutProposal;
    }

    public String getTglProposal() {
        return tglProposal;
    }

    public void setTglProposal(String tglProposal) {
        this.tglProposal = tglProposal;
    }

    public String getAsalProposal() {
        return asalProposal;
    }

    public void setAsalProposal(String asalProposal) {
        this.asalProposal = asalProposal;
    }

    public String getBantuanDiajukan() {
        return bantuanDiajukan;
    }

    public void setBantuanDiajukan(String bantuanDiajukan) {
        this.bantuanDiajukan = bantuanDiajukan;
    }

    public String getNamaPihak() {
        return namaPihak;
    }

    public void setNamaPihak(String namaPihak) {
        this.namaPihak = namaPihak;
    }

    public String getEmailPengaju() {
        return emailPengaju;
    }

    public void setEmailPengaju(String emailPengaju) {
        this.emailPengaju = emailPengaju;
    }

    public String getAlamatPihak() {
        return alamatPihak;
    }

    public void setAlamatPihak(String alamatPihak) {
        this.alamatPihak = alamatPihak;
    }

    public String getNoTelpPihak() {
        return noTelpPihak;
    }

    public void setNoTelpPihak(String noTelpPihak) {
        this.noTelpPihak = noTelpPihak;
    }

    public String getJabatanPengaju() {
        return jabatanPengaju;
    }

    public void setJabatanPengaju(String jabatanPengaju) {
        this.jabatanPengaju = jabatanPengaju;
    }

    public String getLoket() {
        return loket;
    }

    public void setLoket(String loket) {
        this.loket = loket;
    }

    public String getNamaLoket() {
        return namaLoket;
    }

    public void setNamaLoket(String namaLoket) {
        this.namaLoket = namaLoket;
    }

    public String getJabatanPic() {
        return jabatanPic;
    }

    public void setJabatanPic(String jabatanPic) {
        this.jabatanPic = jabatanPic;
    }

    public String getFileProposal() {
        return fileProposal;
    }

    public void setFileProposal(String fileProposal) {
        this.fileProposal = fileProposal;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getKodeLoket() {
        return kodeLoket;
    }

    public void setKodeLoket(String kodeLoket) {
        this.kodeLoket = kodeLoket;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getKajianManfaat() {
        return kajianManfaat;
    }

    public void setKajianManfaat(String kajianManfaat) {
        this.kajianManfaat = kajianManfaat;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getHasilSurvey() {
        return hasilSurvey;
    }

    public void setHasilSurvey(String hasilSurvey) {
        this.hasilSurvey = hasilSurvey;
    }

    public String getPendapatKasubag() {
        return pendapatKasubag;
    }

    public void setPendapatKasubag(String pendapatKasubag) {
        this.pendapatKasubag = pendapatKasubag;
    }

    public String getPendapatKabag() {
        return pendapatKabag;
    }

    public void setPendapatKabag(String pendapatKabag) {
        this.pendapatKabag = pendapatKabag;
    }

    public String getPendapatKacab() {
        return pendapatKacab;
    }

    public void setPendapatKacab(String pendapatKacab) {
        this.pendapatKacab = pendapatKacab;
    }

    public String getRealisasiBantuan() {
        return realisasiBantuan;
    }

    public void setRealisasiBantuan(String realisasiBantuan) {
        this.realisasiBantuan = realisasiBantuan;
    }

    public String getLpj() {
        return lpj;
    }

    public void setLpj(String lpj) {
        this.lpj = lpj;
    }

    public String getEvaluasiKegiatan() {
        return evaluasiKegiatan;
    }

    public void setEvaluasiKegiatan(String evaluasiKegiatan) {
        this.evaluasiKegiatan = evaluasiKegiatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}
