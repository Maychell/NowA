package com.nowa;

import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;
import com.parse.Parse;

/**
 * Created by maychellfernandesdeoliveira on 13/10/2015.
 */
public class Application extends android.app.Application {

    private static final String APPLICATION_ID = "hosdDjBdRYsCsUK2FjGGwhzgkOHcXrbBiiwm4Vq0";
    private static final String CLIENT_KEY = "h1V394zqrv7HLGTuH8d77zdO3JptI8aIZjXDYnZB";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        Parameter.user = new User("@maychell", "fdlkfhsdjbfablkjrehjl123", "maychell", "Engenharia de Software", "null", "20125412", "maychellfernandes@hotmail.com");
    }
}
