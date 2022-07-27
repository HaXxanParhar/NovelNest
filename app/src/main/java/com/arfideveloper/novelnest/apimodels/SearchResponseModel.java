package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    List<NovelsModel> novelsModelList;

    public SearchResponseModel() {
    }

    public SearchResponseModel(boolean status, String message, List<NovelsModel> novelsModelList) {
        this.status = status;
        this.message = message;
        this.novelsModelList = novelsModelList;
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

    public List<NovelsModel> getNovelsModelList() {
        return novelsModelList;
    }

    public void setNovelsModelList(List<NovelsModel> novelsModelList) {
        this.novelsModelList = novelsModelList;
    }
}
