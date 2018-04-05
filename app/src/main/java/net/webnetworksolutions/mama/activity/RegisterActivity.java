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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button reg;
    private TextView tvLogin;
    private EditText etPass;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbHelper(this);
        reg = (Button)findViewById(R.id.btnReg);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        etPass = (EditText)findViewById(R.id.etPass);
        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        etPass.setText(getIntent().getStringExtra("BibleText"));

        register();
        String pass1 = etPass.getText().toString();
        Intent logIntent= new Intent(RegisterActivity.this,LoginActivity.class);
        logIntent.putExtra("loginData",pass1);
        startActivity(logIntent);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnReg:
               // register();
                break;
            case R.id.tvLogin:
                //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
               // finish();
                break;
            default:

        }
    }

    private void register(){
        String pass = etPass.getText().toString();
        if( pass.isEmpty()){
            displayToast("Username/password field empty");
        }else{
            db.addUser(pass);
            displayToast("User registered");
            //finish();
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}