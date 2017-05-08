package com.example.nickolas.vknick;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainPage extends AppCompatActivity {

    private TextView mTextMessage;


    private FragmentTransaction fragT;
    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL, VKScope.AUDIO};
    private int selected;

    com.example.nickolas.vknick.DialogFragment dialogFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragT = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_music:
                    if (selected != R.id.navigation_music   ) {
                        selected = R.id.navigation_music;
                        fragT.remove(dialogFragment);
                        fragT.commit();
                    }
                    return true;
                case R.id.navigation_feed:
                    if (R.id.navigation_feed != selected) {
                        selected = R.id.navigation_feed;
                        fragT.remove(dialogFragment);
                        fragT.commit();
                    }
                    return true;
                case R.id.navigation_messages:
                    if (selected != R.id.navigation_messages) {
                        selected = R.id.navigation_messages;
                        fragT.add(R.id.frame_view, dialogFragment);
                        fragT.commit();
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_view);
        VKSdk.login(this, scope);

        DialogModel dialogModel = new DialogModel();
        dialogFragment = new com.example.nickolas.vknick.DialogFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Toast.makeText(MainPage.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(MainPage.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}