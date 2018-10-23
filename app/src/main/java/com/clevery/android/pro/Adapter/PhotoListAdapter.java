package com.clevery.android.pro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.clevery.android.pro.MainActivity;
import com.clevery.android.pro.Models.PhotoModel;
import com.clevery.android.pro.R;
import com.clevery.android.pro.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by Acer on 01/12/2016.
 */

public class PhotoListAdapter extends BaseAdapter
{
    ArrayList<PhotoModel> arrayList;
    MainActivity activity;

    PhotoListAdapter() {
        arrayList = null;
        activity = null;
    }
    public PhotoListAdapter(MainActivity _activity, ArrayList<PhotoModel> _arrayList) {
        arrayList = _arrayList;
        activity = _activity;
    }
    @Override
    public int getCount() {

        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PhotoModel model = arrayList.get(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.cell_photo, null);
        }
        ImageView img_frame = view.findViewById(R.id.img_frame);
        ImageView img_person = view.findViewById(R.id.img_person);
        TextView txt_type = view.findViewById(R.id.txt_type);
        TextView txt_size = view.findViewById(R.id.txt_size);
        TextView txt_amount = view.findViewById(R.id.txt_amount);
        TextView txt_date = view.findViewById(R.id.txt_date);
        TextView txt_price = view.findViewById(R.id.txt_price);
        TextView txt_received = view.findViewById(R.id.txt_received);
        Button btn_request = view.findViewById(R.id.btn_request);

//        Glide.with(context).load(Utils.decodeBase64(model.photo))
//                        .apply(new RequestOptions()
//                                .placeholder(R.drawable.person).centerCrop().dontAnimate()).into(img_person);
        img_frame.setImageResource(activity.myFrames[model.frame_index]);
        txt_type.setText(model.type);
        txt_size.setText("Size: " + model.width + " X " + model.height);
        txt_amount.setText("Amount: " + String.valueOf(model.amount));
        txt_date.setText(model.date);
        txt_price.setText("$" + String.valueOf(model.price));
        if (model.isDelivered) {
            txt_received.setVisibility(View.VISIBLE);
            btn_request.setVisibility(View.GONE);
        } else {
            txt_received.setVisibility(View.GONE);
            btn_request.setVisibility(View.VISIBLE);
        }
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Successfully requested!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
