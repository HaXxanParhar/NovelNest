package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InboxResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<InboxDataModel> inboxDataModel;

    public InboxResponseModel() {
    }

    public InboxResponseModel(boolean status, String message, List<InboxDataModel> inboxDataModel) {
        this.status = status;
        this.message = message;
        this.inboxDataModel = inboxDataModel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InboxDataModel> getInboxDataModel() {
        return inboxDataModel;
    }

    public void setInboxDataModel(List<InboxDataModel> inboxDataModel) {
        this.inboxDataModel = inboxDataModel;
    }
}
