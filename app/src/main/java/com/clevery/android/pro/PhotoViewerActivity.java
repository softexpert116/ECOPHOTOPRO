package com.clevery.android.pro;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.clevery.android.pro.Models.PhotoModel;
import com.clevery.android.pro.Utils.Utils;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewerActivity extends AppCompatActivity {
    Uri uri = null;
    PhotoModel photo = null;
    int frame_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        if (getIntent().getStringExtra("SEL_PHOTO_URI") != null) {
            uri = Uri.parse(getIntent().getStringExtra("SEL_PHOTO_URI"));
        }
        photo = (PhotoModel) getIntent().getSerializableExtra("SEL_PHOTO");
        frame_index = getIntent().getIntExtra("SEL_FRAME_INDEX", 0);
        RelativeLayout ly_photo = findViewById(R.id.ly_photo);
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PhotoView photoView = (PhotoView)findViewById(R.id.iv_photo);
        final ImageView img_frame = (ImageView)findViewById(R.id.img_frame);
        photoView.setZoomable(true);
        int fr_index = 0;
        if (uri != null) {
            Glide.with(this).load(uri)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.default_pic).fitCenter().dontAnimate()).into(photoView);
            img_frame.setImageResource(MainActivity.myFrames[frame_index]);
            fr_index = frame_index;
        } else {
            Glide.with(this).load(photo.photo)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.default_pic).fitCenter().dontAnimate()).into(photoView);
            img_frame.setImageResource(MainActivity.myFrames[photo.frame_index]);
            fr_index = photo.frame_index;
        }
        if (fr_index == 0) {
            ly_photo.setPadding(0,0,0,0);
        } else {
            ly_photo.setPadding(40,40,40,40);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
