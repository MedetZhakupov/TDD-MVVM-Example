package com.medetzhakupov.myreddit.ui.home;

import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.repo.PostsRepo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Medet Zhakupov.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeViewModelTest {

    private HomeViewModel viewModel;
    private PostsRepo repo;

    @Before
    public void setUp() {

        repo = new PostsRepo();
        viewModel = new HomeViewModel(repo);
    }

    @Test
    public void updatePost() {
        Post post = new Post(0, "test");
        repo.addPost(post);
        post.setVoteStatus(2);
        viewModel.updatePost(0, post);
        assertEquals(repo.getPostList().get(0).getVoteStatus(), post.getVoteStatus());
    }
}