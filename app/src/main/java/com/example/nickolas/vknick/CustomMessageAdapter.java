package com.example.nickolas.vknick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nickolas on 02.05.2017.
 */

public class CustomMessageAdapter extends BaseAdapter {

    VKApiMessage[] messages;
    Context context;
    HashMap<Integer, String> photo;
    Boolean isChat;
    ArrayList<String>[] attachedPhoto;


    public CustomMessageAdapter(Context context, VKApiMessage[] messages, Boolean isChat, HashMap<Integer, String> photo, ArrayList<String>[] attachedPhoto) {
        this.messages = new VKApiMessage[messages.length];
        for (int i = 0; i < messages.length; i++) {
            this.messages[i] = messages[i];
        }
        this.context = context;
        this.photo = photo;
        this.isChat = isChat;
        this.attachedPhoto = attachedPhoto;
    }

    @Override
    public int getCount() {
        return messages.length;
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
        SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (messages[position].out) {
            view = inflater.inflate(R.layout.custom_message_out_view, null);
            setData.textView = (TextView) view.findViewById(R.id.out_message);
            setData.container = (View) view.findViewById(R.id.out_message_container);
        } else {
            view = inflater.inflate(R.layout.custom_message_in_view, null);
            setData.textView = (TextView) view.findViewById(R.id.in_message);
            setData.container = (View) view.findViewById(R.id.in_message_container);
            if (isChat) {
                setData.senderAvatar = (ImageView) view.findViewById(R.id.sender_avatar);
                setData.senderAvatar.setVisibility(View.VISIBLE);
                if (position != 0 && messages[position - 1].user_id == messages[position].user_id) {
                    setData.senderAvatar.setVisibility(View.INVISIBLE);
                }
                new DownloadImageTask(setData.senderAvatar).execute(photo.get(messages[position].user_id));
            }
        }
        setData.textView.setText(messages[position].body);
        if (!messages[position].read_state) {
            setData.container.setBackgroundResource(R.color.unreadedColor);
        }
        if (attachedPhoto[position] != null && !attachedPhoto[position].isEmpty()) {

            View view1;
            ImageView[] imageView;
            if (messages[position].body.equals("")) {
                setData.textView.setVisibility(View.GONE);
            }
            view1 = inflater.inflate(GetResourceIdByString.getResId("photo_" + Integer.toString(attachedPhoto[position].size()), R.layout.class), null);

            imageView = new ImageView[attachedPhoto[position].size()];

            imageView[0] = (ImageView) view1.findViewById(GetResourceIdByString.getResId("photo_1", R.id.class));

            for (int j = 0; j < imageView.length; j++) {
                imageView[j] = (ImageView) view1.findViewById(GetResourceIdByString.getResId("photo_" + (j+1), R.id.class));
                new DownloadImageTask(imageView[j]).execute(attachedPhoto[position].get(j));
            }


            setData.photoList = (LinearLayout) view.findViewById(R.id.photoList);
            setData.photoList.setVisibility(View.VISIBLE);
            setData.photoList.addView(view1);
        }

        return view;
    }

    private class SetData {
        ImageView senderAvatar;
        TextView textView;
        View container;
        LinearLayout photoList;
    }
}
