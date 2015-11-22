package com.nowa.com.dao;

import android.content.Context;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 14/10/2015.
 */
public class DaoPost extends GeneralDao {

    public DaoPost(Context ctx) {
        super(ctx);
    }

    @Override
    public HashMap<String, String> parseObjectToMap(Object obj) {
        Post post = (Post) obj;

        HashMap<String, String> values = new HashMap<>();
        values.put(Post._ID, post.getId());
        values.put(Post.DATE, post.getDate());
        values.put(Post.TIME, post.getTime());
        values.put(Post.MESSAGE, post.getMessage());
        values.put(Post.USER, post.getUser().getId());
        values.put(Post.SUBJECT, post.getSubject().getId());

        return values;
    }

    public List<Post> getPosts() {
        CloudQueries cloud = new CloudQueries(ctx);
        List<ParseObject> result = cloud.getAllObjects("post");

        List<Post> posts = new ArrayList<>();

        for(ParseObject obj : result) {
            User u = new User();
            u.setId(obj.get(Post.USER).toString());
            List<ParseObject> userResults = cloud.getObject("user", User._ID, u.getId());

            if(userResults != null && !userResults.isEmpty()) {
                ParseObject userParse = userResults.get(0);
                u.setName(userParse.getString(User.NAME));
                u.setLogin(userParse.getString(User.LOGIN));
                u.setCourse(userParse.getString(User.COURSE));
                u.setDescription(userParse.getString(User.DESCRIPTION));
                u.setEmail(userParse.getString(User.EMAIL));
                u.setRegisterNumber(userParse.getString(User.REGISTER_NUMBER));
                u.setToken(userParse.getString(User.TOKEN));
            }

            Subject subject = new Subject();
            subject.setId(obj.get(Post.SUBJECT).toString());
            Post post = new Post(obj.get(Post._ID).toString(), obj.get(Post.DATE).toString(), obj.get(Post.TIME).toString(),
                    Parameter.user, obj.get(Post.MESSAGE).toString(), subject);
            posts.add(post);
        }

        return posts;
    }

    public void save(Post post) {
        service("save", "post", parseObjectToMap(post), true);
    }

}
