package com.nowa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nowa.com.adapter.FeedAdapter;
import com.nowa.com.dao.DaoPost;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 07/10/2015.
 */
public class SubjectFeedActivity extends DrawerActivity {

    private RecyclerView mRecyclerView;
    private FeedAdapter mAdapter;
    private List<Post> posts;
    private TextView subjectTitle;
    public static Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_feed);

        posts = new ArrayList<>();

        subjectTitle = (TextView) findViewById(R.id.subject_name);

        subjectTitle.setText(subject.getName());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFeed();

        loadDrawer(savedInstanceState);
    }

    private void loadFeed() {
        DaoPost daoPost = null;
        try {
            daoPost = new DaoPost(this);
            Map<String, String> params = new HashMap<>();
            params.put(Post.SUBJECT, subject.getId());
            posts = daoPost.getPosts(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(posts != null && !posts.isEmpty()) {
            mAdapter = new FeedAdapter(this, posts);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, "Não existem postagens para a turma: " + subject.getName(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
