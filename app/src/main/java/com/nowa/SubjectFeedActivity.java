package com.nowa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowa.com.adapter.FeedAdapter;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 07/10/2015.
 */
public class SubjectFeedActivity extends AppCompatActivity {

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFeed();
    }

    private void loadFeed() {
        User user1 = new User("@maychell", "fdlkfhsdjbfablkjrehjl123", "Maychell Fernandes", "Engenharia de Software", "null", "20125412", "maychellfernandes@hotmail.com");

        posts.add(new Post("10/12/2014", "19:12", user1, "@Algebra20152 Prova MUUUUUITO DOIDA!!!!"));

        mAdapter = new FeedAdapter(this, posts);
        mRecyclerView.setAdapter(mAdapter);
    }

}
