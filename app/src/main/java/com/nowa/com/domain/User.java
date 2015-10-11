package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class User extends Entity {

    public final static String[] COLUMNS = new String[] {
            User._ID, User.LOGIN, User.TOKEN, User.NAME
    };

    private String login;
    private String token;
    private String name;

    /**
     * Default constructor
     */
    public User() {}

    /**
     * Default constructor with params
     * @param login
     * @param token
     * @param name
     */
    public User(String login, String token, String name) {
        this.login = login;
        this.token = token;
        this.name = name;
    }

    /**
     * Default constructor with both params and id
     * @param id
     * @param login
     * @param token
     * @param name
     */
    public User(String id, String login, String token, String name) {
        this.login = login;
        this.token = token;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public final static String LOGIN="login";
    public final static String TOKEN="token";
    public final static String NAME="name";
}
