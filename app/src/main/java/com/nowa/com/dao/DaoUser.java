package com.nowa.com.dao;

import android.content.Context;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 21/11/2015.
 */
public class DaoUser extends GeneralDao {

    public DaoUser(Context ctx) {
        super(ctx);
    }

    @Override
    public HashMap<String, String> parseObjectToMap(Object obj) {
        User user = (User) obj;

        HashMap<String, String> values = new HashMap<>();
        values.put(User._ID, user.getId());
        values.put(User.LOGIN, user.getLogin());
        values.put(User.NAME, user.getName());
        values.put(User.TOKEN, user.getToken());
        values.put(User.COURSE, user.getCourse());
        values.put(User.DESCRIPTION, user.getDescription());
        values.put(User.REGISTER_NUMBER, user.getRegisterNumber());
        values.put(User.EMAIL, user.getEmail());

        return values;
    }

    public User getUserByLogin(String login) {
        User user = null;

        CloudQueries cloudQueries = new CloudQueries(ctx);

        List<ParseObject> results = cloudQueries.getObject("user", User.LOGIN, login);

        if(results != null && !results.isEmpty()) {
            ParseObject po = results.get(0);
            user = new User(po.getObjectId(), po.getString(User.LOGIN), po.getString(User.TOKEN),
                    po.getString(User.NAME), po.getString(User.COURSE), po.getString(User.DESCRIPTION),
                    po.getString(User.REGISTER_NUMBER), po.getString(User.EMAIL));
        }

        return user;
    }

    public void getUserById(String idUser) {
        CloudQueries cloudQueries = new CloudQueries(ctx);

        List<ParseObject> results = cloudQueries.getObject("user", "objectId", idUser);
        ParseObject po = results.get(0);
        Parameter.user = new User(po.getObjectId(), po.getString(User.LOGIN), po.getString(User.TOKEN),
                po.getString(User.NAME), po.getString(User.COURSE), po.getString(User.DESCRIPTION),
                po.getString(User.REGISTER_NUMBER), po.getString(User.EMAIL));
    }

    public void save(User user) {
        service("save", "user", parseObjectToMap(user), true);
    }

}
