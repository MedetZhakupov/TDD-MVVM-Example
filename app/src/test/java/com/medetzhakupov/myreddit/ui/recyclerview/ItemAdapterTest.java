package com.medetzhakupov.myreddit.ui.recyclerview;

import android.database.Observable;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.medetzhakupov.myreddit.ui.recyclerview.ItemAdapter.ID_COUNTER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by MEDETZ on 7/28/2017.
 */
public class ItemAdapterTest {

    private ItemAdapter adapter;
    private RecyclerView.AdapterDataObserver observer;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        adapter = new TestItemAdapter();
        observer = fixAdapterForTesting(adapter);
    }

    @After
    public void tearDown() throws Exception {
        // reset our model ID counter
        ID_COUNTER.set(0);
    }

    @Test
    public void testAddModel() {
        assertEquals(0, adapter.getItemCount());

        adapter.addModel(new TestModel());
        verify(observer).onItemRangeInserted(0, 1);
        assertEquals(1, adapter.getItemCount());

        adapter.addModel(new TestModel());
        verify(observer).onItemRangeInserted(1, 1);
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void testAddModels() {
        List<TestModel> testModels =
                Arrays.asList(new TestModel(),
                        new TestModel(),
                        new TestModel());
        adapter.addModels(testModels);
        verify(observer).onItemRangeInserted(0, 3);
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void testUpdateModels() {
        List<TestModel> testModels =
                Arrays.asList(new TestModel(1),
                        new TestModel(2),
                        new TestModel(3));

        adapter.addModels(testModels);
        verify(observer).onItemRangeInserted(0, 3);

        List<TestModel> updatedModels =
                Arrays.asList(new TestModel(1),
                        new TestModel(2));

        adapter.updateModels(updatedModels);
        verify(observer).onItemRangeChanged(1, 2, ItemAdapter.ITEMS_UPDATED);
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void testClear() {
        List<TestModel> testModels =
                Arrays.asList(new TestModel(),
                        new TestModel(),
                        new TestModel());
        adapter.addModels(testModels);
        adapter.clear();
        verify(observer).onChanged();
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void testGetModel() {
        TestModel model = new TestModel();
        adapter.addModel(model);
        assertEquals(model, adapter.getModel(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetModelBadPostionThrows() {
        adapter.getModel(100);
    }

    @Test
    public void testGetId() {
        TestModel model = new TestModel(999);
        adapter.addModel(model);
        assertEquals(999, adapter.getItemId(0));
    }

    @Test
    public void testAutoIdCountsDown() {
        List<TestModel> testModels =
                Arrays.asList(new TestModel(),
                        new TestModel(),
                        new TestModel(),
                        new TestModel());
        adapter.addModels(testModels);
        assertEquals(-1, adapter.getItemId(0));
        assertEquals(-2, adapter.getItemId(1));
        assertEquals(-3, adapter.getItemId(2));
        assertEquals(-4, adapter.getItemId(3));
    }

    @Test
    public void testKeyIdGeneration() {
        List<TestModel> testModels =
                Arrays.asList(new TestModel("1"),
                        new TestModel("2"),
                        new TestModel("3"),
                        new TestModel("4"));
        adapter.addModels(testModels);

        assertEquals(-1, adapter.getItemId(0));
        assertEquals(-2, adapter.getItemId(1));
        assertEquals(-3, adapter.getItemId(2));
        assertEquals(-4, adapter.getItemId(3));
        assertEquals(4, adapter.getItemCount());

        List<TestModel> updatedModels =
                Arrays.asList(new TestModel("1"),
                        new TestModel("3"));

        adapter.updateModels(updatedModels);

        assertEquals(-1, adapter.getItemId(0));
        assertEquals(-3, adapter.getItemId(1));
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(adapter.isEmpty());
        TestModel model = new TestModel();
        adapter.addModel(model);
        assertFalse(adapter.isEmpty());
    }

    @Test
    public void testHolderMethods() {
        TestModel model = new TestModel();
        adapter.addModel(model);
        ItemModelViewHolder holder = mock(ItemModelViewHolder.class);
        adapter.onBindViewHolder(holder, 0);
        verify(holder).bind(model);
        adapter.onViewRecycled(holder);
        verify(holder).unbind();
        adapter.onViewAttachedToWindow(holder);
        verify(holder).onAttachedToWindow();
        adapter.onViewDetachedFromWindow(holder);
        verify(holder).onDetachedFromWindow();
    }

    private class TestModel extends BaseItemModel {

        private String key;

        TestModel() {
        }

        TestModel(long id) {
            setId(id);
        }

        TestModel(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
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

    private class TestItemAdapter extends ItemAdapter {

        @Override
        public void setHasStableIds(final boolean hasStableIds) {
            // override as this fires in our ItemAdapter constructor before the
            // mObservable is mocked
        }
    }

    /**
     * Fixes internal dependencies to android.database.Observable so that a RecyclerView.Adapter
     * can be tested using regular unit tests
     */
    private static RecyclerView.AdapterDataObserver fixAdapterForTesting(RecyclerView.Adapter adapter)
            throws NoSuchFieldException, IllegalAccessException {
        // Observables are not mocked by default so we need to hook the adapter up to an observer so we can track changes
        Field observableField = RecyclerView.Adapter.class.getDeclaredField("mObservable");
        observableField.setAccessible(true);
        Object observable = observableField.get(adapter);
        Field observersField = Observable.class.getDeclaredField("mObservers");
        observersField.setAccessible(true);
        final ArrayList<Object> observers = new ArrayList<>();
        RecyclerView.AdapterDataObserver dataObserver = mock(RecyclerView.AdapterDataObserver.class);
        observers.add(dataObserver);
        observersField.set(observable, observers);
        return dataObserver;
    }
}