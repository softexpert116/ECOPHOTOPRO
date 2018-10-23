package com.clevery.android.pro.Models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Acer on 01/12/2016.
 */

public class PhotoModel implements Serializable {
    public int _id;
    public String type;
    public int frame_index;
    public String photo;
    public int amount;
    public String width;
    public String height;
    public float price;
    public String date;
    public boolean isDelivered;

    public PhotoModel(int _id, String photo, String type, int frame_index, String width, String height, float price, int amount, String date, boolean isDelivered)
    {
        this._id = _id;
        this.photo = photo;
        this.type = type;
        this.frame_index = frame_index;
        this.width = width;
        this.height = height;
        this.price = price;
        this.amount = amount;
        this.isDelivered = isDelivered;
        this.date = date;
    }
    public PhotoModel()
    {
        this._id = 0;
        this.photo = "";
        this.type = "";
        this.frame_index = 0;
        this.width = "";
        this.height = "";
        this.price = 0;
        this.amount = 0;
        this.isDelivered = false;
        this.date = "";
    }
}
