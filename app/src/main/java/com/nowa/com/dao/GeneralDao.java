package com.nowa.com.dao;

import android.content.Context;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 13/10/2015.
 */
public class GeneralDao {

    private Context ctx;

    public GeneralDao(Context ctx) {
        this.ctx=ctx;
    }

    public boolean service(String methodName, String className, HashMap<String, String> params, boolean cloudPersist) {
        boolean result=false;
        switch (methodName) {
            case "save":
                result=save(className, params, cloudPersist);
            case "delete":
                result=delete(className, params, cloudPersist);
        }
        return result;
    }

    public List<Post> getPosts() {
        CloudQueries cloud = new CloudQueries(ctx);
        List<ParseObject> result = cloud.getAllObjects("post");

        List<Post> posts = new ArrayList<>();

        for(ParseObject obj : result) {
            //id, String date, String time, User user, String message
            User u = new User();
            u.setId(obj.get(Post.USER).toString());
            Post post = new Post(obj.get(Post._ID).toString(), obj.get(Post.DATE).toString(), obj.get(Post.TIME).toString(),
                    Parameter.user, obj.get(Post.MESSAGE).toString());
            posts.add(post);
        }

        return posts;
    }

    public boolean save(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(params.get("id").equals(""))
            insert(className, params, cloudPersist);
        else
            update(className, params, cloudPersist);
        return true;
    }

    private boolean insert(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.save(className, params);
        }
        return true;
    }

    private boolean update(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.updateInBackground(className, params, params.get("id"));
        }
        return true;
    }

    private boolean delete(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.deleteObject(className, "id", params.get("id"));
        }
        return true;
    }
}
