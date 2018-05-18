package com.medetzhakupov.myreddit.repo;

import com.medetzhakupov.myreddit.RxSchedulersOverrideRule;
import com.medetzhakupov.myreddit.model.Post;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Created by Medet Zhakupov.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostsRepoTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    private PostsRepo repo;

    TestObserver<List<Post>> testObserver;

    @Before
    public void setUp() {
        repo = new PostsRepo();
        testObserver = new TestObserver<>();
    }

    @Test
    public void shouldAddPost() {
        Post post = Mockito.mock(Post.class);
        repo.addPost(post);
        assertTrue(repo.getPostList().contains(post));
        repo.getObservablePostList().subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(repo.getPostList());
        testObserver.assertValueCount(1);
    }
}