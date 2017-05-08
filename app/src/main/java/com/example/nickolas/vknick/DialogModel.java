package com.example.nickolas.vknick;

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

public class DialogModel {
    private ArrayList<String> photo;
    private ArrayList<String> fullName;
    private ArrayList<String> lastMessage;
    private ArrayList<Integer> id;

    public DialogModel() {
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

                for (final VKApiDialog msg : list) {
                    lastMessage.add(msg.message.body);
                    fullName.add(msg.message.title);
                    id.add(msg.message.user_id);
                }
                setPhoto();
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

    private void setPhoto() {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, convertId(), VKApiConst.FIELDS, "photo_50"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList<VKApiUser> userVKList = (VKList<VKApiUser>) response.parsedModel;

                for (VKApiUser user : userVKList) {
                    photo.add(user.photo_50);
                }
            }
        });
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
