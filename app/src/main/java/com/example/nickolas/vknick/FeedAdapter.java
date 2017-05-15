package com.example.nickolas.vknick;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nickolas on 15.05.2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    FeedModel feedModel;

    public FeedAdapter(FeedModel feedModel) {
        this.feedModel = feedModel;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_post_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        holder.title = (TextView) v.findViewById(R.id.post_title);
        holder.titlePhoto = (ImageView) v.findViewById(R.id.post_title_photo);
        holder.time = (TextView) v.findViewById(R.id.post_title_time);
        holder.likes = (TextView) v.findViewById(R.id.post_like_count);
        holder.text = (TextView) v.findViewById(R.id.post_text);
        holder.comments = (TextView) v.findViewById(R.id.post_comment_count);
        holder.reposts = (TextView) v.findViewById(R.id.post_share_count);

        return holder;
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder holder, int position) {

        PostModel post = feedModel.posts.get(position);

        new DownloadImageTask(holder.titlePhoto).execute(post.getGroup().photo);
        holder.text.setText(post.getText());
        holder.title.setText(post.getGroup().name);
        holder.time.setText(post.getTime());
        holder.likes.setText(Integer.toString(post.getLikes()));
        holder.reposts.setText(Integer.toString(post.getShares()));
        holder.comments.setText(Integer.toString(post.getComments()));

    }

    @Override
    public int getItemCount() {
        return feedModel.posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView titlePhoto, likeButton;
        TextView title, time, text, author, likes, reposts, comments;
        LinearLayout photoList;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
