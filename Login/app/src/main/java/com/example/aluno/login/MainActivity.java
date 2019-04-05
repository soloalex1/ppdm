package com.example.aluno.login;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginInput, passwordInput;
    Button btnLogin;
    public final String LOGIN = "login";
    public final String SENHA = "senha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        if((this.loginInput.getText().toString().equals(this.LOGIN)) && (this.passwordInput.getText().toString().equals(this.SENHA))){

            // cria uma nova intent, seta a URI e inicia a activity
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://portal.virtual.ufc.br"));
            this.startActivity(intent);
        } else {
            Log.d("bumba", "papoca bichim");
        }
    }
}
