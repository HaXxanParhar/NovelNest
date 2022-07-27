package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class NovelBannersModel {
    @SerializedName("id")
    private int id;
    @SerializedName("novel_banner")
    private String novel_banner;

    public NovelBannersModel() {
    }

    public NovelBannersModel(int id, String novel_banner) {
        this.id = id;
        this.novel_banner = novel_banner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNovel_banner() {
        return novel_banner;
    }

    public void setNovel_banner(String novel_banner) {
        this.novel_banner = novel_banner;
    }
}
