package com.example.nickolas.vknick;

import java.util.ArrayList;

/**
 * Created by Nickolas on 11.05.2017.
 */

public class ChatModel {
    ArrayList<String> id;
    ArrayList<String> photo;
    ArrayList<String> messages;

    public ChatModel(ArrayList<String> id, ArrayList<String> photo) {
        this.id = id;
        this.photo = photo;
    }
}
