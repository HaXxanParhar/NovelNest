package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class ForgotResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ForgotPasswordDataModel forgotPasswordDataModel;


    public ForgotResponseModel(boolean status, String message, ForgotPasswordDataModel forgotPasswordDataModel) {
        this.status = status;
        this.message = message;
        this.forgotPasswordDataModel = forgotPasswordDataModel;
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

    public ForgotPasswordDataModel getForgotPasswordDataModel() {
        return forgotPasswordDataModel;
    }

    public void setForgotPasswordDataModel(ForgotPasswordDataModel forgotPasswordDataModel) {
        this.forgotPasswordDataModel = forgotPasswordDataModel;
    }
}
