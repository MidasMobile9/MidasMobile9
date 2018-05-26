package com.test.midasmobile9.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
    public static final Creator CREATOR = new Creator() {
        public User createFromParcel(Parcel src) {
            return new User(src);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @SerializedName("no")
    private int no;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("profileimg")
    private String profileimg;
    @SerializedName("phone")
    private String phone;
    @SerializedName("part")
    private String part;
    @SerializedName("root")
    private int root;
    @SerializedName("token")
    private String token;

    public User() { };

    public User(int no, String email, String password, String nickname, String profileimg, String phone, String part, int root, String token) {
        this.no = no;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileimg = profileimg;
        this.phone = phone;
        this.part = part;
        this.root = root;
        this.token = token;
    }

    public User(Parcel src) {
        this.no = src.readInt();
        this.email = src.readString();
        this.password = src.readString();
        this.nickname = src.readString();
        this.profileimg = src.readString();
        this.phone = src.readString();
        this.part = src.readString();
        this.root = src.readInt();
        this.token = src.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(nickname);
        dest.writeString(profileimg);
        dest.writeString(phone);
        dest.writeString(part);
        dest.writeInt(root);
        dest.writeString(token);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
