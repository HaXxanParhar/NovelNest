package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class InboxDataModel {
    @SerializedName("id")
    private int id;
    @SerializedName("chat_group_id")
    private String chat_group_id;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("unread_messages")
    private int unread_messages;
    @SerializedName("chat_group_name")
    private String chat_group_name;
    @SerializedName("novel_image")
    private String novel_image;
    @SerializedName("last_message")
    private String last_message;
    @SerializedName("last_message_time")
    private String last_message_time;

    public InboxDataModel() {
    }

    public InboxDataModel(int id, String chat_group_id, String created_at, int unread_messages, String chat_group_name, String novel_image, String last_message, String last_message_time) {
        this.id = id;
        this.chat_group_id = chat_group_id;
        this.created_at = created_at;
        this.unread_messages = unread_messages;
        this.chat_group_name = chat_group_name;
        this.novel_image = novel_image;
        this.last_message = last_message;
        this.last_message_time = last_message_time;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getUnread_messages() {
        return unread_messages;
    }

    public void setUnread_messages(int unread_messages) {
        this.unread_messages = unread_messages;
    }

    public String getChat_group_name() {
        return chat_group_name;
    }

    public void setChat_group_name(String chat_group_name) {
        this.chat_group_name = chat_group_name;
    }

    public String getNovel_image() {
        return novel_image;
    }

    public void setNovel_image(String novel_image) {
        this.novel_image = novel_image;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(String last_message_time) {
        this.last_message_time = last_message_time;
    }

}
