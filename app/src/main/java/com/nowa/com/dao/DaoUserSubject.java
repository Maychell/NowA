package com.nowa.com.dao;

import android.content.Context;

import com.nowa.com.domain.UserSubject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 23/11/2015.
 */
public class DaoUserSubject extends GeneralDao {

    public DaoUserSubject(Context ctx) {
        super(ctx);
    }

    @Override
    public HashMap<String, String> parseObjectToMap(Object obj) {
        UserSubject userSubject = (UserSubject) obj;
        HashMap<String, String> values = new HashMap<>();

        values.put(UserSubject.USER, userSubject.getUser().getId());
        values.put(UserSubject.SUBJECT, userSubject.getSubject().getId());

        return values;
    }

    public void saveAllLocal(List<UserSubject> subjects) {
        for(UserSubject sub : subjects)
            service("save", "user_subject", parseObjectToMap(sub), true);
    }

}
