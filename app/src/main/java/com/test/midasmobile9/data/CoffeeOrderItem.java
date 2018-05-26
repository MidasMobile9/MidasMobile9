package com.test.midasmobile9.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CoffeeOrderItem implements Parcelable{

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

    protected CoffeeOrderItem(Parcel in) {
        no = in.readInt();
        user_no = in.readInt();
        menu_no = in.readInt();
        size = in.readInt();
        hotcold = in.readInt();
        count = in.readInt();
        price = in.readInt();
        state = in.readInt();
        date = in.readString();
    }

    public static final Creator<CoffeeOrderItem> CREATOR = new Creator<CoffeeOrderItem>() {
        @Override
        public CoffeeOrderItem createFromParcel(Parcel in) {
            return new CoffeeOrderItem(in);
        }

        @Override
        public CoffeeOrderItem[] newArray(int size) {
            return new CoffeeOrderItem[size];
        }
    };

    public CoffeeOrderItem(int no, int user_no, int menu_no, int size, int hotcold, int count, int price, int state, String date) {
        this.no = no;
        this.user_no = user_no;
        this.menu_no = menu_no;
        this.size = size;
        this.hotcold = hotcold;
        this.count = count;
        this.price = price;
        this.state = state;
        this.date = date;
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
}
