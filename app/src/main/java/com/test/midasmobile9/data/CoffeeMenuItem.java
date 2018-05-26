package com.test.midasmobile9.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CoffeeMenuItem implements Parcelable {

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
    @SerializedName("enable")
    private int enable;

    protected CoffeeMenuItem(Parcel in) {
        no = in.readInt();
        name = in.readString();
        info = in.readString();
        price = in.readInt();
        img = in.readString();
        hotcold = in.readInt();
        enable = in.readInt();
    }

    public static final Creator<CoffeeMenuItem> CREATOR = new Creator<CoffeeMenuItem>() {
        @Override
        public CoffeeMenuItem createFromParcel(Parcel in) {
            return new CoffeeMenuItem(in);
        }

        @Override
        public CoffeeMenuItem[] newArray(int size) {
            return new CoffeeMenuItem[size];
        }
    };

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
        dest.writeInt(enable);
    }

    public CoffeeMenuItem(int no, String name, String info, int price, String img, int hotcold, int enable) {
        this.no = no;
        this.name = name;
        this.info = info;
        this.price = price;
        this.img = img;
        this.hotcold = hotcold;
        this.enable = enable;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}