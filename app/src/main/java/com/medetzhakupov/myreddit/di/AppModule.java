package com.medetzhakupov.myreddit.di;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.medetzhakupov.myreddit.App;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    //expose Application as an injectable context
    @Singleton
    @Provides
    Context provideContext(App app) {
        return app;
    }
}