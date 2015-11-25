package com.nowa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.nowa.com.adapter.FeedAdapter;
import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.dao.DaoPost;
import com.nowa.com.dao.DaoSubject;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.Subject;
import com.nowa.com.utils.CustomTokenizer;
import com.nowa.com.utils.GetPostsBroadcastReceiver;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FeedActivity extends DrawerActivity implements View.OnClickListener, GetPostsBroadcastReceiver.IUpdateFeed {

    private RecyclerView mRecyclerView;
    private FeedAdapter mAdapter;
    private List<Post> posts;
    private ImageView btnSend;
    private MultiAutoCompleteTextView txtMessage;
    private Subject subjectPostingAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        GetPostsBroadcastReceiver.intent = getIntent();

        posts = new ArrayList<>();
        btnSend = (ImageView) findViewById(R.id.btn_send_message);
        txtMessage = (MultiAutoCompleteTextView) findViewById(R.id.mult_txt_message);
        btnSend.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSubjects();
        fillAutocomplete();
        loadFeed();

        loadDrawer(savedInstanceState);
    }

    private void fillAutocomplete() {
        final ArrayAdapter<Subject> adapter = new ArrayAdapter<Subject>(this,
                android.support.v7.appcompat.R.layout.select_dialog_item_material, Parameter.subjects);
        txtMessage.setAdapter(adapter);
        txtMessage.setThreshold(2);
        txtMessage.setTokenizer(new CustomTokenizer());

        txtMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                subjectPostingAt = adapter.getItem(index);
            }
        });
    }

    private void loadSubjects() {
        DaoSubject daoSubject = null;
        try {
            if (Parameter.subjects == null || Parameter.subjects.isEmpty()) {
                daoSubject = new DaoSubject(this);
                Parameter.subjects = daoSubject.getSubjects(Parameter.user);
            }
            //it means it's the first time that the project is launched
            if (Parameter.subjects.isEmpty()) {
                //Get subjects from sigaa.
                //GET SUBJECTS FROM CLOUD, BECAUSE GET SUBJECTS ON SIGAA ISN'T WORKING§
                CloudQueries cloudQueries = new CloudQueries(this);

                //Out of 15 getting 4 random subjects
                String[] numbers = new String[4];
                for (int i = 0; i < 4; ++i) {
                    int randomNum = 0;
                    boolean has = false;
                    do {
                        has = false;
                        Random rand = new Random();
                        randomNum = rand.nextInt(15) + 1;

                        for(int j=i-1; j>=0; --j) {
                            if(numbers[j].equals(""+randomNum))
                                has = true;
                        }

                    } while (has);
                    numbers[i] = ""+randomNum;
                }

                List<ParseObject> results = cloudQueries.getObject("subject", "number", numbers);

                if (results != null && !results.isEmpty()) {
                    for(ParseObject po : results) {
                        Subject subject = new Subject(po.getObjectId(), po.getString(Subject.NAME), po.getString(Subject.NAME_SIGAA), po.getString(Subject.SUBJECT_CODE), po.getString(Subject.PICTURE));
                        Parameter.subjects.add(subject);
                    }
                }

                daoSubject = new DaoSubject(this);
                daoSubject.saveAllLocal(Parameter.subjects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(daoSubject != null)
                daoSubject.close();
        }
    }

    private void loadFeed() {

        DaoPost daoPost = null;
        try {
            daoPost = new DaoPost(this);
            posts = daoPost.getPosts();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(daoPost != null)
                daoPost.close();
        }

        mAdapter = new FeedAdapter(this, posts);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_send_message)
            newPost();
    }

    private void newPost() {
        //Checking if the user choose at least one subject to post at
        if(subjectPostingAt == null || subjectPostingAt.getId().equals("")) {
            Toast.makeText(this, "Você deve vincular a postagem a uma turma.", Toast.LENGTH_SHORT).show();
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Post post = new Post(dateFormat.format(date).split(" ")[0], dateFormat.format(date).split(" ")[1], Parameter.user, txtMessage.getText().toString(), subjectPostingAt);

        DaoPost daoPost = null;
        try {
            daoPost = new DaoPost(this);
            daoPost.save(post);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(daoPost != null)
                daoPost.close();
        }

        txtMessage.setText("");
        loadFeed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void update() {
        try {
            loadFeed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetPostsBroadcastReceiver.intent = null;
    }
}
