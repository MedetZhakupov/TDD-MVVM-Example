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

    @Inject
    Navigator navigator;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    PostsAdapter adapter;

    ActivityMainBinding binding;
    HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    @SuppressWarnings("unchecked")
    protected Disposable[] subscriptions() {
        return new Disposable[]{
                viewModel.getPosts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(posts -> {
                    PostsAdapter adapter = (PostsAdapter) binding.list.getAdapter();
                    adapter.addPosts(posts);
                    binding.list.scrollToPosition(adapter.getItemCount() - 1);
                    binding.setShowEmptyState(adapter.isEmpty());
                })
        };
    }

    @Override
    public void onVoteChange(int position, Post post) {
        viewModel.updatePost(position, post);
    }
}
