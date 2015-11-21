package com.nowa.com.domain;

/**
 * Created by maychellfernandesdeoliveira on 05/10/2015.
 */
public class Subject extends Entity {

    public final static String[] COLUMNS = new String[] {
            Subject._ID, Subject.SUBJECT_CODE, Subject.NAME, Subject.NAME_SIGAA
    };

    private String name;
    private String nameSigaa;
    private String subjectCode;

    /**
     * Default constructor
     */
    public Subject() {}

    /**
     * Default constructor with params
     * @param name
     * @param nameSigaa
     */
    public Subject(String name, String nameSigaa, String subjectCode) {
        this.name = name;
        this.nameSigaa = nameSigaa;
        this.subjectCode = subjectCode;
    }

    /**
     * Default constructor with both params and id
     * @param id
     * @param name
     * @param nameSigaa
     */
    public Subject(String id, String name, String nameSigaa, String subjectCode) {
        this.setId(id);
        this.name = name;
        this.nameSigaa = nameSigaa;
        this.subjectCode = subjectCode;
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

    public final static String NAME="name";
    public final static String NAME_SIGAA="name_sigaa";
    public final static String SUBJECT_CODE="subject_code";
}
