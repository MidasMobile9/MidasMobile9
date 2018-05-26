package com.test.midasmobile9.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CustomerInfoItem implements Parcelable  {
    public static final Creator CREATOR = new Creator() {
        public CustomerInfoItem createFromParcel(Parcel src) {
            return new CustomerInfoItem(src);
        }

        public CustomerInfoItem[] newArray(int size) {
            return new CustomerInfoItem[size];
        }
    };

    @SerializedName("no")
    private int no;
    @SerializedName("email")
    private String email;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("profileimg")
    private String profileimg;
    @SerializedName("phone")
    private String phone;
    @SerializedName("part")
    private String part;

    public CustomerInfoItem(int no, String email, String nickname, String profileimg, String phone, String part) {
        this.no = no;
        this.email = email;
        this.nickname = nickname;
        this.profileimg = profileimg;
        this.phone = phone;
        this.part = part;
    }

    public CustomerInfoItem(Parcel src) {
        this.no = src.readInt();
        this.email = src.readString();
        this.nickname = src.readString();
        this.profileimg = src.readString();
        this.phone = src.readString();
        this.part = src.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeString(email);
        dest.writeString(nickname);
        dest.writeString(profileimg);
        dest.writeString(phone);
        dest.writeString(part);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
