package com.medetzhakupov.myreddit.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.medetzhakupov.myreddit.ui.home.HomeViewModel;
import com.medetzhakupov.myreddit.ui.post.CreatePostViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreatePostViewModel.class)
    abstract ViewModel bindCreatePostViewModel(CreatePostViewModel createPostViewModel);
}
