package com.nowa.com.utils;

import android.content.Context;
import android.content.Intent;

import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.dao.GeneralDao;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;

import java.util.List;
import java.util.Map;

/**
 * Created by maychellfernandesdeoliveira on 23/11/2015.
 */
public class GetPostsBroadcastReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        updateLocalDatabase(context);
    }

    private void updateLocalDatabase(Context ctx) {
        CloudQueries cloud = new CloudQueries(ctx);

        GeneralDao generalDao = new GeneralDao(ctx) {
            @Override
            public Map<String, String> parseObjectToMap(Object obj) {
                return null;
            }
        };

        //TERMINAR ISSO AQUI: PEGAR NOVOS POSTS E SALVAR NO BANCO DE DADOS LOCAL
    }
}