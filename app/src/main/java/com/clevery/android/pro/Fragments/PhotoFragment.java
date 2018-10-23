package com.clevery.android.pro.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.clevery.android.pro.App;
import com.clevery.android.pro.MainActivity;
import com.clevery.android.pro.Models.PhotoModel;
import com.clevery.android.pro.PhotoViewerActivity;
import com.clevery.android.pro.R;
import com.clevery.android.pro.Utils.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class PhotoFragment extends Fragment {
    MainActivity activity;
    EditText edit_amount, edit_width, edit_height;
    ImageView sel_photo, img_frame;
    ImageView[] img_photo = new ImageView[3];
    Button[] btn_photo = new Button[3];
    RadioGroup group_type;
    RadioButton radio_card, radio_normal;
    TextView txt_price;
    ImageView imageView[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        App.hideKeyboard(activity);
        group_type = (RadioGroup)v.findViewById(R.id.group_type);
        radio_card = (RadioButton)v.findViewById(R.id.radio_card);
        radio_normal = (RadioButton)v.findViewById(R.id.radio_normal);
        img_frame = (ImageView)v.findViewById(R.id.img_frame);
        edit_amount = (EditText)v.findViewById(R.id.edit_amount);
        edit_width = (EditText)v.findViewById(R.id.edit_width);
        edit_height = (EditText)v.findViewById(R.id.edit_height);
        sel_photo = (ImageView)v.findViewById(R.id.img_photo);
        img_photo[0] = (ImageView)v.findViewById(R.id.img_photo1);
        img_photo[1] = (ImageView)v.findViewById(R.id.img_photo2);
        img_photo[2] = (ImageView)v.findViewById(R.id.img_photo3);
        btn_photo[0] = (Button)v.findViewById(R.id.btn_photo1);
        btn_photo[1] = (Button)v.findViewById(R.id.btn_photo2);
        btn_photo[2] = (Button)v.findViewById(R.id.btn_photo3);
        txt_price = (TextView)v.findViewById(R.id.txt_price);

        sel_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.frag_selUri == null) {
                    return;
                }
                Intent intent = new Intent(activity, PhotoViewerActivity.class);
                intent.putExtra("SEL_PHOTO_URI", activity.frag_selUri.toString());
                intent.putExtra("SEL_FRAME_INDEX", activity.frag_frame_index);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ly_frame);
        layout.removeAllViews();
        layout.setPadding(0, 0, 0, 0);
        imageView = new ImageView[activity.myFrames.length];
        for (int i = 0; i < activity.myFrames.length; i++) {
            RelativeLayout layout1 = new RelativeLayout(activity);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(230, ViewGroup.LayoutParams.MATCH_PARENT);
            layout1.setLayoutParams(layoutParams);
            layout1.setGravity(RelativeLayout.CENTER_IN_PARENT);
            layout1.setBackgroundColor(Color.parseColor("#ffffff"));
            imageView[i] = new ImageView(activity);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            lp.setMarginEnd(2);
            imageView[i].setLayoutParams(lp);
            Glide.with(activity).load(activity.myFrames[i])
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.default_pic).centerInside().dontAnimate()).into(imageView[i]);
            imageView[i].setAdjustViewBounds(false);
            imageView[i].setAlpha(0.3f);
            imageView[i].setId(activity.myFrames[i]);
            layout1.addView(imageView[i]);
            final int index = i;
            imageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.frag_frame_index = index;
                    for (int j = 0; j < activity.myFrames.length; j++) {
                        imageView[j].setAlpha(0.3f);
                    }
                    imageView[activity.frag_frame_index].setAlpha(1.0f);
