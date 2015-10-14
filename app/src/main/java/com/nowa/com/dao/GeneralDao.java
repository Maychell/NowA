package com.nowa.com.dao;

import android.content.Context;

import com.nowa.com.cloudUtils.CloudQueries;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 13/10/2015.
 */
public class GeneralDao {

    private Context ctx;

    public GeneralDao(Context ctx) {
        this.ctx=ctx;
    }

    public boolean service(String methodName, String className, HashMap<String, String> params, boolean cloudPersist) {
        boolean result=false;
        switch (methodName) {
            case "save":
                result=save(className, params, cloudPersist);
            case "delete":
                result=delete(className, params, cloudPersist);
        }
        return result;
    }

    public boolean save(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(params.get("id").equals(""))
            insert(className, params, cloudPersist);
        else
            update(className, params, cloudPersist);
        return true;
    }

    private boolean insert(String className, HashMap<String, String> params, boolean cloudPersist) {
        if(cloudPersist) {
            CloudQueries cloud = new CloudQueries(ctx);
            cloud.saveInBackGround(className, params);
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
