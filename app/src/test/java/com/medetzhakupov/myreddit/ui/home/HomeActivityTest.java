package com.medetzhakupov.myreddit.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.medetzhakupov.myreddit.BuildConfig;
import com.medetzhakupov.myreddit.R;
import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.ui.post.CreatePostActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;
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
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(0, "Post one"));
        posts.add(new Post(1, "Post two"));
        posts.add(new Post(2, "Post three"));

        HomeActivity activity = Robolectric.setupActivity(HomeActivity.class);
        PostsAdapter adapter = (PostsAdapter) activity.binding.list.getAdapter();
        adapter.addPosts(posts);
        activity.binding.setShowEmptyState(adapter.isEmpty());

        assertEquals(activity.binding.list.getChildCount(), posts.size());
        assertThat(activity.binding.emptyText).isNotVisible();
    }

    @Test
    public void testUpvote() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(0, "Post one"));

        HomeActivity activity = Robolectric.setupActivity(HomeActivity.class);
        activity.viewModel.getPosts();
        PostItemViewModel.OnVoteChangeListener onVoteChangeListener =
                Mockito.mock(PostItemViewModel.OnVoteChangeListener.class);
        PostsAdapter adapter = new PostsAdapter(onVoteChangeListener);
        adapter.addPosts(posts);
        activity.binding.list.setAdapter(adapter);

        TextView tv = activity.binding.list.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.vote);
        tv.dispatchTouchEvent(buildMotionEventUp(tv, tv.getWidth()/3, tv.getHeight()/2));

        assertEquals(adapter.getModel(0).post.getVote(), 1);
        Mockito.verify(onVoteChangeListener).onVoteChange(eq(0), Mockito.any(Post.class));
        assertEquals(tv.getCurrentTextColor(), Color.RED);
    }

    @Test
    public void testDownvote() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(0, "Post one"));

        HomeActivity activity = Robolectric.setupActivity(HomeActivity.class);
        activity.viewModel.getPosts();
        PostItemViewModel.OnVoteChangeListener onVoteChangeListener =
                Mockito.mock(PostItemViewModel.OnVoteChangeListener.class);
        PostsAdapter adapter = new PostsAdapter(onVoteChangeListener);
        adapter.addPosts(posts);
        activity.binding.list.setAdapter(adapter);

        TextView tv = activity.binding.list.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.vote);
        tv.dispatchTouchEvent(buildMotionEventUp(tv, tv.getWidth()/3, tv.getHeight()/2));
        tv.dispatchTouchEvent(buildMotionEventUp(tv, tv.getWidth()/3, tv.getHeight()/2));
        tv.dispatchTouchEvent(buildMotionEventUp(tv, tv.getWidth() - tv.getWidth()/3, tv.getHeight()/2));

        assertEquals(adapter.getModel(0).post.getVote(), 1);
        Mockito.verify(onVoteChangeListener, Mockito.times(3)).onVoteChange(eq(0), Mockito.any(Post.class));
        assertEquals(tv.getCurrentTextColor(), Color.BLUE);
    }

    private MotionEvent buildMotionEventUp(View view, float x, float y) {
        // Obtain MotionEvent object
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        // List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
        int metaState = 0;

        return MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                metaState
        );
    }
}
