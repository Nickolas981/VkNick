package com.example.nickolas.vknick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;

import org.json.JSONArray;
import org.json.JSONException;

public class MessagePage extends AppCompatActivity implements View.OnClickListener, SwipyRefreshLayout.OnRefreshListener {

    public ListView messageListView;
    ImageView sendButton;
    EditText etSendingMessage;
    SwipyRefreshLayout swipeLayout;
    VKApiMessage[] msg;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);

        messageListView = (ListView) findViewById(R.id.messageListView);
        sendButton = (ImageView) findViewById(R.id.btnSendMessage);
        etSendingMessage = (EditText) findViewById(R.id.etMessage);

        sendButton.setOnClickListener(this);

        Intent intent = getIntent();

        swipeLayout = (SwipyRefreshLayout) findViewById(R.id.swipe_refresh_message);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        id = intent.getIntExtra("user_id", 1);
        getSupportActionBar().setTitle(intent.getStringExtra("user_name"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshMessages();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void refreshMessages() {
        VKRequest vkRequest = new VKRequest("messages.getHistory", VKParameters.from(VKApiConst.USER_ID, id));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
                    msg = new VKApiMessage[array.length()];
                    for (int i = 0; i < array.length(); i++) {
                        VKApiMessage mes = new VKApiMessage(array.getJSONObject(i));
                        msg[array.length() - 1 - i] = mes;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomMessageAdapter customMessageAdapter = new CustomMessageAdapter(MessagePage.this, msg);
                messageListView.setAdapter(new CustomMessageAdapter(MessagePage.this, msg));
                messageListView.setSelection(customMessageAdapter.getCount() - 1);
            }

        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendMessage) {
            VKRequest vkRequest = new VKRequest("messages.send", VKParameters.from(VKApiConst.USER_ID, id, VKApiConst.MESSAGE, etSendingMessage.getText().toString()));
            etSendingMessage.setText("");
            vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    refreshMessages();
                }
            });
        }
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        refreshMessages();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
