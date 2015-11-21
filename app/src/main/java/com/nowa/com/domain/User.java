package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class User extends Entity {

    public final static String[] COLUMNS = new String[] {
            User._ID, User.LOGIN, User.TOKEN, User.NAME, User.COURSE, User.DESCRIPTION, User.REGISTER_NUMBER, User.EMAIL
    };

    private String login;
    private String token;
    private String name;
    private String course;
    private String description;
    private String registerNumber;
    private String email;

    /**
     * Default constructor
     */
    public User() {}

    /**
     * Default constructor with params
     * @param login
     * @param token
     * @param name
     * @param course
     * @param description
     * @param registerNumber
     * @param email
     */
    public User(String login, String token, String name, String course, String description, String registerNumber, String email) {
        this.login = login;
        this.token = token;
        this.name = name;
        this.course = course;
        this.description = description;
        this.registerNumber = registerNumber;
        this.email = email;
    }

    /**
     * Default constructor with both params and id
     * @param id
     * @param login
     * @param token
     * @param name
     * @param course
     * @param description
     * @param registerNumber
     * @param email
     */
    public User(String id, String login, String token, String name, String course, String description, String registerNumber, String email) {
        this.setId(id);
        this.login = login;
        this.token = token;
        this.name = name;
        this.course = course;
        this.description = description;
        this.registerNumber = registerNumber;
        this.email = email;
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
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRegisterNumber() { return registerNumber; }
    public void setRegisterNumber(String registerNumber) { this.registerNumber = registerNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public final static String LOGIN="login";
    public final static String TOKEN="token";
    public final static String NAME="name";
    public final static String COURSE="course";
    public final static String DESCRIPTION="description";
    public final static String REGISTER_NUMBER="register_number";
    public final static String EMAIL="email";
}
