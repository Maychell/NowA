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
import com.nowa.com.utils.GetPostsBroadcastReceiver;
import com.nowa.com.utils.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class FollowingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SubjectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        GetPostsBroadcastReceiver.intent = getIntent();

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
        /*
        List<Subject> subjects = new ArrayList<>();

        subjects.add(new Subject("@android", "Desenvolvimento Android", "DIM201"));
        subjects.add(new Subject("@database", "Database", "KF1232"));
        subjects.add(new Subject("@geometry", "Geometry", "SDF324"));

        */

        mAdapter = new SubjectAdapter(this, Parameter.subjects);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetPostsBroadcastReceiver.intent = null;
    }
}
