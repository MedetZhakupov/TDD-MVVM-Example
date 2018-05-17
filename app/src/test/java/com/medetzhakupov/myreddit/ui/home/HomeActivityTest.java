package com.medetzhakupov.myreddit.ui.home;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.medetzhakupov.myreddit.BuildConfig;
import com.medetzhakupov.myreddit.R;
import com.medetzhakupov.myreddit.ui.post.CreatePostActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertEquals;
import static org.assertj.android.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityTest {


    @Test
    public void shouldShowEmptyText() {
        HomeActivity activity = Robolectric.setupActivity(HomeActivity.class);
        TextView textView = activity.findViewById(R.id.empty_text);

        assertThat(textView).hasText(R.string.no_topics_at_the_moment);
        assertThat(textView).isVisible();
    }

    @Test
    public void shouldStartCreatePost() {
        HomeActivity activity = Robolectric.setupActivity(HomeActivity.class);
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.performClick();

        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(CreatePostActivity.class, shadowIntent.getIntentClass());
    }

    @Test
    public void shouldShowPostList() {

    }
}
