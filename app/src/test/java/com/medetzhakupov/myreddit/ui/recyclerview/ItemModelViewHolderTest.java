package com.medetzhakupov.myreddit.ui.recyclerview;

import android.databinding.ViewDataBinding;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by MEDETZ on 7/28/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ItemModelViewHolderTest {

    private ItemModelViewHolder viewHolder;

    private ViewDataBinding binding;
    private View view;
    private ItemModel model;
    private ItemHolder innerHolder;

    @Before
    public void setUp() throws Exception {
        view = mock(View.class);
        binding = mock(ViewDataBinding.class);
        model = mock(ItemModel.class);
        when(binding.getRoot()).thenReturn(view);
        viewHolder = new ItemModelViewHolder(binding);
        innerHolder = mock(ItemHolder.class);
        when(model.createHolder(binding)).thenReturn(innerHolder);
    }

    @Test
    public void testBind() {
        viewHolder.bind(model);
        verify(model).createHolder(binding);
        verify(innerHolder).bind(model);
    }

    @Test
    public void testUnbind() {
        viewHolder.bind(model);
        viewHolder.unbind();
        verify(innerHolder).unbind();
    }

    @Test
    public void testOnAttachedToWindow() {
        viewHolder.bind(model);
        viewHolder.onAttachedToWindow();
        verify(innerHolder).attached();
    }

    @Test
    public void testOnDetachedFromWindow() {
        viewHolder.bind(model);
        viewHolder.onDetachedFromWindow();
        verify(innerHolder).detached();
    }

}
