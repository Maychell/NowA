package com.nowa.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nowa.FeedActivity;
import com.nowa.MainActivity;
import com.nowa.R;

/**
 * Created by maychellfernandesdeoliveira on 16/11/2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText txtLogin, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        txtLogin = (EditText) findViewById(R.id.txt_login);
        txtPassword = (EditText) findViewById(R.id.txt_password);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login) {
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        }
    }

    //metodo para validar login
}
