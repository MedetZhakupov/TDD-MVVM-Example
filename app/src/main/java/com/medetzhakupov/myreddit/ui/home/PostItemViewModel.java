package com.medetzhakupov.myreddit.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.medetzhakupov.myreddit.R;
import com.medetzhakupov.myreddit.databinding.ItemPostBinding;
import com.medetzhakupov.myreddit.model.Post;
import com.medetzhakupov.myreddit.ui.recyclerview.BaseItemModel;
import com.medetzhakupov.myreddit.ui.recyclerview.ItemAdapter;
import com.medetzhakupov.myreddit.ui.recyclerview.ItemHolder;
import com.medetzhakupov.myreddit.ui.recyclerview.ItemModel;

import java.util.List;

/**
 * Created by Medet Zhakupov.
 */
public class PostItemViewModel extends BaseItemModel<ItemPostBinding, PostItemViewModel.ViewHolder> {

    Post post;
    int position;
    OnVoteChangeListener voteChangeListener;

    public PostItemViewModel(Post post, int position, OnVoteChangeListener voteChangeListener) {
        this.post = post;
        this.position = position;
        this.voteChangeListener = voteChangeListener;
    }

    @Override
    public String getKey() {
        return String.valueOf(position);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_post;
    }

    @Override
    public boolean hasTheSameContents(ItemModel itemModel) {
        Post newPost = ((PostItemViewModel) itemModel).post;
        return post.getId() == newPost.getId();
    }

    @Override
    public ViewHolder createHolder(ItemPostBinding binding) {
        return new ViewHolder(binding);
    }

    public class ViewHolder extends ItemHolder<PostItemViewModel, ItemPostBinding> {

        public ViewHolder(ItemPostBinding binding) {
            super(binding);
        }

        @Override
        protected void bind(PostItemViewModel model) {
            synchronized (PostItemViewModel.class) {
                updateContent(model, binding);
                setListeners(model, binding);
            }
        }

        private void updateContent(final PostItemViewModel model,final ItemPostBinding binding) {
            synchronized (PostItemViewModel.class) {
                binding.setPost(model.post);
                Log.d("ItemUpdate", String.format("position: %s status %s", model.position, model.post.getVoteStatus()));
                switch (model.post.getVoteStatus()) {
                    case 0:
                        binding.vote.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.secondary_text));
                        binding.vote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_24dp, 0, R.drawable.ic_downvote_24dp, 0);
                        break;
                    case 1:
                        binding.vote.setTextColor(Color.RED);
                        binding.vote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_red_24dp, 0, R.drawable.ic_downvote_24dp, 0);
                        break;
                    case 2:
                        binding.vote.setTextColor(Color.BLUE);
                        binding.vote.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_24dp, 0, R.drawable.ic_downvote_blue_24dp, 0);
                        break;
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        private void setListeners(final PostItemViewModel model, final ItemPostBinding binding) {
            binding.vote.setOnTouchListener(new CustomTouchListener(new OnVoteClickListener() {
                @Override
                public void onUpVoteClicked(Drawable drawable) {
                    if (model.post != null) {
                        model.post.incrementVote();
                        model.post.setVoteStatus(1);
                        binding.setPost(model.post);
                        model.voteChangeListener.onVoteChange(position, post);
                        updateContent(model, binding);
                    }
                }

                @Override
                public void onDownVoteClicked(Drawable drawable) {
                    if (model.post != null) {
                        model.post.decrementVote();
                        model.post.setVoteStatus(model.post.getVote() == 0 ? 0 : 2);
                        binding.setPost(model.post);
                        model.voteChangeListener.onVoteChange(model.position, model.post);
                    }
                    updateContent(model, binding);
                }
            }));
        }
    }

    public interface OnVoteClickListener {
        void onUpVoteClicked(Drawable drawable);

        void onDownVoteClicked(Drawable drawable);
    }

    public interface OnVoteChangeListener {
        void onVoteChange(int position, Post post);
    }

    public class CustomTouchListener implements View.OnTouchListener {

        private OnVoteClickListener onVoteClickListener;

        public CustomTouchListener(OnVoteClickListener voteClickListener) {
            this.onVoteClickListener = voteClickListener;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    Rect rect = new Rect();
                    v.getGlobalVisibleRect(rect);
                    int middle = (rect.right - rect.left) / 2;
                    if (middle < event.getRawX()) {
                        onVoteClickListener.onDownVoteClicked(((TextView) v).getCompoundDrawables()[2]);
                    } else {
                        onVoteClickListener.onUpVoteClicked(((TextView) v).getCompoundDrawables()[0]);
                    }
                    break;
            }
            return false;
        }
    }
}
