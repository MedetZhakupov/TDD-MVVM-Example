package com.medetzhakupov.myreddit.ui;

import android.app.Activity;
import android.content.Intent;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Medet Zhakupov.
 */
public class NavigatorTest {

    private Navigator navigator;

    @Test
    public void shouldStartCreatePostActivity() {
        Activity activity = Mockito.mock(Activity.class);
        navigator = new Navigator(activity);

        navigator.navigateToCreatePost();
        Mockito.verify(activity).startActivity(Mockito.any(Intent.class));
    }

}