package com.medetzhakupov.myreddit.ui.post;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.medetzhakupov.myreddit.R;
import com.medetzhakupov.myreddit.databinding.ActivityCreatePostBinding;
import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.ui.BaseActivity;

import javax.inject.Inject;

/**
 * Created by Medet Zhakupov.
 */
public class CreatePostActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ActivityCreatePostBinding binding;
    CreatePostViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreatePostViewModel.class);
        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setTitle(R.string.text_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.post:
                if (TextUtils.isEmpty(binding.text.getText())) {
                    Snackbar.make(binding.getRoot(), R.string.text_cannotbe_empty, Snackbar.LENGTH_SHORT).show();
                } else {
                    viewModel.addPost(binding.text.getText().toString());
                    finish();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
