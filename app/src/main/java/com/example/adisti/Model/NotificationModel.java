package com.example.adisti.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    @SerializedName("notif_id")
    String notifId;
    @SerializedName("user_id")
    String userId;
    @SerializedName("status")
    Integer status;
    @SerializedName("code")
    Integer code;
    @SerializedName("created_at")
    String date;
    @SerializedName("proposal_id")
    String proposalId;

    public NotificationModel(String notifId, String userId, Integer status, Integer code, String date, String proposalId) {
        this.notifId = notifId;
        this.userId = userId;
        this.status = status;
        this.code = code;
        this.date = date;
        this.proposalId = proposalId;
    }


    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }
}
