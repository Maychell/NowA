package com.nowa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nowa.FeedActivity;
import com.nowa.MainActivity;
import com.nowa.R;
import com.nowa.com.cloudUtils.CloudQueries;
import com.nowa.com.dao.DaoUser;
import com.nowa.com.database.Database;
import com.nowa.com.database.DatabaseScript;
import com.nowa.com.domain.Subject;
import com.nowa.com.domain.User;
import com.nowa.com.utils.Parameter;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maychellfernandesdeoliveira on 16/11/2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText txtLogin, txtPassword;
    private SharedPreferences sharedpreferences;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        txtLogin = (EditText) findViewById(R.id.txt_login);
        txtPassword = (EditText) findViewById(R.id.txt_password);

        db = new DatabaseScript(this);

        btnLogin.setOnClickListener(this);

        checkLoggedIn();
    }

    private void checkLoggedIn() {
        sharedpreferences = getSharedPreferences(Parameter.MY_PREFERENCES, this.MODE_PRIVATE);

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
            if(authorized()) {
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
            } else
                Toast.makeText(this, "Usuário não encontrado. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean authorized() {
        boolean authorized = false;

        DaoUser daoUser = null;
        try {
            daoUser = new DaoUser(this);
            String user_id = sharedpreferences.getString("User", null);
            if(user_id != null) {
                daoUser.getUserById(user_id);
                return true;
            }

            User user = daoUser.getUser(txtLogin.getText().toString(), txtPassword.getText().toString());
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
    protected void onDestroy() {
        if(db != null)
            db.close();
        super.onDestroy();
    }
}