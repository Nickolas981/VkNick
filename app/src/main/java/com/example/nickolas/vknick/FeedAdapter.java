package com.example.nickolas.vknick;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nickolas on 15.05.2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    FeedModel feedModel;
    Context context;
    View v;

    public FeedAdapter(FeedModel feedModel, Context context) {
        this.feedModel = feedModel;
        this.context = context;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_post_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        holder.title = (TextView) v.findViewById(R.id.post_title);
        holder.titlePhoto = (ImageView) v.findViewById(R.id.post_title_photo);
        holder.time = (TextView) v.findViewById(R.id.post_title_time);
        holder.likes = (TextView) v.findViewById(R.id.post_like_count);
        holder.text = (TextView) v.findViewById(R.id.post_text);
        holder.comments = (TextView) v.findViewById(R.id.post_comment_count);
        holder.reposts = (TextView) v.findViewById(R.id.post_share_count);
        holder.photoList = (LinearLayout) v.findViewById(R.id.post_photos);
        holder.likeButton = (ImageView) v.findViewById(R.id.like_button);


        return holder;
    }


    @Override
    public void onBindViewHolder(final FeedAdapter.ViewHolder holder, final int position) {

        final PostModel post = feedModel.posts.get(position);

        new DownloadImageTask(holder.titlePhoto).execute(post.getGroup().photo);
        holder.text.setText(post.getText());
        holder.title.setText(post.getGroup().name);
        holder.time.setText(post.getTime());
        holder.likes.setText(Integer.toString(post.getLikes()));
        holder.reposts.setText(Integer.toString(post.getShares()));
        holder.comments.setText(Integer.toString(post.getComments()));
        holder.photoList.removeAllViews();

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post.isLiked() == 1) {
                    VKRequest request = new VKRequest("likes.delete", VKParameters.from("type", "post", "item_id", Integer.toString(post.getID()), "owner_id", "-" + post.getSourceID()));
                    request.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            try {
                                JSONObject jsonObject = response.json.getJSONObject("response");
                                post.setLikes(Integer.parseInt(jsonObject.getString("likes")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            post.setLiked(0);
                            notifyItemChanged(position);
                        }
                    });
                } else{
                    VKRequest request = new VKRequest("likes.add", VKParameters.from("type", "post", "item_id", Integer.toString(post.getID()), "owner_id", "-" + post.getSourceID()));
                    request.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            try {
                                JSONObject jsonObject = response.json.getJSONObject("response");
                                post.setLikes(Integer.parseInt(jsonObject.getString("likes")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            post.setLiked(1);
                            notifyItemChanged(position);
                        }
                    });
                }
            }
        });

        if (!post.getPostPhotos().isEmpty()) {
            View view1;
            ImageView[] imageView;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int asd = post.getPostPhotos().size();
            view1 = inflater.inflate(GetResourceIdByString.getResId("photo_" + Integer.toString(asd), R.layout.class), null);

            imageView = new ImageView[asd];

            imageView[0] = (ImageView) view1.findViewById(GetResourceIdByString.getResId("photo_1", R.id.class));

            for (int j = 0; j < asd; j++) {
                imageView[j] = (ImageView) view1.findViewById(GetResourceIdByString.getResId("photo_" + (j + 1), R.id.class));
                new DownloadImageTask(imageView[j]).execute(post.getPostPhotos().get(j));
            }
            holder.photoList.setVisibility(View.VISIBLE);
            holder.photoList.addView(view1);
            view1 = null;
        }

        if (post.isLiked() == 1) {
            holder.likes.setTextColor(Color.parseColor("#338cc9"));
            holder.likeButton.setImageResource(R.drawable.ic_favorite_true_blue_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return feedModel.posts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView titlePhoto, likeButton;
        TextView title, time, text, author, likes, reposts, comments;
        LinearLayout photoList;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}