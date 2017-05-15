package com.example.nickolas.vknick;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FeedFragment extends Fragment {


    private View view;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    FeedModel feedModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        feedModel.recyclerView = (RecyclerView) view.findViewById(R.id.feed_recycler_view);

        mLayoutManager  = new LinearLayoutManager(view.getContext());
        feedModel.recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FeedAdapter(feedModel);
        feedModel.recyclerView.setAdapter(mAdapter);

        feedModel.recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                feedModel.update();
            }
        }, 1000);
    }
}
