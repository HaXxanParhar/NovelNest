package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeDataModel {
    @SerializedName("novel_banners")
    List<NovelBannersModel> novelBannersModelList;
    @SerializedName("categories")
    List<CategoriesModel> categoriesModelList;
    @SerializedName("novels")
    private NovelsPaginationDataModel novelsPaginationDataModel;

    public HomeDataModel() {
    }

    public HomeDataModel(List<NovelBannersModel> novelBannersModelList, List<CategoriesModel> categoriesModelList, NovelsPaginationDataModel novelsPaginationDataModel) {
        this.novelBannersModelList = novelBannersModelList;
        this.categoriesModelList = categoriesModelList;
        this.novelsPaginationDataModel = novelsPaginationDataModel;
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
}
