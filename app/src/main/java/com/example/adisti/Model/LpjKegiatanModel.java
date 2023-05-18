package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LpjKegiatanModel implements Serializable {

    @SerializedName("proposal_id")
    String proposalId;
    @SerializedName("foto_kegiatan")
    String fotoKegiatan;
    @SerializedName("lpj")
    String lpj;

    public LpjKegiatanModel(String proposalId, String fotoKegiatan, String lpj) {
        this.proposalId = proposalId;
        this.fotoKegiatan = fotoKegiatan;
        this.lpj = lpj;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getFotoKegiatan() {
        return fotoKegiatan;
    }

    public void setFotoKegiatan(String fotoKegiatan) {
        this.fotoKegiatan = fotoKegiatan;
    }

    public String getLpj() {
        return lpj;
    }

    public void setLpj(String lpj) {
        this.lpj = lpj;
    }
}
