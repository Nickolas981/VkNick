package com.example.nickolas.vknick;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class DialogFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    SwipeRefreshLayout swipeLayout;
    public DialogModel dialogModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        dialogModel = new DialogModel();
        super.onCreate(savedInstanceState);
    }

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

        dialogModel.listView = (ListView) view.findViewById(R.id.dialogListView);
//        dialogModel.listView.setAdapter(new CustomDialogAdapter(view.getContext(), dialogModel));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogModel.update(new CustomDialogAdapter(view.getContext(), dialogModel));
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        dialogModel.update(new CustomDialogAdapter(view.getContext(), dialogModel));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                dialogModel.listView.setAdapter(new CustomDialogAdapter(view.getContext(), dialogModel));
                swipeLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
