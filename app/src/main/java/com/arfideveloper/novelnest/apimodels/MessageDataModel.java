package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class MessageDataModel {
    @SerializedName("id")
    private int id;
    @SerializedName("chat_group_id")
    private String chat_group_id;
    @SerializedName("text")
    private String text;
    @SerializedName("media")
    private String media;
    @SerializedName("media_type")
    private String media_type;
    @SerializedName("time")
    private String time;
    @SerializedName("sender_id")
    private int sender_id;
    @SerializedName("sender_name")
    private String sender_name;
    @SerializedName("sender_image")
    private String sender_image;
    @SerializedName("chat_group_name")
    private String chat_group_name;

    public MessageDataModel() {
    }

    public MessageDataModel(int id, String chat_group_id, String text, String media, String media_type, String time, int sender_id, String sender_name, String sender_image, String chat_group_name) {
        this.id = id;
        this.chat_group_id = chat_group_id;
        this.text = text;
        this.media = media;
        this.media_type = media_type;
        this.time = time;
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.sender_image = sender_image;
        this.chat_group_name = chat_group_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChat_group_id() {
        return chat_group_id;
    }

    public void setChat_group_id(String chat_group_id) {
        this.chat_group_id = chat_group_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_image() {
        return sender_image;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }

    public String getChat_group_name() {
        return chat_group_name;
    }

    public void setChat_group_name(String chat_group_name) {
        this.chat_group_name = chat_group_name;
    }
}
