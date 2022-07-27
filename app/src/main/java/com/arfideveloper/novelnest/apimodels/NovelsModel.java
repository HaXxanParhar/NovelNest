package com.arfideveloper.novelnest.apimodels;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class NovelsModel implements Parcelable {
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
    @SerializedName("novel_text")
    private String novel_text;
    @SerializedName("created_time")
    private String created_time;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("chat_group_joined")
    private boolean chat_group_joined;
    @SerializedName("is_favourite")
    private String is_favourite;

    public NovelsModel() {
    }

    public NovelsModel(int id, String category_id, String novel_title, String novel_banner, String novel_cover, String background_color, String novel_text, String created_time, String created_at, boolean chat_group_joined, String is_favourite) {
        this.id = id;
        this.category_id = category_id;
        this.novel_title = novel_title;
        this.novel_banner = novel_banner;
        this.novel_cover = novel_cover;
        this.background_color = background_color;
        this.novel_text = novel_text;
        this.created_time = created_time;
        this.created_at = created_at;
        this.chat_group_joined = chat_group_joined;
        this.is_favourite = is_favourite;
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

    public String getNovel_text() {
        return novel_text;
    }

    public void setNovel_text(String novel_text) {
        this.novel_text = novel_text;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isChat_group_joined() {
        return chat_group_joined;
    }

    public void setChat_group_joined(boolean chat_group_joined) {
        this.chat_group_joined = chat_group_joined;
    }

    public String getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(String is_favourite) {
        this.is_favourite = is_favourite;
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
        dest.writeString(this.novel_text);
        dest.writeString(this.created_time);
        dest.writeString(this.created_at);
        dest.writeByte(this.chat_group_joined ? (byte) 1 : (byte) 0);
        dest.writeString(this.is_favourite);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.category_id = source.readString();
        this.novel_title = source.readString();
        this.novel_banner = source.readString();
        this.novel_cover = source.readString();
        this.background_color = source.readString();
        this.novel_text = source.readString();
        this.created_time = source.readString();
        this.created_at = source.readString();
        this.chat_group_joined = source.readByte() != 0;
        this.is_favourite = source.readString();
    }

    protected NovelsModel(Parcel in) {
        this.id = in.readInt();
        this.category_id = in.readString();
        this.novel_title = in.readString();
        this.novel_banner = in.readString();
        this.novel_cover = in.readString();
        this.background_color = in.readString();
        this.novel_text = in.readString();
        this.created_time = in.readString();
        this.created_at = in.readString();
        this.chat_group_joined = in.readByte() != 0;
        this.is_favourite = in.readString();
    }

    public static final Creator<NovelsModel> CREATOR = new Creator<NovelsModel>() {
        @Override
        public NovelsModel createFromParcel(Parcel source) {
            return new NovelsModel(source);
        }

        @Override
        public NovelsModel[] newArray(int size) {
            return new NovelsModel[size];
        }
    };
}
