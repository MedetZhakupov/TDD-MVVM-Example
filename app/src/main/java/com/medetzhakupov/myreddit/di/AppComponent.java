package com.medetzhakupov.myreddit.di;

import com.medetzhakupov.myreddit.App;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        HomeActivityBuilders.class,
        CreatePostActivityBuilder.class,
        ViewModelModule.class,
})
public interface AppComponent extends AndroidInjector<App> {

    @Override
    void inject(App instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(App app);
        AppComponent build();

    }
}
