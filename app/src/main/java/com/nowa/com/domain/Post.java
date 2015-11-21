package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class Post extends Entity {

    public final static String[] COLUMNS = new String[] {
            Post._ID, Post.DATE, Post.TIME, Post.USER, Post.MESSAGE, Post.SUBJECT
    };

    private String date;
    private String time;
    private User user;
    private String message;
    private Subject subject;

    /**
     * Default constructor
     */
    public Post() {}

    /**
     * Default constructor with params
     * @param date
     * @param time
     * @param user
     * @param message
     * @param subject
     */
    public Post(String date, String time, User user, String message, Subject subject) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.message = message;
        this.subject = subject;
    }

    /**
     * Default constructor with both params and id
     * @param id
     * @param date
     * @param time
     * @param user
     * @param message
     * @param subject
     */
    public Post(String id, String date, String time, User user, String message, Subject subject) {
        this.setId(id);
        this.date = date;
        this.time = time;
        this.user = user;
        this.message = message;
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public final static String DATE="date";
    public final static String TIME="time";
    public final static String USER="id_user";
    public final static String MESSAGE="message";
    public final static String SUBJECT="id_subject";
}
