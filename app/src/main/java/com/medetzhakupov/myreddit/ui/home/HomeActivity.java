package com.medetzhakupov.myreddit.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.medetzhakupov.myreddit.R;
import com.medetzhakupov.myreddit.databinding.ActivityMainBinding;
import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.ui.BaseActivity;
import com.medetzhakupov.myreddit.ui.Navigator;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HomeActivity extends BaseActivity implements PostItemViewModel.OnVoteChangeListener {

    private static final String KEY_RECYCLER_STATE = "key_recycler_state";
    @Inject
    Navigator navigator;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    PostsAdapter adapter;

    ActivityMainBinding binding;
    HomeViewModel viewModel;
    Bundle state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = savedInstanceState;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);

        binding.appbar.toolbar.setTitle(R.string.posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.list.setLayoutManager(layoutManager);
        binding.list.setAdapter(adapter);
        binding.setShowEmptyState(adapter.isEmpty());
        binding.fab.setOnClickListener(v -> navigator.navigateToCreatePost());
        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.fab.getVisibility() == View.VISIBLE) {
                    binding.fab.hide();
                } else if (dy < 0 && binding.fab.getVisibility() != View.VISIBLE) {
                    binding.fab.show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_RECYCLER_STATE, binding.list.getLayoutManager().onSaveInstanceState());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Disposable[] subscriptions() {
        return new Disposable[]{
                viewModel.getPosts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(posts -> {
                    PostsAdapter adapter = (PostsAdapter) binding.list.getAdapter();
                    adapter.addPosts(posts);
                    binding.setShowEmptyState(adapter.isEmpty());
                    if (state != null) binding.list.getLayoutManager().onRestoreInstanceState(state.getParcelable(KEY_RECYCLER_STATE));
                })
        };
    }

    @Override
    public void onVoteChange(int position, Post post) {
        viewModel.updatePost(position, post);
    }
}
