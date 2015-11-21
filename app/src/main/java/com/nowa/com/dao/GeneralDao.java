package com.nowa.com.dao;

import android.content.Context;
import android.database.Cursor;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.database.Database;
import com.nowa.com.domain.Post;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 13/10/2015.
 */
public abstract class GeneralDao extends Database {

    protected Context ctx;

    public GeneralDao(Context ctx) {
        this.ctx=ctx;
        db = this.ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public boolean service(String methodName, String className, HashMap<String, String> params, boolean cloudPersist) {
        boolean result=false;
        switch (methodName) {
            case "save":
                result=insert(className, params, cloudPersist);
            case "update":
                result=update(className, params, cloudPersist);
            case "delete":
                result=delete(className, params, cloudPersist);
        }
        return result;
    }

    public abstract Map<String, String> parseObjectToMap(Object obj);

    /**
     * To get an cursor with all orderings
     * @return Cursor
     * */
    protected Cursor getAllCursors(String className, String[] columns, String selection) {
        return db.query(className, columns, selection, null, null, null, null);
    }

    /*
    public boolean save(String className, HashMap<String, String> params, boolean cloudPersist) {
        //if(params.get("id").equals(""))
            insert(className, params, cloudPersist);
        //else
           // update(className, params, cloudPersist);
        return true;
    }
    */

    private boolean insert(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.save(className, params);
        }
        return true;
    }

    private boolean update(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.updateInBackground(className, params, params.get("id"));
        }
        return true;
    }

    private boolean delete(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.deleteObject(className, "id", params.get("id"));
        }
        return true;
    }
}
