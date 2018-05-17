package com.medetzhakupov.myreddit.ui;

import android.app.Activity;
import android.content.Intent;

import com.medetzhakupov.myreddit.ui.post.CreatePostActivity;

import javax.inject.Inject;

/**
 * Created by Medet Zhakupov.
 */
public class Navigator {

    private final Activity activity;

    @Inject
    public Navigator(Activity activity) {
        this.activity = activity;
    }


    public void navigateToCreatePost() {
        Intent intent = new Intent(activity, CreatePostActivity.class);
        activity.startActivity(intent);
    }
}
