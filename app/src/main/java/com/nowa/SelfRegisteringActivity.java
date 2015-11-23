package com.nowa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nowa.com.dao.DaoUser;
import com.nowa.com.domain.User;

import java.util.UUID;

public class SelfRegisteringActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCreateAccount;
    private EditText txtName, txtLogin, txtCourse, txtDescription, txtRegistrationNumber, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_registering);
        fillViewFields();

        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_self_registering, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create_account) {
            DaoUser userDao = null;
            try {
                doesntHaveErrors();
                User user = new User(txtLogin.getText().toString(), String.valueOf(UUID.randomUUID()),
                        txtName.getText().toString(), txtCourse.getText().toString(), txtDescription.getText().toString(),
                        txtRegistrationNumber.getText().toString(), txtEmail.getText().toString());
                userDao = new DaoUser(this);
                userDao.save(user);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (userDao != null)
                    userDao.close();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillViewFields() {
        btnCreateAccount = (Button) findViewById(R.id.btn_create_account);
        txtName = (EditText) findViewById(R.id.txt_name);
        txtLogin = (EditText) findViewById(R.id.txt_login);
        txtCourse = (EditText) findViewById(R.id.txt_course);
        txtDescription = (EditText) findViewById(R.id.txt_description);
        txtRegistrationNumber = (EditText) findViewById(R.id.txt_registration_number);
        txtEmail = (EditText) findViewById(R.id.txt_email);
    }

    private void doesntHaveErrors() throws Exception {
        if (txtName.getText().toString().equals("") || txtEmail.getText().toString().equals("") ||
                txtLogin.getText().toString().equals("") ||
                txtCourse.getText().toString().equals("") ||
                txtRegistrationNumber.getText().toString().equals("")) {
            throw new Exception("Preencha todos os campos.");
        }
        DaoUser userDao = null;
        try {
            userDao = new DaoUser(this);
            User registeredUser = userDao.getUserByLogin(txtLogin.getText().toString());
            if (registeredUser != null) {
                throw new Exception("Já existe um usuário com este login.");
            }
        } finally {
            if (userDao != null)
                userDao.close();
        }
    }

    private void cleanData() {
        txtName.setText("");
        txtLogin.setText("");
        txtCourse.setText("");
        txtDescription.setText("");
        txtRegistrationNumber.setText("");
        txtEmail.setText("");
    }
}
