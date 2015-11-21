package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 21/11/2015.
 */
public class HashtagPost {

    public static final String[] COLUMNS = new String[] {
            HashtagPost.POST, HashtagPost.HASHTAG
    };

    private Post post;
    private Hashtag hashtag;

    public HashtagPost() {}

    public HashtagPost(Post post, Hashtag hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }

    public static final String POST = "id_post";
    public static final String HASHTAG = "id_hashtag";
}
