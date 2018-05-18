package com.medetzhakupov.myreddit.ui.post;

import com.medetzhakupov.myreddit.repo.PostsRepo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Medet Zhakupov.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreatePostViewModelTest {

    private CreatePostViewModel viewModel;
    private PostsRepo repo;

    @Before
    public void setUp() {
        repo = new PostsRepo();
        viewModel = new CreatePostViewModel(repo);
    }

    @Test
    public void addPost() {
        String text = "test";

        assertEquals(repo.getPostList().size(), 0);
        viewModel.addPost(text);
        assertEquals(repo.getPostList().size(), 1);
        assertEquals(repo.getPostList().get(0).getText(), text);
    }
}