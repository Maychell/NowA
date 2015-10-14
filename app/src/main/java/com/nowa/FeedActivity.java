package com.nowa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nowa.com.adapter.FeedAdapter;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private FeedAdapter mAdapter;
    private List<Post> posts;
    private ImageView btnSend;
    private User user;
    private EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        posts = new ArrayList<>();
        btnSend = (ImageView) findViewById(R.id.btn_send_message);
        txtMessage = (EditText) findViewById(R.id.txt_message);
        btnSend.setOnClickListener(this);

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
        user = new User("@maychell", "fdlkfhsdjbfablkjrehjl123", "maychell", "Engenharia de Software", "null", "20125412", "maychellfernandes@hotmail.com");
        posts.add(new Post("10/12/2014", "19:12", user, "@Algebra20152 Prova MUUUUUITO DOIDA!!!!"));
        posts.add(new Post("10/12/2014", "19:12", user, "@PIU20152 Atividade pra amanhã, galera :x"));
        posts.add(new Post("10/12/2014", "19:12", user, "@DEVAndroid #Notas lancadas :p"));
        /*
        posts.add(new Post("10/12/2014", "19:12", user, "@Algebra20152 Prova MUUUUUITO DOIDA!!!![2]"));
        posts.add(new Post("10/12/2014", "19:12", user, "@Algebra20152 Nao aguento mais provas!!"));
        posts.add(new Post("10/12/2014", "19:12", user, "@Algebra20152 Saiu a #nota da prova :x"));
        posts.add(new Post("10/12/2014", "19:12", user, "@Topicos320152 Foca no trabalho, negada."));
        */

        mAdapter = new FeedAdapter(this, posts);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_send_message) {
            posts.add(new Post("10/12/2014", "19:12", user, txtMessage.getText().toString()));
            mAdapter = new FeedAdapter(this, posts);
            mRecyclerView.setAdapter(mAdapter);
            txtMessage.setText("");
        }
    }
}
