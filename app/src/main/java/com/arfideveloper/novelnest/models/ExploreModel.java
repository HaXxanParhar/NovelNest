package com.arfideveloper.novelnest.models;

public class ExploreModel {

    private int novel_image, novel_background, uploadTime;
    private String novelName;

    public ExploreModel() {
    }

    public ExploreModel(int novel_image, int novel_background, int uploadTime, String novelName) {
        this.novel_image = novel_image;
        this.novel_background = novel_background;
        this.uploadTime = uploadTime;
        this.novelName = novelName;
    }

    public int getNovel_image() {
        return novel_image;
    }

    public void setNovel_image(int novel_image) {
        this.novel_image = novel_image;
    }

    public int getNovel_background() {
        return novel_background;
    }

    public void setNovel_background(int novel_background) {
        this.novel_background = novel_background;
    }

    public int getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(int uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }
}
