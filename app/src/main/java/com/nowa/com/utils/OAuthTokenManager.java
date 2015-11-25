package com.nowa.com.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;

import com.android.volley.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by rafaelfdequeiroz on 24/11/15.
 */
public class OAuthTokenManager {

    private Credential credential;

    private static OAuthTokenManager oAuthTokenManager;

    private String clientId, clientSecret;

    private OAuthTokenManager() {
    }

    public static OAuthTokenManager getInstance() {
        if(oAuthTokenManager == null) {
            oAuthTokenManager = new OAuthTokenManager();
        }
        return oAuthTokenManager;
    }

    public void getTokenCredential(Activity activity,String oAuthServerURL, String clientId,
                                   String clientSecret, OAuthManager.OAuthCallback<Credential> callback){
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(oAuthServerURL + "/oauth/token"),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId,
                oAuthServerURL + "/oauth/authorize");
        //builder.setCredentialStore(credentialStore);

        AuthorizationFlow flow = builder.build();

        AuthorizationUIController controller = new DialogFragmentController(activity.getFragmentManager()) {
            @Override
            public boolean isJavascriptEnabledForWebView() {
                return true;
            }

            @Override
            public String getRedirectUri() throws IOException {
                //return "http://android.local/";
                return "http://localhost/Callback";
            }
        };

        OAuthManager oAuthManager = new OAuthManager(flow, controller);

        try {
            oAuthManager.authorizeExplicitly("userId", callback, null);

            if (credential != null) {
                Log.d("TOKEN", credential.getAccessToken());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resourceRequest(Context context, int method, String url, Response.Listener<String> listener,
                                 Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer "+ credential.getAccessToken();
                headers.put("Authorization", auth);
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    public void setCredentials(Credential result) {
        credential = result;
    }
}
