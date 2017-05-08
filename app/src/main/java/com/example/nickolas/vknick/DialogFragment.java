package com.example.nickolas.vknick;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class DialogFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    View view;
    SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        view = inflater.inflate(R.layout.fragment_dialog, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        DialogModel.listView = (ListView) view.findViewById(R.id.dialogListView);

        DialogModel.listView.setAdapter(new CustomDialogAdapter(view.getContext(), DialogModel.fullName, DialogModel.lastMessage, DialogModel.photo));


        return view;
    }

    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                DialogModel.update();
                DialogModel.listView.setAdapter(new CustomDialogAdapter(view.getContext(), DialogModel.fullName, DialogModel.lastMessage, DialogModel.photo));
                swipeLayout.setRefreshing(false);
            }
        }, 0);
    }
}