//                    imageView[activity.frag_frame_index].setBackgroundColor(Color.parseColor("#dddddd"));
                    img_frame.setImageResource(activity.myFrames[activity.frag_frame_index]);
                }
            });
            if (i == 0) {
                TextView textView = new TextView(activity);
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
                textView.setLayoutParams(layoutParams1);
                textView.setText("No\nFrame");
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                layout1.addView(textView);
            }
            layout.addView(layout1);
        }
        imageView[activity.frag_frame_index].setAlpha(1.0f);
        img_frame.setImageResource(activity.myFrames[activity.frag_frame_index]);
        edit_amount.setText(activity.frag_amount);
        edit_width.setText(activity.frag_width);
        edit_height.setText(activity.frag_height);
        txt_price.setText("$" + activity.frag_price);
        if (activity.frag_type.equals("ID Card")) {
            radio_card.setChecked(true);
        } else {
            radio_normal.setChecked(true);
        }
        group_type = (RadioGroup)v.findViewById(R.id.group_type);
        group_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_card) {
                    activity.frag_type = "ID Card";
                } else {
                    activity.frag_type = "Normal";
                }
            }
        });
        edit_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.frag_amount = edit_amount.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edit_width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.frag_width = edit_width.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edit_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity.frag_height = edit_height.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        for (int i = 0; i < 3; i++) {
            final int j = i;
            img_photo[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPhoto(j);
                }
            });
        }
        for (int i = 0; i < 3; i++) {
            final int j = i;
            btn_photo[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(activity, PhotoFragment.this);
                }
            });
        }

        Button btn_save = (Button)v.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.frag_amount.length()*activity.frag_width.length()*activity.frag_height.length() == 0) {
                    Toast.makeText(activity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (activity.frag_selUri == null) {
                    Toast.makeText(activity, "Please capture Image!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Bitmap selectedImage = null;
//                try {
//                    final InputStream imageStream = activity.getContentResolver().openInputStream(activity.frag_selUri);
//                    selectedImage = BitmapFactory.decodeStream(imageStream);
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String encodedImage = Utils.encodeToBase64(selectedImage);
                PhotoModel model = new PhotoModel(0, activity.frag_selUri.toString(), activity.frag_type, activity.frag_frame_index, activity.frag_width, activity.frag_height, Float.valueOf(activity.frag_price), Integer.valueOf(activity.frag_amount), "", false);
                ArrayList<PhotoModel> arrayList = App.readPreference_waiting_array();
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(model);
                App.setPreference_waiting_array(arrayList);
                Toast.makeText(activity, "Successfully saved!", Toast.LENGTH_SHORT).show();
                // initialize -------------------
                edit_amount.setText("");
                edit_width.setText("");
                edit_height.setText("");
                radio_card.setChecked(true);
                activity.frag_frame_index = 0;
                img_frame.setImageResource(activity.myFrames[activity.frag_frame_index]);
                imageView[activity.frag_frame_index].setAlpha(1.0f);
                activity.frag_amount= ""; activity.frag_width = ""; activity.frag_height = ""; activity.frag_type = "";activity.frag_price = "0.00";
                activity.frag_selUri = null;
                for (int i = 0; i < 3; i++) {
                    activity.frag_imgUri[i] = null;
                    setPersonPhoto(activity.frag_imgUri[i], img_photo[i]);
                }
                setPersonPhoto(activity.frag_selUri, sel_photo);
            }
        });
        return v;
    }
    private void deselectPhotos() {
        for (int i = 0; i < 3; i++) {
            final int j = i;
            img_photo[j].setBackgroundColor(Color.parseColor("#00000000"));
            btn_photo[j].setVisibility(View.GONE);
        }
    }
    private void selectPhoto(int j) {
        activity.frag_photo_index = j;
        deselectPhotos();
        img_photo[j].setBackgroundDrawable(activity.getDrawable(R.drawable.frame_circle_border));
        btn_photo[j].setVisibility(View.VISIBLE);
        activity.frag_selUri = activity.frag_imgUri[j];
        setPersonPhoto(activity.frag_selUri, sel_photo);
    }
    private void setPersonPhoto(Uri uri, ImageView imageView) {
        Glide.with(this).load(uri)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.person).centerCrop().dontAnimate()).into(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                activity.frag_selUri = result.getUri();
                activity.frag_imgUri[activity.frag_photo_index] = activity.frag_selUri;
                setPersonPhoto(activity.frag_selUri, img_photo[activity.frag_photo_index]);
                setPersonPhoto(activity.frag_selUri, sel_photo);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(activity, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onResume() {
        selectPhoto(activity.frag_photo_index);
//        edit_student_id.setText(activity.frag_student_id);
//        edit_school_code.setText(activity.frag_school_code);
//        edit_classroom.setText(activity.frag_classroom);
        setPersonPhoto(activity.frag_selUri, sel_photo);
        for (int i = 0; i < 3; i++) {
            setPersonPhoto(activity.frag_imgUri[i], img_photo[i]);
        }
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
