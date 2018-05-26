package com.test.midasmobile9.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AdminMenuItem implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AdminMenuItem createFromParcel(Parcel src) {
            return new AdminMenuItem(src);
        }

        public AdminMenuItem[] newArray(int size) {
            return new AdminMenuItem[size];
        }
    };

    @SerializedName("no")
    private int no;
    @SerializedName("name")
    private String name;
    @SerializedName("info")
    private String info;
    @SerializedName("price")
    private int price;
    @SerializedName("img")
    private String img;
    @SerializedName("hotcold")
    private int hotcold;

    public AdminMenuItem(int no, String name, String info, int price, String img, int hotcold) {
        this.no = no;
        this.name = name;
        this.info = info;
        this.price = price;
        this.img = img;
        this.hotcold = hotcold;
    }

    public AdminMenuItem(Parcel src) {
        this.no = src.readInt();
        this.name = src.readString();
        this.info = src.readString();
        this.price = src.readInt();
        this.img = src.readString();
        this.hotcold = src.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no);
        dest.writeString(name);
        dest.writeString(info);
        dest.writeInt(price);
        dest.writeString(img);
        dest.writeInt(hotcold);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getHotcold() {
        return hotcold;
    }

    public void setHotcold(int hotcold) {
        this.hotcold = hotcold;
    }
}
