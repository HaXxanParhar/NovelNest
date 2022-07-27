package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class CategoryResponseModel {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private NovelsPaginationDataModel novelsPaginationDataModel;

    public CategoryResponseModel() {
    }

    public CategoryResponseModel(boolean status, String message, NovelsPaginationDataModel novelsPaginationDataModel) {
        this.status = status;
        this.message = message;
        this.novelsPaginationDataModel = novelsPaginationDataModel;
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

    public NovelsPaginationDataModel getNovelsPaginationDataModel() {
        return novelsPaginationDataModel;
    }

    public void setNovelsPaginationDataModel(NovelsPaginationDataModel novelsPaginationDataModel) {
        this.novelsPaginationDataModel = novelsPaginationDataModel;
    }
}
