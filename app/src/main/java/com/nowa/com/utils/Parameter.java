package com.nowa.com.utils;

import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;

import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 13/10/2015.
 */
public class Parameter {

    public static final int ACTION_LOGIN = 1;
    public static final int ACTION_CHECK_LOGGED_IN = 2;
    public static final int ACTION_START_FEED = 3;
    public static final int ACTION_SAVE = 4;
    public static final int ACTION_LOAD_FEED = 5;

    public static User user;

    public static List<Subject> subjects;

    public static String[] getSubjectTagging() {
        String[] subjectTagging = null;
        if(subjects != null) {
            subjectTagging = new String[subjects.size()];

            for(int i=0; i<subjects.size(); ++i)
                subjectTagging[i] = subjects.get(i).getName();
        }

        return subjectTagging;
    }

    public static final String MY_PREFERENCES = "NowaMyPrefs";
}
