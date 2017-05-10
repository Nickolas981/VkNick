package com.example.nickolas.vknick;

import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

/**
 * Created by Nickolas on 08.05.2017.
 */

public class DialogModel extends ActionBarActivity {
    public ArrayList<String> photo;
    public ArrayList<String> fullName;
    public ArrayList<String> lastMessage;
    public ArrayList<Integer> id;
    public ListView listView;
    public String mPhoto;
    public ArrayList<Boolean> out;
    public ArrayList<Boolean> readed;

    public DialogModel() {
        update(null);
        setMyPhoto();
    }

    public void update(final CustomDialogAdapter adapter) {
        final VKRequest request = VKApi.messages().getDialogs(VKParameters.from(VKApiConst.COUNT, 15));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKApiGetDialogResponse getDialogResponse = (VKApiGetDialogResponse) response.parsedModel;

                final VKList<VKApiDialog> list = getDialogResponse.items;

                lastMessage = new ArrayList<String>();
                fullName = new ArrayList<String>();
                photo = new ArrayList<String>();
                id = new ArrayList<Integer>();
                out = new ArrayList<Boolean>();
                readed = new ArrayList<Boolean>();

                for (final VKApiDialog msg : list) {
                    lastMessage.add(msg.message.body);
                    fullName.add(msg.message.title);
                    id.add(msg.message.user_id);
                    out.add(msg.message.out);
                    readed.add(msg.message.read_state);
                }
                setPhoto(adapter);
            }
        });
    }


    private String convertId() {
        String result = id.get(0).toString();

        for (int i = 1; i < id.size(); i++) {
            result += " ," + id.get(i).toString();
        }

        return result;
    }

    private void setPhoto(final CustomDialogAdapter adapter) {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, convertId(), VKApiConst.FIELDS, "photo_50"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList<VKApiUser> userVKList = (VKList<VKApiUser>) response.parsedModel;

                for (int i = 0; i < userVKList.size(); i++) {
                    if (fullName.get(i).equals(" ... ") || fullName.get(i).equals("")) {
                        photo.add(userVKList.get(i).photo_50);
                        fullName.remove(i);
                        fullName.add(i, userVKList.get(i).first_name + " " + userVKList.get(i).last_name);
                    } else {
                        photo.add("1");
                    }
                }
                if (adapter != null){
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private void setMyPhoto(){
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_50"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList<VKApiUser> userVKList = (VKList<VKApiUser>) response.parsedModel;

                mPhoto = userVKList.get(0).photo_50;
            }
        });
    }


    public int getCount() {
        return fullName.size();
    }

    public String getFullName(int position) {
        return fullName.get(position);
    }

    public String getLastMessage(int position) {
        return lastMessage.get(position);
    }

    public String getPhoto(int position) {
        return photo.get(position);
    }
}
