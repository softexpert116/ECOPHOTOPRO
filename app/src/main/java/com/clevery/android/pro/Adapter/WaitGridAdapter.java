package com.clevery.android.pro.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.clevery.android.pro.App;
import com.clevery.android.pro.Fragments.WaitingFragment;
import com.clevery.android.pro.MainActivity;
import com.clevery.android.pro.Models.PhotoModel;
import com.clevery.android.pro.PhotoViewerActivity;
import com.clevery.android.pro.R;
import com.clevery.android.pro.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by Acer on 01/12/2016.
 */

public class WaitGridAdapter extends BaseAdapter
{
    ArrayList<PhotoModel> arrayList;
    MainActivity activity;
    WaitingFragment fragment;

    WaitGridAdapter() {
        arrayList = null;
        activity = null;
    }
    public WaitGridAdapter(MainActivity _activity, ArrayList<PhotoModel> _arrayList, WaitingFragment _fragment) {
        arrayList = _arrayList;
        activity = _activity;
        fragment = _fragment;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final PhotoModel model = arrayList.get(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.cell_grid, null);
        }
        Button btn_delete = (Button)view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Do you want to remove this item?");
                builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        arrayList.remove(i);
                        App.setPreference_waiting_array(arrayList);
                        fragment.refreshGridView();
                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ImageView img_frame = view.findViewById(R.id.img_frame);
        ImageView img_person = view.findViewById(R.id.img_person);
        TextView txt_type = view.findViewById(R.id.txt_type);
        TextView txt_size = view.findViewById(R.id.txt_size);
        TextView txt_amount = view.findViewById(R.id.txt_amount);
        TextView txt_price = view.findViewById(R.id.txt_price);
//        Glide.with(context).load(model.frame)
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.default_pic).centerInside().dontAnimate()).into(img_frame);
        Glide.with(activity).load(Uri.parse(model.photo))
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.person).centerCrop().dontAnimate()).into(img_person);
        img_frame.setImageResource(activity.myFrames[model.frame_index]);
        txt_type.setText(model.type);
        txt_size.setText(model.width + " X " + model.height);
        txt_amount.setText("Amount: " + model.amount);
        txt_price.setText("$" + String.valueOf(model.price*model.amount));
        img_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PhotoViewerActivity.class);
                intent.putExtra("SEL_PHOTO", model);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        return view;
    }

}
