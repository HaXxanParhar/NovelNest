package com.arfideveloper.novelnest.models;

public class HomeNovelDetailsModel {

    private int novelImage, profileImage, uploadTime;
    private String novelName, profileName;
    private boolean likesImage;


    public HomeNovelDetailsModel() {
    }

    public HomeNovelDetailsModel(int novelImage, int profileImage, int uploadTime, String novelName, String profileName, boolean likesImage) {
        this.novelImage = novelImage;
        this.profileImage = profileImage;
        this.uploadTime = uploadTime;
        this.novelName = novelName;
        this.profileName = profileName;
        this.likesImage = likesImage;
    }

    public int getNovelImage() {
        return novelImage;
    }

    public void setNovelImage(int novelImage) {
        this.novelImage = novelImage;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public boolean isLikesImage() {
        return likesImage;
    }

    public void setLikesImage(boolean likesImage) {
        this.likesImage = likesImage;
    }
}
