package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SurveyModel implements Serializable{
    @SerializedName("proposal_id")
    String proposalId;

    @SerializedName("no_urut_proposal")
    String noUrutProposal;

    @SerializedName("nama_petugas_survey")
    String namaPetugasSurvey;

    @SerializedName("jabatan_petugas_survey")
    String jabatanPetugasSurvey;

    @SerializedName("unit_kerja")
    String unitKerja;

    @SerializedName("ketua_panitia")
    String ketuaPanitia;

    @SerializedName("nama_panitia")
    String namaPanitia;

    @SerializedName("alamat_panitia")
    String alamatPanitia;

    @SerializedName("no_telp_panitia")
    String noTelpPanitia;

    @SerializedName("nama_bendahara")
    String namaBendahara;

    @SerializedName("alamat_bendahara")
    String alamatBendahara;

    @SerializedName("no_telp_bendahara")
    String noTelpBendahara;

    @SerializedName("sdk_1")
    String sdk1;

    @SerializedName("nominal_sdk_1")
    String nominalSdk1;

    @SerializedName("sdk_2")
    String sdk2;

    @SerializedName("nominal_sdk_2")
    String nominalSdk2;

    @SerializedName("sdk_3")
    String sdk3;

    @SerializedName("nominal_sdk_3")
    String nominalSdk3;

    @SerializedName("pk_1")
    String pk1;

    @SerializedName("nominal_pk_1")
    String nominalPk1;

    @SerializedName("pk_2")
    String pk2;

    @SerializedName("nominal_pk_2")
    String nominalPk2;

    @SerializedName("pk_3")
    String pk3;

    @SerializedName("nominal_pk_3")
    String nominalPk3;

    @SerializedName("file_ktp")
    String fileKtp;

    @SerializedName("format_ktp")
    String formatKtp;

    @SerializedName("file_butab")
    String fileButab;

    @SerializedName("format_butab")
    String formatButab;

    @SerializedName("file_foto_survey")
    String fileFotoSurvey;

    @SerializedName("format_foto_survey")
    String formatFotoSurvey;

    @SerializedName("kode_loket")
    String kodeLoket;

    @SerializedName("date_created")
    String dateCreated;

    public SurveyModel(String proposalId, String noUrutProposal, String namaPetugasSurvey, String jabatanPetugasSurvey, String unitKerja, String ketuaPanitia, String namaPanitia, String alamatPanitia, String noTelpPanitia, String namaBendahara, String alamatBendahara, String noTelpBendahara, String sdk1, String nominalSdk1, String sdk2, String nominalSdk2, String sdk3, String nominalSdk3, String pk1, String nominalPk1, String pk2, String nominalPk2, String pk3, String nominalPk3, String fileKtp, String formatKtp, String fileButab, String formatButab, String fileFotoSurvey, String formatFotoSurvey, String kodeLoket, String dateCreated) {
        this.proposalId = proposalId;
        this.noUrutProposal = noUrutProposal;
        this.namaPetugasSurvey = namaPetugasSurvey;
        this.jabatanPetugasSurvey = jabatanPetugasSurvey;
        this.unitKerja = unitKerja;
        this.ketuaPanitia = ketuaPanitia;
        this.namaPanitia = namaPanitia;
        this.alamatPanitia = alamatPanitia;
        this.noTelpPanitia = noTelpPanitia;
        this.namaBendahara = namaBendahara;
        this.alamatBendahara = alamatBendahara;
        this.noTelpBendahara = noTelpBendahara;
        this.sdk1 = sdk1;
        this.nominalSdk1 = nominalSdk1;
        this.sdk2 = sdk2;
        this.nominalSdk2 = nominalSdk2;
        this.sdk3 = sdk3;
        this.nominalSdk3 = nominalSdk3;
        this.pk1 = pk1;
        this.nominalPk1 = nominalPk1;
        this.pk2 = pk2;
        this.nominalPk2 = nominalPk2;
        this.pk3 = pk3;
        this.nominalPk3 = nominalPk3;
        this.fileKtp = fileKtp;
        this.formatKtp = formatKtp;
        this.fileButab = fileButab;
        this.formatButab = formatButab;
        this.fileFotoSurvey = fileFotoSurvey;
        this.formatFotoSurvey = formatFotoSurvey;
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

    public String getJabatanPetugasSurvey() {
        return jabatanPetugasSurvey;
    }

    public void setJabatanPetugasSurvey(String jabatanPetugasSurvey) {
        this.jabatanPetugasSurvey = jabatanPetugasSurvey;
    }

    public String getUnitKerja() {
        return unitKerja;
    }

    public void setUnitKerja(String unitKerja) {
        this.unitKerja = unitKerja;
    }

    public String getKetuaPanitia() {
        return ketuaPanitia;
    }

    public void setKetuaPanitia(String ketuaPanitia) {
        this.ketuaPanitia = ketuaPanitia;
    }

    public String getNamaPanitia() {
        return namaPanitia;
    }

    public void setNamaPanitia(String namaPanitia) {
        this.namaPanitia = namaPanitia;
    }

    public String getAlamatPanitia() {
        return alamatPanitia;
    }

    public void setAlamatPanitia(String alamatPanitia) {
        this.alamatPanitia = alamatPanitia;
    }

    public String getNoTelpPanitia() {
        return noTelpPanitia;
    }

    public void setNoTelpPanitia(String noTelpPanitia) {
        this.noTelpPanitia = noTelpPanitia;
    }

    public String getNamaBendahara() {
        return namaBendahara;
    }

    public void setNamaBendahara(String namaBendahara) {
        this.namaBendahara = namaBendahara;
    }

    public String getAlamatBendahara() {
        return alamatBendahara;
    }

    public void setAlamatBendahara(String alamatBendahara) {
        this.alamatBendahara = alamatBendahara;
    }

    public String getNoTelpBendahara() {
        return noTelpBendahara;
    }

    public void setNoTelpBendahara(String noTelpBendahara) {
        this.noTelpBendahara = noTelpBendahara;
    }

    public String getSdk1() {
        return sdk1;
    }

    public void setSdk1(String sdk1) {
        this.sdk1 = sdk1;
    }

    public String getNominalSdk1() {
        return nominalSdk1;
    }

    public void setNominalSdk1(String nominalSdk1) {
        this.nominalSdk1 = nominalSdk1;
    }

    public String getSdk2() {
        return sdk2;
    }

    public void setSdk2(String sdk2) {
        this.sdk2 = sdk2;
    }

    public String getNominalSdk2() {
        return nominalSdk2;
    }

    public void setNominalSdk2(String nominalSdk2) {
        this.nominalSdk2 = nominalSdk2;
    }

    public String getSdk3() {
        return sdk3;
    }

    public void setSdk3(String sdk3) {
        this.sdk3 = sdk3;
    }

    public String getNominalSdk3() {
        return nominalSdk3;
    }

    public void setNominalSdk3(String nominalSdk3) {
        this.nominalSdk3 = nominalSdk3;
    }

    public String getPk1() {
        return pk1;
    }

    public void setPk1(String pk1) {
        this.pk1 = pk1;
    }

    public String getNominalPk1() {
        return nominalPk1;
    }

    public void setNominalPk1(String nominalPk1) {
        this.nominalPk1 = nominalPk1;
    }

    public String getPk2() {
        return pk2;
    }

    public void setPk2(String pk2) {
        this.pk2 = pk2;
    }

    public String getNominalPk2() {
        return nominalPk2;
    }

    public void setNominalPk2(String nominalPk2) {
        this.nominalPk2 = nominalPk2;
    }

    public String getPk3() {
        return pk3;
    }

    public void setPk3(String pk3) {
        this.pk3 = pk3;
    }

    public String getNominalPk3() {
        return nominalPk3;
    }

    public void setNominalPk3(String nominalPk3) {
        this.nominalPk3 = nominalPk3;
    }

    public String getFileKtp() {
        return fileKtp;
    }

    public void setFileKtp(String fileKtp) {
        this.fileKtp = fileKtp;
    }

    public String getFormatKtp() {
        return formatKtp;
    }

    public void setFormatKtp(String formatKtp) {
        this.formatKtp = formatKtp;
    }

    public String getFileButab() {
        return fileButab;
    }

    public void setFileButab(String fileButab) {
        this.fileButab = fileButab;
    }

    public String getFormatButab() {
        return formatButab;
    }

    public void setFormatButab(String formatButab) {
        this.formatButab = formatButab;
    }

    public String getFileFotoSurvey() {
        return fileFotoSurvey;
    }

    public void setFileFotoSurvey(String fileFotoSurvey) {
        this.fileFotoSurvey = fileFotoSurvey;
    }

    public String getFormatFotoSurvey() {
        return formatFotoSurvey;
    }

    public void setFormatFotoSurvey(String formatFotoSurvey) {
        this.formatFotoSurvey = formatFotoSurvey;
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
