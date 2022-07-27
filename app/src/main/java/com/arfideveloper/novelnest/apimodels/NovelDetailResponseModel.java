package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class NovelDetailResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private NovelDetailsDataModel novelDetailsDataModel;

    public NovelDetailResponseModel() {
    }

    public NovelDetailResponseModel(boolean status, String message, NovelDetailsDataModel novelDetailsDataModel) {
        this.status = status;
        this.message = message;
        this.novelDetailsDataModel = novelDetailsDataModel;
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

    public NovelDetailsDataModel getNovelDetailsDataModel() {
        return novelDetailsDataModel;
    }

    public void setNovelDetailsDataModel(NovelDetailsDataModel novelDetailsDataModel) {
        this.novelDetailsDataModel = novelDetailsDataModel;
    }
}
