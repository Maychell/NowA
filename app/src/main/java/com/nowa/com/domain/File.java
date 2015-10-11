package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class File extends Entity {

    public final static String[] COLUMNS = new String[] {
            File._ID, File.POST, File.PATH, File.NAME, File.FORMAT
    };

    private Post post;
    private String path;
    private String name;
    private String format;

    /**
     * Default constructor
     */
    public File() {}

    /**
     * Default constructor with params
     * @param post
     * @param path
     * @param name
     * @param format
     */
    public File(Post post, String path, String name, String format) {
        this.post = post;
        this.path = path;
        this.name = name;
        this.format = format;
    }

    /**
     * Default constructor with both params and id
     * @param id
     * @param post
     * @param path
     * @param name
     * @param format
     */
    public File(String id, Post post, String path, String name, String format) {
        this.setId(id);
        this.post = post;
        this.path = path;
        this.name = name;
        this.format = format;
    }

    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public final static String POST="id_post";
    public final static String PATH="path";
    public final static String NAME="name";
    public final static String FORMAT="format";
}
