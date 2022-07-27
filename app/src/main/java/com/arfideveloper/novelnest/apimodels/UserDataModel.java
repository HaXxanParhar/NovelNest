package com.arfideveloper.novelnest.apimodels;

import com.google.gson.annotations.SerializedName;

public class    UserDataModel {
    @SerializedName("id")
    private int id;
    @SerializedName("facebook_id")
    private String facebook_id;
    @SerializedName("google_id")
    private String google_id;
    @SerializedName("name")
    private String name;
    @SerializedName("pen_name")
    private String pen_name;
    @SerializedName("email")
    private String email;
    @SerializedName("email_verified_at")
    private String email_verified_at;
    @SerializedName("profile_image")
    private String profile_image;
    @SerializedName("bio")
    private String bio;
    @SerializedName("gender")
    private String gender;
    @SerializedName("date_of_birth")
    private String date_of_birth;
    @SerializedName("portfolio_url")
    private String portfolio_url;
    @SerializedName("cnic_front_image")
    private String cnic_front_image;
    @SerializedName("cnic_back_image")
    private String cnic_back_image;
    @SerializedName("average_rating")
    private String average_rating;
    @SerializedName("is_verified")
    private String is_verified;
    @SerializedName("type")
    private String type;
    @SerializedName("token")
    private String token;

    public UserDataModel() {
    }

    public UserDataModel(int id, String facebook_id, String google_id, String name, String pen_name, String email, String email_verified_at, String profile_image, String bio, String gender, String date_of_birth, String portfolio_url, String cnic_front_image, String cnic_back_image, String average_rating, String is_verified, String type, String token) {
        this.id = id;
        this.facebook_id = facebook_id;
        this.google_id = google_id;
        this.name = name;
        this.pen_name = pen_name;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.profile_image = profile_image;
        this.bio = bio;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.portfolio_url = portfolio_url;
        this.cnic_front_image = cnic_front_image;
        this.cnic_back_image = cnic_back_image;
        this.average_rating = average_rating;
        this.is_verified = is_verified;
        this.type = type;
        this.token = token;



    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPen_name() {
        return pen_name;
    }

    public void setPen_name(String pen_name) {
        this.pen_name = pen_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPortfolio_url() {
        return portfolio_url;
    }

    public void setPortfolio_url(String portfolio_url) {
        this.portfolio_url = portfolio_url;
    }

    public String getCnic_front_image() {
        return cnic_front_image;
    }

    public void setCnic_front_image(String cnic_front_image) {
        this.cnic_front_image = cnic_front_image;
    }

    public String getCnic_back_image() {
        return cnic_back_image;
    }

    public void setCnic_back_image(String cnic_back_image) {
        this.cnic_back_image = cnic_back_image;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}