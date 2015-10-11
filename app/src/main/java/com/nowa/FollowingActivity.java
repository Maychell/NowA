package com.nowa;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nowa.R;
import com.nowa.com.adapter.FeedAdapter;
import com.nowa.com.adapter.SubjectAdapter;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class FollowingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SubjectAdapter mAdapter;
    private List<Subject> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSubjects();
    }

    private void loadSubjects() {
        List<Subject> subjects = new ArrayList<>();

        subjects.add(new Subject("Android", "Desenvolvimento Android"));
        subjects.add(new Subject("Database", "Database"));
        subjects.add(new Subject("Geometry", "Geometry"));

        mAdapter = new SubjectAdapter(this, subjects);
        mRecyclerView.setAdapter(mAdapter);
    }
}
