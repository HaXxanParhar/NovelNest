package com.arfideveloper.novelnest.models;

public class SearchModel {
    private String bookName;

    public SearchModel() {
    }

    public SearchModel(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
