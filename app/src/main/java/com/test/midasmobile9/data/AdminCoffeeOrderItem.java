package com.test.midasmobile9.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AdminCoffeeOrderItem implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AdminCoffeeOrderItem createFromParcel(Parcel src) {
            return new AdminCoffeeOrderItem(src);
        }

        public AdminCoffeeOrderItem[] newArray(int size) {
            return new AdminCoffeeOrderItem[size];
        }
    };

    @SerializedName("no")
    private int no;
    @SerializedName("user_no")
    private int user_no;
    @SerializedName("menu_no")
    private int menu_no;
    @SerializedName("size")
    private int size;
    @SerializedName("hotcold")
    private int hotcold;
    @SerializedName("count")
    private int count;
    @SerializedName("price")
    private int price;
    @SerializedName("state")
    private int state;
    @SerializedName("date")
    private String date;
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
    @SerializedName("name")
    private String name;
    @SerializedName("info")
    private String info;
    @SerializedName("img")
    private String img;
    @SerializedName("enable")
    private int enable;

    public AdminCoffeeOrderItem(int no, int user_no, int menu_no, int size, int hotcold, int count, int price, int state, String date, String email, String nickname, String profileimg, String phone, String part, String name, String info, String img, int enable) {
        this.no = no;
        this.user_no = user_no;
        this.menu_no = menu_no;
        this.size = size;
        this.hotcold = hotcold;
        this.count = count;
        this.price = price;
        this.state = state;
        this.date = date;
        this.email = email;
        this.nickname = nickname;
        this.profileimg = profileimg;
        this.phone = phone;
        this.part = part;
        this.name = name;
        this.info = info;
        this.img = img;
        this.enable = enable;
    }

    public AdminCoffeeOrderItem(Parcel src) {
        this.no = src.readInt();
        this.user_no = src.readInt();
        this.menu_no = src.readInt();
        this.size = src.readInt();
        this.hotcold = src.readInt();
        this.count = src.readInt();
        this.price = src.readInt();
        this.state = src.readInt();
        this.date = src.readString();
        this.email = src.readString();
        this.nickname = src.readString();
        this.profileimg = src.readString();
        this.phone = src.readString();
        this.part = src.readString();
        this.name = src.readString();
        this.info = src.readString();
        this.img = src.readString();
        this.enable = src.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeInt(user_no);
        dest.writeInt(menu_no);
        dest.writeInt(size);
        dest.writeInt(hotcold);
        dest.writeInt(count);
        dest.writeInt(price);
        dest.writeInt(state);
        dest.writeString(date);
        dest.writeString(email);
        dest.writeString(nickname);
        dest.writeString(profileimg);
        dest.writeString(phone);
        dest.writeString(part);
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(img);
        dest.writeInt(enable);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }

    public int getMenu_no() {
        return menu_no;
    }

    public void setMenu_no(int menu_no) {
        this.menu_no = menu_no;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHotcold() {
        return hotcold;
    }

    public void setHotcold(int hotcold) {
        this.hotcold = hotcold;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
