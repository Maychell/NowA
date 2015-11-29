package com.nowa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        checkLoggedIn();
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

    /*
    private void populateCloudDatabase() {
        List<HashMap<String, String>> subjects = new ArrayList<>();
        CloudQueries cloudQueries = new CloudQueries(this);

        HashMap<String, String> subject = new HashMap<>();

        //1
        subject.put("number", "1");
        subject.put("picture", "http://0.gravatar.com/userimage/65397875/0d22d6ef7eb81a3db86f9581033ac671");
        subject.put(Subject.NAME, "@desenvolvimentoDSPDM20152");
        subject.put(Subject.NAME_SIGAA, "DESENVOLVIMENTO DE SISTEMAS PARA DISPOSITIVOS MÓVEIS");
        subject.put(Subject.SUBJECT_CODE, "DIM0524");
        subjects.add(subject);

        //2
        subject = new HashMap<>();
        subject.put("number", "2");
        subject.put("picture", "http://0.gravatar.com/userimage/65397875/b98b469d3f08702b1a72b7535940b182");
        subject.put(Subject.NAME, "@algebraLBI20152");
        subject.put(Subject.NAME_SIGAA, "ALGEBRA LINEAR BASICA I");
        subject.put(Subject.SUBJECT_CODE, "MAT0319");
        subjects.add(subject);

        //3
        subject = new HashMap<>();
        subject.put("number", "3");
        subject.put("picture", "http://2.gravatar.com/userimage/65397875/c6edd5855c1d422e6603c566f8f61231");
        subject.put(Subject.NAME, "@arquiteturaDS20152");
        subject.put(Subject.NAME_SIGAA, "ARQUITETURA DE SOFTWARE");
        subject.put(Subject.SUBJECT_CODE, "DIM0514");
        subjects.add(subject);

        //4
        subject = new HashMap<>();
        subject.put("number", "4");
        subject.put("picture", "http://1.gravatar.com/userimage/65397875/a2e268180dc14c1b62c753736c1e1590");
        subject.put(Subject.NAME, "@logicaAAEDS20152");
        subject.put(Subject.NAME_SIGAA, "LÓGICA APLICADA A ENGENHARIA DE SOFTWARE");
        subject.put(Subject.SUBJECT_CODE, "DIM0505");
        subjects.add(subject);

        //5
        subject = new HashMap<>();
        subject.put("number", "5");
        subject.put("picture", "http://2.gravatar.com/userimage/65397875/00bea85ac35c2a7f2e3de4d9280196f7");
        subject.put(Subject.NAME, "@projetoDIDU20152");
        subject.put(Subject.NAME_SIGAA, "PROJETO DE INTERFACES DE USUÁRIO");
        subject.put(Subject.SUBJECT_CODE, "DIM0508");
        subjects.add(subject);

        //6
        subject = new HashMap<>();
        subject.put("number", "6");
        subject.put("picture", "http://0.gravatar.com/userimage/65397875/0fa1e6924ff82706b1f4124d5588b825");
        subject.put(Subject.NAME, "@topicosEEESIII20152");
        subject.put(Subject.NAME_SIGAA, "TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE III");
        subject.put(Subject.SUBJECT_CODE, "DIM0533");
        subjects.add(subject);

        //7
        subject = new HashMap<>();
        subject.put("number", "7");
        subject.put("picture", "http://1.gravatar.com/userimage/65397875/2c48287f0cf168301d1b5ee4f39f0e37");
        subject.put(Subject.NAME, "@treinamentoPCDP20152");
        subject.put(Subject.NAME_SIGAA, "TREINAMENTO PARA COMPETICOES DE PROGRAMACAO");
        subject.put(Subject.SUBJECT_CODE, "DIM0410");
        subjects.add(subject);

        //8
        subject = new HashMap<>();
        subject.put("number", "8");
        subject.put("picture", "http://2.gravatar.com/userimage/65397875/e705c543a71730869d3c758b143e9f08");
        subject.put(Subject.NAME, "@boasPDP20152");
        subject.put(Subject.NAME_SIGAA, "BOAS PRÁTICAS DE PROGRAMAÇÃO");
        subject.put(Subject.SUBJECT_CODE, "DIM0501");
        subjects.add(subject);

        //9
        subject = new HashMap<>();
        subject.put("number", "9");
        subject.put("picture", "http://2.gravatar.com/userimage/65397875/5ca866177a2a1139a86be4afcc707d8c");
        subject.put(Subject.NAME, "@processosDS20152");
        subject.put(Subject.NAME_SIGAA, "PROCESSOS DE SOFTWARE");
        subject.put(Subject.SUBJECT_CODE, "DIM0510");
        subjects.add(subject);

        //10
        subject = new HashMap<>();
        subject.put("number", "10");
        subject.put("picture", "http://2.gravatar.com/userimage/65397875/c8afac1de07a1205704f5e639b43ace9");
        subject.put(Subject.NAME, "@programacaoD20152");
        subject.put(Subject.NAME_SIGAA, "PROGRAMAÇÃO DISTRIBUÍDA");
        subject.put(Subject.SUBJECT_CODE, "DIM0502");
        subjects.add(subject);

        //11
        subject = new HashMap<>();
        subject.put("number", "11");
        subject.put("picture", "http://0.gravatar.com/userimage/65397875/e6de134d7d467f0c22a78742d8167da1");
        subject.put(Subject.NAME, "@testeDSI20152");
        subject.put(Subject.NAME_SIGAA, "TESTE DE SOFTWARE I");
        subject.put(Subject.SUBJECT_CODE, "DIM0507");
        subjects.add(subject);

        //12
        subject = new HashMap<>();
        subject.put("number", "12");
        subject.put("picture", "http://1.gravatar.com/userimage/65397875/227ce2e844a8e76f9d0e3a6912c7944c");
        subject.put(Subject.NAME, "@programacaoC20152");
        subject.put(Subject.NAME_SIGAA, "PROGRAMAÇÃO CONCORRENTE");
        subject.put(Subject.SUBJECT_CODE, "DIM0542");
        subjects.add(subject);

        //13
        subject = new HashMap<>();
        subject.put("number", "13");
        subject.put("picture", "http://1.gravatar.com/userimage/65397875/c9a0488469ab71b887c1d217038f90a3");
        subject.put(Subject.NAME, "@geometriaAEV20152");
        subject.put(Subject.NAME_SIGAA, "GEOMETRIA ANALITICA E VETORIAL");
        subject.put(Subject.SUBJECT_CODE, "MAT0363");
        subjects.add(subject);

        //14
        subject = new HashMap<>();
        subject.put("number", "14");
        subject.put("picture", "http://0.gravatar.com/userimage/65397875/3d6fa2b768f031accb2201b05d1a6b91");
        subject.put(Subject.NAME, "@planejamentoEGDP20152");
        subject.put(Subject.NAME_SIGAA, "PLANEJAMENTO E GERENCIAMENTO DE PROJETOS");
        subject.put(Subject.SUBJECT_CODE, "DIM0518");
        subjects.add(subject);

        //15
        subject = new HashMap<>();
        subject.put("number", "15");
        subject.put("picture", "http://0.gravatar.com/userimage/65397875/4f5129e1a400f5d656ac7b70be71f2d2");
        subject.put(Subject.NAME, "@manutencaoDS20152");
        subject.put(Subject.NAME_SIGAA, "MANUTENÇÃO DE SOFTWARE");
        subject.put(Subject.SUBJECT_CODE, "DIM0515");
        subjects.add(subject);

        for(HashMap<String, String> sub : subjects)
            cloudQueries.save("subject", sub);

        Toast.makeText(this, "Subjects saved successfully:D", Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login) {
            //loginFromUFRN();
            loginFromApp();
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
}
