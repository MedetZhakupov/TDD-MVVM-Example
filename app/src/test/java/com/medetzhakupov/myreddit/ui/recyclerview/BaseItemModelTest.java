package com.medetzhakupov.myreddit.ui.recyclerview;

/**
 * Created by MEDETZ on 7/28/2017.
 */

import android.databinding.ViewDataBinding;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.medetzhakupov.myreddit.ui.recyclerview.ItemAdapter.ID_COUNTER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by MEDETZ on 7/28/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseItemModelTest {

    @After
    public void tearDown() throws Exception {
        // reset our model ID counter
        ID_COUNTER.set(0);
    }

    @Test
    public void testGetId() {
        TestModel testModel = new TestModel(999L);
        assertEquals(999L, testModel.getId().longValue());
    }

    @Test
    public void testIsTheSameItemDefault() {
        // defaults to checking if layout id's are equal
        BaseItemModel itemOne = new TestModel(1);
        BaseItemModel itemTwo = new TestModel(2);
        assertTrue(itemOne.isTheSameItem(itemTwo));
    }

    @Test
    public void testHasTheSameContentsDefault() {
        // defaults to checking if layout id's are equal
        BaseItemModel itemOne = new TestModel(1);
        BaseItemModel itemTwo = new TestModel(2);
        assertFalse(itemOne.hasTheSameContents(itemTwo));
    }

    private class TestModel extends BaseItemModel {

        TestModel() {
        }

        TestModel(final long id) {
            super();
            setId(id);
        }

        @Override
        public String getKey() {
            return null;
        }

        @Override
        public int getLayoutId() {
            return 0;
        }

        @Override
        public ItemHolder createHolder(final ViewDataBinding binding) {
            return null;
        }

    }
}
