package com.medetzhakupov.myreddit.ui.post;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;

import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.repo.PostsRepo;

import javax.inject.Inject;

/**
 * Created by Medet Zhakupov.
 */
public class CreatePostViewModel extends ViewModel {

    private final PostsRepo postsRepo;

    @Inject
    public CreatePostViewModel(PostsRepo postsRepo) {
        this.postsRepo = postsRepo;
    }

    public void addPost(String postText) {
        Post post = new Post(postsRepo.getPostList().size(), postText);
        postsRepo.addPost(post);
    }
}
