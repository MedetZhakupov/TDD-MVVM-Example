package com.medetzhakupov.myreddit.ui.home;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;

import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.repo.PostsRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomeViewModel extends ViewModel implements LifecycleObserver {

    private final PostsRepo postsRepo;

    @Inject
    public HomeViewModel(PostsRepo postsRepo) {
        this.postsRepo = postsRepo;
    }

    public Observable<List<Post>> getPosts() {
        return postsRepo.getObservablePostList();
    }

    public void updatePost(int position, Post updatedPost) {
        Post post = postsRepo.getPostList().get(position);
        if (post.getId() == updatedPost.getId()) {
            postsRepo.getPostList().set(position, updatedPost);
        }
    }
}
