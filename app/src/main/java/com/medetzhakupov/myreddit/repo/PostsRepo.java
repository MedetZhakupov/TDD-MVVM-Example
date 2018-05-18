package com.medetzhakupov.myreddit.repo;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.medetzhakupov.myreddit.model.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Medet Zhakupov.
 */
@Singleton
public class PostsRepo {

    private final BehaviorRelay<List<Post>> postListRelay = BehaviorRelay.create();
    private final List<Post> postList = new ArrayList<>(0);

    @Inject
    public PostsRepo() {

    }

    public void addPost(Post post) {
        postList.add(post);
        postListRelay.accept(postList);
    }

    public BehaviorRelay<List<Post>> getObservablePostList() {
        return postListRelay;
    }

    public List<Post> getPostList() {
        return postList;
    }
}
