package com.clevery.android.pro.Models;

import java.io.Serializable;

/**
 * Created by Acer on 01/12/2016.
 */

public class AlbumModel implements Serializable {
    public int _id;
    public String type;
    public int photos;
    public float price;
    public String date;
    public boolean isDelivered;

    public AlbumModel(int _id, String type, float price, int photos, String date, boolean isDelivered)
    {
        this._id = _id;
        this.type = type;
        this.price = price;
        this.photos = photos;
        this.isDelivered = isDelivered;
        this.date = date;
    }
    public AlbumModel()
    {
        this._id = 0;
        this.type = "";
        this.price = 0;
        this.photos = 0;
        this.isDelivered = false;
        this.date = "";
    }
}
