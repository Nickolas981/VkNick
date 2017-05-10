package com.example.nickolas.vknick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nickolas on 30.04.2017.
 */

class CustomDialogAdapter extends BaseAdapter {


    private ArrayList<String> fullName, messages;
    private Context context;
    private ArrayList<String> photo;
    private ArrayList<Boolean> readed;
    private ArrayList<Boolean> out;
    private String mPhoto;
    private ArrayList<Integer> ids;

    public CustomDialogAdapter(Context context, DialogModel dialogModel) {
        fullName = dialogModel.fullName;
        messages = dialogModel.lastMessage;
        this.context = context;
        photo = dialogModel.photo;
        mPhoto = dialogModel.mPhoto;
        readed = dialogModel.readed;
        out = dialogModel.out;
        ids = dialogModel.id;
    }

    @Override
    public int getCount() {
        return fullName.size();
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
        setData.myAvatar = (ImageView) view.findViewById(R.id.my_avatar);
        setData.msg = (TextView) view.findViewById(R.id.msg);
        setData.imageView = (ImageView) view.findViewById(R.id.dialogAvatarImage);
        if (out.get(position)) {
            new DownloadImageTask(setData.myAvatar).execute(mPhoto);
            setData.myAvatar.setVisibility(View.VISIBLE);
            setData.msg.setPadding(10, 0, 0, 0);

            if (!readed.get(position)) {
                setData.msg.setBackgroundResource(R.color.unreadedColor);
            }
        } else if (!readed.get(position)) {
            view.setBackgroundResource(R.color.unreadedColor);
        }
        if (!photo.get(position).equals("1"))
            new DownloadImageTask(setData.imageView).execute(photo.get(position));
        setData.userName.setText(fullName.get(position));
        setData.msg.setText(messages.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessagePage.class);
                intent.putExtra("user_name", fullName.get(position));
                intent.putExtra("user_id", ids.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }

    private class SetData {
        TextView userName, msg;
        ImageView imageView, myAvatar;

    }
}
