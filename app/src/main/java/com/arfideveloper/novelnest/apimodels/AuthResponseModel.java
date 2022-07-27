package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class AuthResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private UserDataModel userDataModel;


    public AuthResponseModel(boolean status, String message, UserDataModel userDataModel) {
        this.status = status;
        this.message = message;
        this.userDataModel = userDataModel;
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

    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    public void setUserDataModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }
}
