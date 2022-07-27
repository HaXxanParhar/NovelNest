package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class ForgotPasswordDataModel {
    @SerializedName("email")
    private String email;
    @SerializedName("verification_code")
    private String verification_code;

    public ForgotPasswordDataModel() {
    }

    public ForgotPasswordDataModel(String email, String verification_code) {
        this.email = email;
        this.verification_code = verification_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }
}
