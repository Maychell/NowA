package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 23/11/2015.
 */
public class UserSubject {

    public static final String[] COLUMNS = new String[] {
            UserSubject.USER, UserSubject.SUBJECT
    };

    private User user;
    private Subject subject;

    public UserSubject() {}

    public UserSubject(User user, Subject subject) {
        this.user = user;
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public static final String USER = "id_user";
    public static final String SUBJECT = "id_subject";
}
