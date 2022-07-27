package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouritesResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<NovelsModel> novelsList;


    public FavouritesResponseModel(boolean status, String message, List<NovelsModel> novelsList) {
        this.status = status;
        this.message = message;
        this.novelsList = novelsList;
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

    public List<NovelsModel> getNovelsList() {
        return novelsList;
    }

    public void setNovelsList(List<NovelsModel> novelsList) {
        this.novelsList = novelsList;
    }
}
