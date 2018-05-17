package com.medetzhakupov.myreddit.ui.home;

import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.ui.recyclerview.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Medet Zhakupov.
 */
public class PostsAdapter extends ItemAdapter<PostItemViewModel> {

    private final PostItemViewModel.OnVoteChangeListener onVoteChangeListener;

    @Inject
    public PostsAdapter(PostItemViewModel.OnVoteChangeListener onVoteChangeListener) {
        this.onVoteChangeListener = onVoteChangeListener;
    }

    public void addPosts(List<Post> posts) {
        List<PostItemViewModel> models = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            models.add(new PostItemViewModel(posts.get(i), i, onVoteChangeListener));
        }
        updateModels(models);
    }
}
