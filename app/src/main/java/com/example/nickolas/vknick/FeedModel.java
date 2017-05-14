package com.example.nickolas.vknick;

import android.content.Context;
import android.widget.ListView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nickolas on 14.05.2017.
 */

public class FeedModel {
    ArrayList<PostModel> posts;
    ListView listView;
    String startFrom;
    ArrayList<Group> groups;


    public FeedModel() {
        update();
    }

    void update() {                                     //сюда вставивть  адаптер

        posts = new ArrayList<>();
        groups = new ArrayList<>();
        VKRequest request = new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.FILTERS, "post", VKApiConst.COUNT, "1"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONObject jsonResponse = response.json.getJSONObject("response");

                    JSONArray gr = jsonResponse.getJSONArray("groups");

                    for (int i = 0; i < gr.length(); i++) {
                        JSONObject group = gr.getJSONObject(i);
                        Group g = new Group();
                        g.id = group.getInt("id");
                        g.name = group.getString("name");
                        g.photo = group.getString("photo_50");
                        groups.add(g);
                    }

                    JSONArray items = jsonResponse.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        PostModel post = new PostModel();
                        JSONObject item = items.getJSONObject(i);
                        post.setID(item.getInt("post_id"));
                        post.setSourceID(-(item.getInt("source_id")));
                        post.setText(item.getString("text"));
                        post.setIsAd(item.getInt("marked_as_ads"));
                        post.setTime(convertTime(item.getInt("date")));
                        JSONArray attachments = item.getJSONArray("attachments");
                        for (int j = 0; j < attachments.length(); j++) {
                            if (attachments.getJSONObject(j).getString("type").equals("photo")) {
                                JSONObject photo = attachments.getJSONObject(j).getJSONObject("photo");
                                post.addPostPhoto(photo.getString("photo_604"));
                            }
                        }
                        JSONObject comments = item.getJSONObject("comments");
                        post.setComments(comments.getInt("count"));
                        post.setCanComment(comments.getInt("can_post"));
                        JSONObject likes = item.getJSONObject("likes");
                        post.setLikes(likes.getInt("count"));
                        post.setLiked(likes.getInt("user_likes"));
                        JSONObject reposts = item.getJSONObject("reposts");
                        post.setShares(reposts.getInt("count"));
                        post.setGroup(findGroup(post.getSourceID()));
                        posts.add(post);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Group findGroup(int sourceId){
        for (Group group: groups){
            if (group.id == sourceId){
                return group;
            }
        }
        return null;
    }

    private String convertTime(int t) {
        Date date = new Date(t * 1000);
        String hours = Integer.toString(date.getHours());
        String minutes = Integer.toString(date.getMinutes());
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }
        return (hours + ":" + minutes);
    }


}
class Group{
    int id;
    String name;
    String photo;
}