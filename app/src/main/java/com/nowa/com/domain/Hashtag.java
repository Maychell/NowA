package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 21/11/2015.
 */
public class Hashtag extends Entity {

    public static final String[] COLUMNS = new String[] {
            Hashtag._ID, Hashtag.NAME
    };

    private String name;

    public Hashtag() {
    }

    public Hashtag(String name) {
        this.name = name;
    }

    public Hashtag(String id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static final String NAME = "name";
}
