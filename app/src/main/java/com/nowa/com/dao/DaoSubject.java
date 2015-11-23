package com.nowa.com.dao;

import android.content.Context;
import android.database.Cursor;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;
import com.nowa.com.domain.UserSubject;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

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
            Cursor c = getAllCursors("user_subject", UserSubject.COLUMNS, UserSubject.USER+"='"+user.getId()+"'");
            String selection = Subject._ID+" IN (";

            if(c.moveToFirst()) {
                do {
                    selection += "'"+c.getString(c.getColumnIndex(UserSubject.SUBJECT)) + "'" + (c.isLast() ? "" : ", ");
                } while (c.moveToNext());
            } else {
                //TRY TO RETRIEVE FROM CLOUD.
                subjects = getSubjectsOnCloud(user);
                return subjects;
            }

            selection += ")";

            c = getAllCursors("subject", Subject.COLUMNS, selection);

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

    private List<Subject> getSubjectsOnCloud(User user) {
        List<Subject> subjects = new ArrayList<>();

        CloudQueries cloud = new CloudQueries(ctx);
        List<ParseObject> result = cloud.getObject("user_subject", UserSubject.USER, user.getId());

        if(result != null && !result.isEmpty()) {
            String[] selections = new String[result.size()];
            for(int i=0; i<result.size(); ++i) {
                selections[i] = result.get(i).getString(UserSubject.SUBJECT);
            }
            //Getting subjects on cloud
            List<ParseObject> subjectResult = cloud.getObject("subject", Subject._ID, selections);

            if(subjectResult != null && !subjectResult.isEmpty()) {
                for(ParseObject po : subjectResult) {
                    Subject subject = new Subject(po.getString(Subject._ID),
                            po.getString(Subject.NAME), po.getString(Subject.NAME_SIGAA),
                            po.getString(Subject.SUBJECT_CODE), po.getString(Subject.PICTURE));

                    subjects.add(subject);
                }
            }
        }

        return subjects;
    }

    public void saveAllLocal(List<Subject> subjects) {
        DaoUserSubject dao = new DaoUserSubject(ctx);
        for(Subject sub : subjects) {
            service("save", "subject", parseObjectToMap(sub), false);

            //linking the user with the subject
            UserSubject us = new UserSubject(Parameter.user, sub);
            service("save", "user_subject", dao.parseObjectToMap(us), true);
        }
    }
}
