package com.arfideveloper.novelnest.models;

public class ChatModel {
    private String text_message, text_time;
    public int messageType, receiverImage;


    public ChatModel(String text_message, String text_time, int messageType, int receiverImage) {
        this.text_message = text_message;
        this.text_time = text_time;
        this.messageType = messageType;
        this.receiverImage = receiverImage;
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

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(int receiverImage) {
        this.receiverImage = receiverImage;
    }
}
