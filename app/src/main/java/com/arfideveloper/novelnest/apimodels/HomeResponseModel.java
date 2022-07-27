package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponseModel {
    @SerializedName("novel_banners")
    List<NovelBannersModel> novelBannersModelList;
    @SerializedName("categories")
    List<CategoriesModel> categoriesModelList;
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("user_type")
    private String type;
    @SerializedName("is_verified")
    private String isVerified;
    @SerializedName("data")
    private NovelsPaginationDataModel novelsPaginationDataModel;

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

    public List<NovelBannersModel> getNovelBannersModelList() {
        return novelBannersModelList;
    }

    public void setNovelBannersModelList(List<NovelBannersModel> novelBannersModelList) {
        this.novelBannersModelList = novelBannersModelList;
    }

    public List<CategoriesModel> getCategoriesModelList() {
        return categoriesModelList;
    }

    public void setCategoriesModelList(List<CategoriesModel> categoriesModelList) {
        this.categoriesModelList = categoriesModelList;
    }

    public NovelsPaginationDataModel getNovelsPaginationDataModel() {
        return novelsPaginationDataModel;
    }

    public void setNovelsPaginationDataModel(NovelsPaginationDataModel novelsPaginationDataModel) {
        this.novelsPaginationDataModel = novelsPaginationDataModel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }
}
