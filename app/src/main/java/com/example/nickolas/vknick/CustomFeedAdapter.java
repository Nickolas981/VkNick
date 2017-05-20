package com.example.nickolas.vknick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nickolas on 15.05.2017.
 */

public class CustomFeedAdapter extends BaseAdapter {

    FeedModel feedModel;
    Context context;

    public CustomFeedAdapter(FeedModel feedModel, Context context) {
        this.feedModel = feedModel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return feedModel.posts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        SetData setData = new SetData();

        view = inflater.inflate(R.layout.custom_post_view, null);

        setData.titlePhoto = (ImageView) view.findViewById(R.id.post_title_photo);
        setData.title = (TextView) view.findViewById(R.id.post_title);
        setData.time = (TextView) view.findViewById(R.id.post_title_time);
        setData.text = (TextView) view.findViewById(R.id.post_text);
        setData.photoList = (LinearLayout) view.findViewById(R.id.post_photos);
        setData.author = (TextView) view.findViewById(R.id.post_author_name);
        setData.likeButton = (ImageView) view.findViewById(R.id.like_button);
        setData.likes = (TextView) view.findViewById(R.id.post_like_count);
        setData.comments = (TextView) view.findViewById(R.id.post_comment_count);
        setData.reposts = (TextView) view.findViewById(R.id.post_share_count);
        setData.title.setText(feedModel.posts.get(position).getText());
        


        return view;
    }

    class SetData {
        ImageView titlePhoto, likeButton;
        TextView title, time, text, author, likes, reposts, comments;
        LinearLayout photoList;
    }
}
