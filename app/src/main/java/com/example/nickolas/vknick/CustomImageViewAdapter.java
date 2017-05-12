//package com.example.nickolas.vknick;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import java.util.ArrayList;
//
///**
// * Created by Nickolas on 12.05.2017.
// */
//
//public class CustomImageViewAdapter extends BaseAdapter {
//
//    ArrayList<String> photo;
//    Context context;
//
//    public CustomImageViewAdapter(ArrayList<String> photo, Context context) {
//        this.photo = photo;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return photo.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return photo.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.custom_image_view, null);
//
//        ImageView imageView = (ImageView) view.findViewById(R.id.image_in_message);
//        new DownloadImageTask(imageView).execute(photo.get(position));
//        return view;
//    }
//}
