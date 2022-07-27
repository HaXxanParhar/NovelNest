package com.arfideveloper.novelnest.apimodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NovelDetailsDataModel implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("novel_title")
    private String novel_title;
    @SerializedName("novel_banner")
    private String novel_banner;
    @SerializedName("novel_cover")
    private String novel_cover;
    @SerializedName("background_color")
    private String background_color;
    @SerializedName("novel_type")
    private String novel_type;
    @SerializedName("novel_text")
    private String novel_text;
    @SerializedName("novel_file")
    private String novel_file;
    @SerializedName("created_time")
    private String created_time;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("chat_group_joined")
    private boolean is_joined;

    public NovelDetailsDataModel(int id, String category_id, String novel_title, String novel_banner, String novel_cover, String background_color, String novel_type, String novel_text, String novel_file, String created_time, String category_name, boolean is_joined) {
        this.id = id;
        this.category_id = category_id;
        this.novel_title = novel_title;
        this.novel_banner = novel_banner;
        this.novel_cover = novel_cover;
        this.background_color = background_color;
        this.novel_type = novel_type;
        this.novel_text = novel_text;
        this.novel_file = novel_file;
        this.created_time = created_time;
        this.category_name = category_name;
        this.is_joined = is_joined;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getNovel_title() {
        return novel_title;
    }

    public void setNovel_title(String novel_title) {
        this.novel_title = novel_title;
    }

    public String getNovel_banner() {
        return novel_banner;
    }

    public void setNovel_banner(String novel_banner) {
        this.novel_banner = novel_banner;
    }

    public String getNovel_cover() {
        return novel_cover;
    }

    public void setNovel_cover(String novel_cover) {
        this.novel_cover = novel_cover;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getNovel_type() {
        return novel_type;
    }

    public void setNovel_type(String novel_type) {
        this.novel_type = novel_type;
    }

    public String getNovel_text() {
        return novel_text;
    }

    public void setNovel_text(String novel_text) {
        this.novel_text = novel_text;
    }

    public String getNovel_file() {
        return novel_file;
    }

    public void setNovel_file(String novel_file) {
        this.novel_file = novel_file;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean getIs_joined() {
        return is_joined;
    }

    public void setIs_joined(boolean is_joined) {
        this.is_joined = is_joined;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.category_id);
        dest.writeString(this.novel_title);
        dest.writeString(this.novel_banner);
        dest.writeString(this.novel_cover);
        dest.writeString(this.background_color);
        dest.writeString(this.novel_type);
        dest.writeString(this.novel_text);
        dest.writeString(this.novel_file);
        dest.writeString(this.created_time);
        dest.writeString(this.category_name);
        dest.writeByte(this.is_joined ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.category_id = source.readString();
        this.novel_title = source.readString();
        this.novel_banner = source.readString();
        this.novel_cover = source.readString();
        this.background_color = source.readString();
        this.novel_type = source.readString();
        this.novel_text = source.readString();
        this.novel_file = source.readString();
        this.created_time = source.readString();
        this.category_name = source.readString();
        this.is_joined = source.readByte() != 0;
    }

    protected NovelDetailsDataModel(Parcel in) {
        this.id = in.readInt();
        this.category_id = in.readString();
        this.novel_title = in.readString();
        this.novel_banner = in.readString();
        this.novel_cover = in.readString();
        this.background_color = in.readString();
        this.novel_type = in.readString();
        this.novel_text = in.readString();
        this.novel_file = in.readString();
        this.created_time = in.readString();
        this.category_name = in.readString();
        this.is_joined = in.readByte() != 0;
    }

    public static final Parcelable.Creator<NovelDetailsDataModel> CREATOR = new Parcelable.Creator<NovelDetailsDataModel>() {
        @Override
        public NovelDetailsDataModel createFromParcel(Parcel source) {
            return new NovelDetailsDataModel(source);
        }

        @Override
        public NovelDetailsDataModel[] newArray(int size) {
            return new NovelDetailsDataModel[size];
        }
    };
}
