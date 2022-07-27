package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserMessagesResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<MessageDataModel> messagesList;

    public UserMessagesResponseModel() {

    }

    public UserMessagesResponseModel(boolean status, String message, List<MessageDataModel> messagesList) {
        this.status = status;
        this.message = message;
        this.messagesList = messagesList;
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

    public List<MessageDataModel> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<MessageDataModel> messagesList) {
        this.messagesList = messagesList;
    }

}
