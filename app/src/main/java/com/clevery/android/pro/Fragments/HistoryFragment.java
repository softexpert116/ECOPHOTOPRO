package com.clevery.android.pro.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.clevery.android.pro.Adapter.AlbumListAdapter;
import com.clevery.android.pro.Adapter.PhotoListAdapter;
import com.clevery.android.pro.App;
import com.clevery.android.pro.MainActivity;
import com.clevery.android.pro.Models.AlbumModel;
import com.clevery.android.pro.Models.PhotoModel;
import com.clevery.android.pro.R;
import com.clevery.android.pro.httpModule.RequestBuilder;
import com.clevery.android.pro.httpModule.ResponseElement;
import com.clevery.android.pro.httpModule.RunanbleCallback;

import org.json.JSONArray;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    MainActivity activity;
    PhotoListAdapter photoListAdapter;
    AlbumListAdapter albumListAdapter;
    ArrayList<PhotoModel> arrayPhoto = new ArrayList<>();
    ArrayList<AlbumModel> arrayAlbum = new ArrayList<>();
//    SwipeRefreshLayout swipeRefreshLayout;
    ListView list_photo, list_album;
    Button btn_photo, btn_album;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        list_photo = (ListView) v.findViewById(R.id.list_photo);
        list_album = (ListView) v.findViewById(R.id.list_album);
        btn_photo = (Button) v.findViewById(R.id.btn_photo);
        btn_album = (Button) v.findViewById(R.id.btn_album);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_album.setTextColor(Color.parseColor("#aaaaaa"));
                btn_photo.setTextColor(Color.parseColor("#222222"));
                list_album.setVisibility(View.GONE);
                list_photo.setVisibility(View.VISIBLE);
            }
        });
        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_photo.setTextColor(Color.parseColor("#aaaaaa"));
                btn_album.setTextColor(Color.parseColor("#222222"));
                list_photo.setVisibility(View.GONE);
                list_album.setVisibility(View.VISIBLE);
            }
        });

//        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.ly_refresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                getRequest();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        list_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(activity, "Student ID: xxx\nSchool Code: yyy\nClassroom: zzz", Toast.LENGTH_SHORT).show();
            }
        });

        list_album.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(activity, "Student ID: xxx\nSchool Code: yyy\nClassroom: zzz", Toast.LENGTH_SHORT).show();
            }
        });
//        getRequest();
        for (int i = 0; i < 20; i++) {
            String type = "Normal";
            if (i > 5) {
                type = "ID Card";
            }
            int frame_index = i%5;
            boolean isDelivered = false;
            if (i > 3) {
                isDelivered = true;
            }
            PhotoModel model = new PhotoModel(i, null, type, frame_index, "2", "3", 1.21f, 3, "03/21/2018", isDelivered);
            arrayPhoto.add(model);

            type = "Matted Albums";
            if (i > 3) {
                type = "Flush Mount Albums";
            }
            if (i > 10) {
                type = "Parent or Gift Albums";
            }
            AlbumModel model1 = new AlbumModel(i, type, 1.21f, 3, "03/21/2018", isDelivered);
            arrayAlbum.add(model1);
        }
        photoListAdapter = new PhotoListAdapter(activity, arrayPhoto);
        list_photo.setAdapter(photoListAdapter);

        albumListAdapter = new AlbumListAdapter(activity, arrayAlbum);
        list_album.setAdapter(albumListAdapter);
        return v;
    }
    private void initArray(JSONArray array) {
//        arrayList.clear();
////        for (int i = 0; i < array.length(); i++) {
////            try {
////                JSONObject object = array.getJSONObject(i);
////                int id = object.getInt("id");
////                String student_id = object.getString("student_id");
////                String school_code = object.getString("school_code");
////                String classroom = object.getString("classroom");
////                String photo = object.getString("photo");
////                String date = object.getString("date");
////                PhotoModel model = new PhotoModel(id, student_id, school_code, classroom, photo, date);
////                arrayList.add(model);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
//        swipeRefreshLayout.setVisibility(View.VISIBLE);
//        if (arrayList.size() == 0) {
//            swipeRefreshLayout.setVisibility(View.INVISIBLE);
//        }
//        photoListAdapter.notifyDataSetChanged();
    }
    private void getRequest() {
//        swipeRefreshLayout.setRefreshing(true);
        RequestBuilder requestBuilder = new RequestBuilder(App.serverUrl);
        requestBuilder
                .addParam("type", "get_students")
                .addParam("user_id", App.readPreference(App.MY_ID, ""))
                .sendRequest(callback);
    }

    RunanbleCallback callback = new RunanbleCallback() {
        @Override
        public void finish(ResponseElement element) {
            int code = element.getStatusCode();
//            swipeRefreshLayout.setRefreshing(false);
            switch (code) {
                case 200:
                    initArray(element.getArray("data"));
                    break;
                case 400:
//                    break;
                case 500:
                    new AlertDialog.Builder(activity)
                            .setTitle("Error")
                            .setMessage("Can't connect to server!")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    public void onResume() {
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
