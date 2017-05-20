package com.example.nickolas.vknick;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private View view;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    FeedModel feedModel;
    SwipeRefreshLayout swipeRefreshLayout;

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

        mLayoutManager = new LinearLayoutManager(view.getContext());
        feedModel.recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FeedAdapter(feedModel, view.getContext());
        feedModel.recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        feedModel.recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        feedModel.update(new FeedAdapter(feedModel, view.getContext()));

    }

    @Override
    public void onRefresh() {
        feedModel.update(new FeedAdapter(feedModel, view.getContext()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
