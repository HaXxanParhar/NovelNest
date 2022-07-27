package com.arfideveloper.novelnest.models;

public class InboxModel {
    private int profile_image;
    private String profile_name, text_message, text_time;


    public InboxModel(int profile_image, String profile_name, String text_message, String text_time) {
        this.profile_image = profile_image;
        this.profile_name = profile_name;
        this.text_message = text_message;
        this.text_time = text_time;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getText_time() {
        return text_time;
    }

    public void setText_time(String text_time) {
        this.text_time = text_time;
    }
}
