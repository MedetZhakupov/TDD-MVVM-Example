package com.medetzhakupov.myreddit.ui.post;

import android.support.design.widget.Snackbar;
import android.view.MenuItem;

import com.medetzhakupov.myreddit.BuildConfig;
import com.medetzhakupov.myreddit.R;
import com.medetzhakupov.myreddit.ui.ShadowSnackbar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.assertj.android.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Medet Zhakupov.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, shadows = ShadowSnackbar.class)
public class CreatePostActivityTest {

    @Test
    public void shouldSetTitle() {
        CreatePostActivity activity = Robolectric.setupActivity(CreatePostActivity.class);
        assertEquals(activity.getSupportActionBar().getTitle(), activity.getString(R.string.text_post));
    }

    @Test
    public void shouldShowSnackBarWhenEmptyPost() {
        CreatePostActivity activity = Robolectric.setupActivity(CreatePostActivity.class);
        MenuItem menuItem = new RoboMenuItem(R.id.post);
        activity.onOptionsItemSelected(menuItem);
        assertEquals(ShadowSnackbar.getTextOfLatestSnackbar(), activity.getString(R.string.text_cannotbe_empty));
    }

    @Test
    public void shouldFinishWhenAddedPost() {
        CreatePostActivity activity = Robolectric.setupActivity(CreatePostActivity.class);
        activity.binding.text.setText("Testing add post");
        MenuItem menuItem = new RoboMenuItem(R.id.post);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = shadowOf(activity);
        assertTrue(shadowActivity.isFinishing());
    }
}