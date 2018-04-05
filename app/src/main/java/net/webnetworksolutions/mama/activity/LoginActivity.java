package net.webnetworksolutions.mama.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.webnetworksolutions.mama.helpers.DbHelper;
import net.webnetworksolutions.mama.R;
import net.webnetworksolutions.mama.support.Session;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button login;
    private TextView register;
    private EditText etPass;
    private DbHelper db;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbHelper(this);
        session = new Session(this);
        login = (Button)findViewById(R.id.btnLogin);
        //register = (TextView)findViewById(R.id.btnReg);
        etPass = (EditText)findViewById(R.id.etPass);
        login.setOnClickListener(this);

        etPass.setText(getIntent().getStringExtra("loginData"));
       // register.setOnClickListener(this);

        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnReg:
             //   startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:

        }
    }

    private void login(){

        String pass = etPass.getText().toString();

        if(db.getUser(pass)){
            session.setLoggedin(true);
            startActivity(new Intent(LoginActivity.this, Login2Activity.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
        }
    }
}
