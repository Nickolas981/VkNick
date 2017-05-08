package com.example.nickolas.vknick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

/**
 * Created by Nickolas on 30.04.2017.
 */

class CustomDialogAdapter extends BaseAdapter {


    private ArrayList<String> users, messages;
    private Context context;
    private ArrayList<String> photo;

    public CustomDialogAdapter(Context context, ArrayList<String> users, ArrayList<String> messages, ArrayList<String> photo) {
        this.users = users;
        this.messages = messages;
        this.context = context;
        this.photo = photo;
    }

    @Override
    public int getCount() {
        return users.size();
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final SetData setData = new SetData();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialogs_view, null);
        setData.userName = (TextView) view.findViewById(R.id.user_name);
        setData.msg = (TextView) view.findViewById(R.id.msg);
        setData.imageView = (ImageView)view.findViewById(R.id.dialogAvatarImage);
        if (!photo.get(position).equals("1"))
            new DownloadImageTask(setData.imageView).execute(photo.get(position));
        setData.userName.setText(users.get(position));
        setData.msg.setText(messages.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private class SetData {
        TextView userName, msg;
        ImageView imageView;
    }
}
