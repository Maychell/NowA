package com.nowa.com.dao;

import android.content.Context;

import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 13/10/2015.
 */
public class GeneralDao {

    private Context ctx;

    public GeneralDao(Context ctx) {
        this.ctx=ctx;
    }

    public boolean service(String methodName, Map<String, String> params, boolean cloudPersist) {
        boolean result=false;
        switch (methodName) {
            case "save":
                result=save(params, cloudPersist);
            case "delete":
                result=delete(params);
        }
        return result;
    }

    public boolean save(Map<String, String> params, boolean cloudPersist) {
        if(params.get("id").equals(""))
            insert(params, cloudPersist);
        else
            update(params, cloudPersist);
        return true;
    }

    private boolean insert(Map<String, String> params, boolean cloudPersist) {
        return true;
    }

    private boolean update(Map<String, String> params, boolean cloudPersist) {
        return true;
    }

    public boolean delete(Map<String, String> params) {
        return true;
    }
}
