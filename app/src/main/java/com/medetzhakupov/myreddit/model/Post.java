package com.medetzhakupov.myreddit.model;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import org.parceler.Parcel;

@Parcel
public class Post {

    protected int id;
    protected String text;
    protected int vote;
    protected int voteStatus;

    public Post() {}

    public Post(int id, @NonNull String text) {
        this.id = id;
        this.text = text;
    }

    public Post(@NonNull String text, int vote) {
        this.text = text;
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getVote() {
        return vote;
    }

    public void setVoteStatus(int voteStatus) {
        this.voteStatus = voteStatus;
    }

    public int getVoteStatus() {
        return voteStatus;
    }

    public void incrementVote() {
        vote++;
    }

    public void decrementVote() {
        if (vote > 0) {
            vote--;
        }
    }
}
