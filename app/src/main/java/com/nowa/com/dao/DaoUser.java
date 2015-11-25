package com.nowa.com.dao;

import android.content.Context;
import android.database.Cursor;

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

        try {
            //Try to retrieve to get user from local database
            Cursor c = getAllCursors("user", User.COLUMNS, User._ID+"='"+idUser+"'");

            if(c.moveToFirst()) {

                int idxid = c.getColumnIndex(User._ID);
                int idxlogin = c.getColumnIndex(User.LOGIN);
                int idxtoken = c.getColumnIndex(User.TOKEN);
                int idxname = c.getColumnIndex(User.NAME);
                int idxcourse = c.getColumnIndex(User.COURSE);
                int idxdescription = c.getColumnIndex(User.DESCRIPTION);
                int idxregisterNumber = c.getColumnIndex(User.REGISTER_NUMBER);
                int idxemail = c.getColumnIndex(User.EMAIL);

                Parameter.user = new User(c.getString(idxid), c.getString(idxlogin), c.getString(idxtoken),
                        c.getString(idxname), c.getString(idxcourse), c.getString(idxdescription),
                        c.getString(idxregisterNumber), c.getString(idxemail));
                return;
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        CloudQueries cloudQueries = new CloudQueries(ctx);

        List<ParseObject> results = cloudQueries.getObject("user", "objectId", idUser);
        ParseObject po = results.get(0);
        Parameter.user = new User(po.getObjectId(), po.getString(User.LOGIN), po.getString(User.TOKEN),
                po.getString(User.NAME), po.getString(User.COURSE), po.getString(User.DESCRIPTION),
                po.getString(User.REGISTER_NUMBER), po.getString(User.EMAIL));

        save(Parameter.user, false);
    }

    public void save(User user, boolean... cloud) {
        service("save", "user", parseObjectToMap(user), (cloud != null ? cloud[0] : true));
    }

}
