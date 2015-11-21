package com.nowa.com.dao;

import android.content.Context;
import android.database.Cursor;

import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 14/10/2015.
 */
public class DaoSubject extends GeneralDao {

    public DaoSubject(Context ctx) {
        super(ctx);
    }

    @Override
    public HashMap<String, String> parseObjectToMap(Object obj) {
        Subject subject = (Subject) obj;

        HashMap<String, String> values = new HashMap<>();
        values.put(Subject._ID, subject.getId());
        values.put(Subject.NAME, subject.getName());
        values.put(Subject.NAME_SIGAA, subject.getNameSigaa());
        values.put(Subject.SUBJECT_CODE, subject.getSubjectCode());
        values.put(Subject.PICTURE, subject.getPicture());

        return values;
    }

    public List<Subject> getSubjects(User user) {
        List<Subject> subjects = new ArrayList<>();

        try {
            Cursor c = getAllCursors("subject", Subject.COLUMNS, null);
            if(c.moveToFirst()) {

                int idxid = c.getColumnIndex(Subject._ID);
                int idxname = c.getColumnIndex(Subject.NAME);
                int idxnameSigaa = c.getColumnIndex(Subject.NAME_SIGAA);
                int idxsubjectCode = c.getColumnIndex(Subject.SUBJECT_CODE);
                int idxpicture = c.getColumnIndex(Subject.PICTURE);

                do{
                    Subject subject = new Subject(c.getString(idxid), c.getString(idxname), c.getString(idxnameSigaa), c.getString(idxsubjectCode), c.getString(idxpicture));

                    subjects.add(subject);
                } while(c.moveToNext());
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return subjects;
    }

    public void saveAllLocal(List<Subject> subjects) {
        for(Subject sub : subjects)
            service("save", "subject", parseObjectToMap(sub), false);
    }
}
