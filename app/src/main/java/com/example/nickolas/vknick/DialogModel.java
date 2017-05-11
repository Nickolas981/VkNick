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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nickolas on 08.05.2017.
 */

public class DialogModel extends ActionBarActivity {
    public String[] photo;
    //    public ArrayList<String> photo;
    public ArrayList<String> fullName;
    public ArrayList<String> lastMessage;
    public int[] id;
    //    public ArrayList<Integer> id;
    public ListView listView;
    public String mPhoto;
    public ArrayList<Boolean> out;
    public ArrayList<Boolean> readed;
    public Boolean[] online;
    public Boolean[] onlinePhone;
    public ArrayList<Boolean> isChat;

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
                photo = new String[list.size()];
                id = new int[list.size()];
                out = new ArrayList<Boolean>();
                readed = new ArrayList<Boolean>();
                online = new Boolean[list.size()];
                onlinePhone = new Boolean[list.size()];
                isChat = new ArrayList<Boolean>();

                try {
                    JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
                    System.out.println("asdasdasd");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i).getJSONObject("message");
                        if (!(object.getString("title").equals("") || object.getString("title").equals(" ... "))) {
                            photo[i] = object.getString("photo_50");
                            id[i] = object.getInt("chat_id") + 2000000000;
                            online[i] = false;
                            onlinePhone [i] = false;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("_________________________________________________________________________");
                }

                for (int i = 0; i < list.size(); i++) {
                    lastMessage.add(list.get(i).message.body);
                    fullName.add(list.get(i).message.title);
                    if (id[i] == 0) {
                        id[i] = list.get(i).message.user_id;
                    }
                    out.add(list.get(i).message.out);
                    readed.add(list.get(i).message.read_state);
                }
                setPhoto(adapter);
            }
        });
    }


    private String convertId() {
        String result = "";

        for (int i = 0; i < id.length; i++) {
            if (id[i] < 2000000000) {
                result += " ," + id[i];
            }
        }

        return result;
    }

    private void setPhoto(final CustomDialogAdapter adapter) {
        int m = 0;
        final VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, convertId(), VKApiConst.FIELDS, "photo_50, online, online_mobile"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                int m = 0;
                VKList<VKApiUser> userVKList = (VKList<VKApiUser>) response.parsedModel;

                for (int i = 0; i < userVKList.size(); i++) {
                    if (fullName.get(i + m).equals(" ... ") || fullName.get(i + m).equals("")) {
                        photo[i + m] = userVKList.get(i).photo_50;
                        fullName.remove(i + m);
                        fullName.add(i + m, userVKList.get(i).first_name + " " + userVKList.get(i).last_name);
                        online[i + m] = userVKList.get(i).online;
                        onlinePhone[i + m] = userVKList.get(i).online_mobile;
                    }
                    else{
                        m++;
                        photo[i + m] = userVKList.get(i).photo_50;
                        fullName.remove(i + m);
                        fullName.add(i + m, userVKList.get(i).first_name + " " + userVKList.get(i).last_name);
                        online[i + m] = userVKList.get(i).online;
                        onlinePhone[i + m] = userVKList.get(i).online_mobile;

                    }
                }
                setMyPhoto();
                if (adapter != null) {
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private void setMyPhoto() {
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
        return photo[position];
    }
}
