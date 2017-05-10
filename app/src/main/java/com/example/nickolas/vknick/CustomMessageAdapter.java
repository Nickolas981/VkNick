package com.example.nickolas.vknick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiMessage;

/**
 * Created by Nickolas on 02.05.2017.
 */

public class CustomMessageAdapter extends BaseAdapter {

    VKApiMessage[] messages;
    Context context;

    public CustomMessageAdapter(Context context, VKApiMessage[] messages) {
        this.messages = new VKApiMessage[messages.length];
        for (int i = 0; i < messages.length; i++) {
            this.messages[i] = messages[i];
        }
        this.context = context;
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

        if (messages[position].out){
            view = inflater.inflate(R.layout.custom_message_out_view, null);
            setData.textView = (TextView) view.findViewById(R.id.out_message);
            setData.container = (View) view.findViewById(R.id.out_message_container);
        } else {
            view = inflater.inflate(R.layout.custom_message_in_view, null);
            setData.textView = (TextView) view.findViewById(R.id.in_message);
            setData.container = (View) view.findViewById(R.id.in_message_container);
        }
        setData.textView.setText(messages[position].body);
        if (!messages[position].read_state){
            setData.container.setBackgroundResource(R.color.unreadedColor);
        }

        return view;
    }

    private class SetData {
        TextView textView;
        View container;
    }
}
