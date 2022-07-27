package com.arfideveloper.novelnest.models;

public class NovelDetailsModel {
    private int novelImage, likesCount;
    private String novelName, writerName;

    public NovelDetailsModel() {
    }

    public NovelDetailsModel(int novelImage, int likesCount, String novelName, String writerName) {
        this.novelImage = novelImage;
        this.likesCount = likesCount;
        this.novelName = novelName;
        this.writerName = writerName;
    }

    public int getNovelImage() {
        return novelImage;
    }

    public void setNovelImage(int novelImage) {
        this.novelImage = novelImage;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }
}
