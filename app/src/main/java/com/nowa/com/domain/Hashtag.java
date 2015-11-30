package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 21/11/2015.
 */
public class Hashtag extends Entity implements SpecialWord {

    public static final String[] COLUMNS = new String[] {
            Hashtag._ID, Hashtag.NAME
    };

    private String name;
    private String realName;

    public Hashtag() {
    }

    public Hashtag(String name) {
        this.name = name;
    }

    public Hashtag(String id, String name, String realName) {
        this.setId(id);
        this.name = name;
        this.realName = realName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static final String NAME = "#name";
    public static final String REALNAME = "name";

    @Override
    public String toString() {
        return this.name;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {

        return this.realName;
    }
}
