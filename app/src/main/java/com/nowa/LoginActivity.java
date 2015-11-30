package com.nowa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.api.client.auth.oauth2.Credential;
import com.nowa.com.dao.DaoUser;
import com.nowa.com.database.Database;
import com.nowa.com.database.DatabaseScript;
import com.nowa.com.domain.Hashtag;
import com.nowa.com.domain.User;
import com.nowa.com.utils.OAuthTokenManager;
import com.nowa.com.utils.Parameter;
import com.wuman.android.auth.OAuthManager;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by maychellfernandesdeoliveira on 16/11/2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnSignUp;
    private EditText txtLogin, txtPassword;
    private TextView txtSignUp;
    private SharedPreferences sharedpreferences;

    private static final String CLIENTID = "nowa-id";
    private static final String CLIENTSECRET = "applicationnowa";
    private String jsonResponse;
    private Context loginActivity;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        txtLogin = (EditText) findViewById(R.id.txt_login);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up);

        db = new DatabaseScript(this);

        sharedpreferences = getSharedPreferences(Parameter.MY_PREFERENCES, this.MODE_PRIVATE);

        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);

        new ProgressTask(Parameter.ACTION_CHECK_LOGGED_IN).execute();
        loginActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Parameter.user != null)
            finish();
    }

    private void checkLoggedIn() {
        String user_id = sharedpreferences.getString("User", null);
        if(user_id != null && !user_id.equals("")) {
            DaoUser daoUser = new DaoUser(this);
            daoUser.getUserById(user_id);
            daoUser.close();

            Intent it = new Intent(this, FeedActivity.class);
            startActivity(it);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login) {
            new ProgressTask(Parameter.ACTION_LOGIN).execute();
        } else if(v.getId() == R.id.txt_sign_up) {
            Intent it = new Intent(this, SelfRegisteringActivity.class);
            startActivity(it);
        }

    }

    private void loginFromUFRN() {
        OAuthTokenManager.getInstance().getTokenCredential(this, "http://apitestes.info.ufrn.br/authz-server",
                CLIENTID, CLIENTSECRET, new OAuthManager.OAuthCallback<Credential>() {
                    @Override
                    public void run(OAuthManager.OAuthFuture<Credential> future) {
                        try {
                            Credential credential = future.getResult();
                            OAuthTokenManager.getInstance().setCredentials(credential);
                            if (credential != null) {
                                getUserData();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getUserData() {
        final Context context = this;

        String urlJsonObj = "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info";

        OAuthTokenManager.getInstance().resourceRequest(this, Request.Method.GET, urlJsonObj,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String nome = jsonObject.getString("nome");
                            String login = jsonObject.getString("login");

                            DaoUser daoUser = new DaoUser(loginActivity);
                            User user = daoUser.getUserByLogin(login);

                            if (user == null) {
                                user = new User(login, "ofhaluasehffa;seifj", nome, "Engenharia de Software",
                                        "Aluno iniciante", "2011090433", "aluno.teste@gmail.com");
                                daoUser.save(user);
                            }

                            Parameter.user = user;

                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("User", Parameter.user.getId());
                            editor.commit();

                            jsonResponse = "";
                            jsonResponse += "Name: " + nome + "\n\n";
                            jsonResponse += "Login: " + login + "\n\n";

                            VolleyLog.d("SAID", "UserData", response);

                            Log.i("USERDATA", response);

                            Intent intent = new Intent(context, FeedActivity.class);
                            context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("SAIDA", "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginFromApp() {
        if(authorized()) {
            Intent it = new Intent(this, FeedActivity.class);
            startActivity(it);
        } else
            Toast.makeText(this, "Usuário não encontrado. Tente novamente.", Toast.LENGTH_SHORT).show();
    }

    private boolean authorized() {
        boolean authorized = false;

        if(txtLogin.getText().toString().equals(""))
            return authorized;

        DaoUser daoUser = null;
        try {
            daoUser = new DaoUser(this);
            User user = daoUser.getUserByLogin(txtLogin.getText().toString());
            if (user != null) {
                authorized = true;
                Parameter.user = user;

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString("User", Parameter.user.getId());
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authorized;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        if(db != null)
            db.close();
        super.onDestroy();
    }

    public class ProgressTask extends AsyncTask<String, Void, Boolean> {

        private int action;

        public ProgressTask(int action) {
            this.action = action;
            dialog = new ProgressDialog(LoginActivity.this);
        }

        private ProgressDialog dialog;

        @Override
        protected Boolean doInBackground(String... strings) {
            if(action == Parameter.ACTION_CHECK_LOGGED_IN)
                checkLoggedIn();
            if(action == Parameter.ACTION_LOGIN)
                loginFromApp();
                //loginFromUFRN();
            return true;
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
