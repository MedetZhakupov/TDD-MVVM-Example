package com.medetzhakupov.myreddit.di;

import android.app.Activity;

import com.medetzhakupov.myreddit.ui.home.HomeActivity;
import com.medetzhakupov.myreddit.ui.home.PostItemViewModel;
import com.medetzhakupov.myreddit.ui.post.CreatePostActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface HomeActivityBuilders {

    @Binds
    Activity provideActiity(HomeActivity activity);

    @Binds
    PostItemViewModel.OnVoteChangeListener provideOnVoteChangeListener(HomeActivity activity);

    @ContributesAndroidInjector
    HomeActivity homeActivity();
}
