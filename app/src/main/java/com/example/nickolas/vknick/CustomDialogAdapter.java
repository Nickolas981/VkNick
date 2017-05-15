package com.example.nickolas.vknick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nickolas on 30.04.2017.
 */

class CustomDialogAdapter extends BaseAdapter {


    private Context context;
    DialogModel dialogModel;

    public CustomDialogAdapter(Context context, DialogModel dialogModel) {
        this.context = context;
        this.dialogModel = dialogModel;
    }

    @Override
    public int getCount() {
        return dialogModel.fullName.size();
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
        setData.onlineIndicator = (ImageView) view.findViewById(R.id.online_indicator);
        setData.unreadedIndicator = (TextView) view.findViewById(R.id.count_of_messages);
        setData.time = (TextView) view.findViewById(R.id.last_message_time);

//        String min = Integer.toString(dialogModel.time.get(position).getMinutes());
//        min = dialogModel.time.get(position).getMinutes() > 9? min:"0" + min;
//        String time = Long.toString(dialogModel.time.get(position).getHours()) + ":" + min;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(dialogModel.time.get(position));


        if (dialogModel.out.get(position)) {
            new DownloadImageTask(setData.myAvatar).execute(dialogModel.mPhoto);
            setData.myAvatar.setVisibility(View.VISIBLE);
            setData.msg.setPadding(10, 0, 0, 0);

            if (!dialogModel.readed.get(position)) {
                setData.msg.setBackgroundResource(R.color.unreadedColor);
            }
        } else if (!dialogModel.readed.get(position)) {
            view.setBackgroundResource(R.color.unreadedColor);
            setData.unreadedIndicator.setText(dialogModel.countOfUnreded.get(position).toString());
            setData.unreadedIndicator.setVisibility(View.VISIBLE);
        }
        if (dialogModel.online[position]){
            setData.onlineIndicator.setVisibility(View.VISIBLE);
        }
        if (dialogModel.onlinePhone[position]){
            setData.onlineIndicator.setImageResource(R.drawable.ic_phone_android_black_24dp);
        }
        if (!dialogModel.photo[position].equals("1"))
            new DownloadImageTask(setData.imageView).execute(dialogModel.photo[position]);
        setData.userName.setText(dialogModel.fullName.get(position));
        setData.time.setText(time);
        setData.msg.setText(dialogModel.lastMessage.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessagePage.class);
                intent.putExtra("user_name", dialogModel.fullName.get(position));
                intent.putExtra("user_id", dialogModel.id[position]);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private class SetData {
        TextView userName, msg, unreadedIndicator, time;
        ImageView imageView, myAvatar, onlineIndicator;

    }
}
