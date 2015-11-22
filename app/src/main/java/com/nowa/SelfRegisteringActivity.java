package com.nowa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nowa.R;
import com.nowa.com.dao.DaoUser;
import com.nowa.com.domain.User;

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
        if(v.getId() == R.id.btn_create_account) {
            if(doesntHaveErrors()) {
                User user = new User(txtLogin.getText().toString(), "jh6faklhfehlf4afhaefoaeo1",
                        txtName.getText().toString(), txtCourse.getText().toString(), txtDescription.getText().toString(),
                        txtRegistrationNumber.getText().toString(), txtEmail.getText().toString());
                DaoUser userDao = new DaoUser(this);
                userDao.save(user);
            } else
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
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

    private boolean doesntHaveErrors() {
        if (txtName.getText().equals("") || txtEmail.getText().equals("") || txtLogin.getText().equals("") ||
                txtCourse.getText().equals("") || txtRegistrationNumber.getText().equals("") ||
                txtDescription.getText().equals("")) {
            return false;
        }

        return true;
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