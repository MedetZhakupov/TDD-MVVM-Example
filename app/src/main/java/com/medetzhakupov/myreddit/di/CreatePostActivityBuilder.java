package com.medetzhakupov.myreddit.di;

import android.app.Activity;

import com.medetzhakupov.myreddit.ui.post.CreatePostActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Medet Zhakupov.
 */
@Module
public interface CreatePostActivityBuilder {

    @ContributesAndroidInjector
    CreatePostActivity createPostActivity();
}
