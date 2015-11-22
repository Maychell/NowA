package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class Subject extends Entity {

    public final static String[] COLUMNS = new String[] {
            Subject._ID, Subject.SUBJECT_CODE, Subject.NAME, Subject.NAME_SIGAA, Subject.PICTURE
    };

    private String name;
    private String nameSigaa;
    private String subjectCode;
    private String picture;

    /**
     * Default constructor
     */
    public Subject() {}

    /**
     * Default constructor with params
     * @param name
     * @param nameSigaa
     */
    public Subject(String name, String nameSigaa, String subjectCode, String picture) {
        this.name = name;
        this.nameSigaa = nameSigaa;
        this.subjectCode = subjectCode;
        this.picture = picture;
    }

    /**
     * Default constructor with both params and id
     * @param id
     * @param name
     * @param nameSigaa
     */
    public Subject(String id, String name, String nameSigaa, String subjectCode, String picture) {
        this.setId(id);
        this.name = name;
        this.nameSigaa = nameSigaa;
        this.subjectCode = subjectCode;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNameSigaa() {
        return nameSigaa;
    }
    public void setNameSigaa(String nameSigaa) {
        this.nameSigaa = nameSigaa;
    }
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public final static String NAME="name";
    public final static String NAME_SIGAA="name_sigaa";
    public final static String SUBJECT_CODE="subject_code";
    public final static String PICTURE="picture";
}
