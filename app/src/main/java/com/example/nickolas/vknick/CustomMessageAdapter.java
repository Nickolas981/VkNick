package com.example.nickolas.vknick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    public CustomMessageAdapter(Context context, VKApiMessage[] messages, Boolean isChat, HashMap<Integer,String> photo) {
        this.messages = new VKApiMessage[messages.length];
        for (int i = 0; i < messages.length; i++) {
            this.messages[i] = messages[i];
        }
        this.context = context;
        this.photo = photo;
        this.isChat = isChat;
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
                new DownloadImageTask(setData.senderAvatar).execute(photo.get(messages[position].user_id));
            }
        }
        setData.textView.setText(messages[position].body);
        if (!messages[position].read_state) {
            setData.container.setBackgroundResource(R.color.unreadedColor);
        }

        return view;
    }

    private class SetData {
        ImageView senderAvatar;
        TextView textView;
        View container;
    }
}
