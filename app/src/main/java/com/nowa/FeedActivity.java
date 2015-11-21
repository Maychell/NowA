package com.nowa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.nowa.com.adapter.FeedAdapter;
import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.dao.DaoPost;
import com.nowa.com.dao.DaoSubject;
import com.nowa.com.dao.GeneralDao;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.Subject;
import com.nowa.com.utils.CustomTokenizer;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FeedActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private FeedAdapter mAdapter;
    private List<Post> posts;
    private ImageView btnSend;
    private MultiAutoCompleteTextView txtMessage;

    private String[] language;/* = new String[] {
            "abc",
            "abcd",
            "abcde",
            "abcdef",
            "abcdefg",
            "@hij",
            "@hijk",
            "@hijkl",
            "@hijklm",
            "@hijklmn",
    };
    */
//    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        posts = new ArrayList<>();
        btnSend = (ImageView) findViewById(R.id.btn_send_message);
        txtMessage = (MultiAutoCompleteTextView) findViewById(R.id.mult_txt_message);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.support.v7.appcompat.R.layout.select_dialog_item_material, language);
        txtMessage.setAdapter(adapter);
        txtMessage.setThreshold(2);
        txtMessage.setTokenizer(new CustomTokenizer());
        btnSend.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //metodo para pegar turmas no sigaa, salvar na nuvem e pega-las da nuvem


    @Override
    protected void onResume() {
        super.onResume();
        loadSubjects();
        loadFeed();
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
                List<Integer> numbers = new ArrayList<>();
                for (int i = 0; i < 4; ++i) {
                    int randomNum = 0;
                    do {
                        Random rand = new Random();
                        randomNum = rand.nextInt(15) + 1;
                    } while (numbers.contains(randomNum));
                    numbers.add(randomNum);

                    List<ParseObject> results = cloudQueries.getObject("subject", "number", "" + randomNum);

                    if (results.size() > 0) {
                        //Subject(String id, String name, String nameSigaa, String subjectCode)
                        ParseObject po = results.get(0);
                        Subject subject = new Subject(po.getObjectId(), po.getString(Subject.NAME), po.getString(Subject.NAME_SIGAA), po.getString(Subject.SUBJECT_CODE));
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

        /*
        User user1 = new User("@maychell", "fdlkfhsdjbfablkjrehjl123", "Maychell Fernandes", "Engenharia de Software", "null", "20125412", "maychellfernandes@hotmail.com");
        User user2 = new User("@rafaelfq", "fdlkfhsdjbfablkjrehjl123", "Rafael Fernandes", "Engenharia de Software", "null", "20104324", "rafael_v1d4_l0k4@hotmail.com");
        User user3 = new User("@itamirxd", "fdlkfhsdjbfablkjrehjl123", "Itamir Francisco", "Ciência da Computação", "null", "120334345", "itamir_ufrn@hotmail.com");

        posts.add(new Post("10/12/2014", "19:12", user1, "@Algebra20152 Prova MUUUUUITO DOIDA!!!!"));
        posts.add(new Post("10/12/2014", "19:12", user2, "@PIU20152 Atividade pra amanhã, galera :x"));
        posts.add(new Post("10/12/2014", "19:12", user3, "@DEVAndroid #Notas lancadas :p"));
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
            Post post = new Post("14/10/2015", "19:12", Parameter.user, txtMessage.getText().toString());
            //posts.add(post);

            HashMap<String, String> params = new HashMap<>();
            params.put(Post._ID, "");
            params.put(Post.DATE, post.getDate());
            params.put(Post.TIME, post.getTime());
            params.put(Post.USER, post.getUser().getId());
            params.put(Post.MESSAGE, post.getMessage());

            DaoPost daoPost = null;
            try {
                daoPost = new DaoPost(this);
                daoPost.service("save", "post", params, true);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if(daoPost != null)
                    daoPost.close();
            }

            post = null;
            /*
            mAdapter = new FeedAdapter(this, posts);
            mRecyclerView.setAdapter(mAdapter);
            */
            txtMessage.setText("");
            loadFeed();
        }
    }
}
