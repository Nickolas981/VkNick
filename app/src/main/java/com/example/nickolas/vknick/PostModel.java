package com.example.nickolas.vknick;

import java.util.ArrayList;

/**
 * Created by Nickolas on 14.05.2017.
 */

public class PostModel {
    private String time, text;


    private ArrayList<String> postPhotos;
    private int likes, shares, comments, sourceID, ID;
    private boolean isLiked, canComment, isAd;

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {

        return group;
    }

    private Group group;

    public String getText() {
        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public PostModel() {
        postPhotos = new ArrayList<String>();
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPostPhotos(ArrayList<String> postPhotos) {
        this.postPhotos = postPhotos;
    }

    public void addPostPhoto(String photo) {
        postPhotos.add(photo);
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isAd() {
        return isAd;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;

    }

    public void setIsAd(boolean isAd) {
        this.isAd = isAd;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<String> getPostPhotos() {
        return postPhotos;
    }

    public int getLikes() {
        return likes;
    }

    public int getShares() {
        return shares;
    }

    public int getComments() {
        return comments;
    }

    public int getSourceID() {
        return sourceID;
    }

    public int getID() {
        return ID;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public boolean isCanComment() {
        return canComment;
    }
}
