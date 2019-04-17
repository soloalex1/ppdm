package com.example.aluno.contactlist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText input;
    TextView textView;
    Button btnPesquisar;
    ContatoDeEmergencia[] contactList = new ContatoDeEmergencia[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input1);
        btnPesquisar = findViewById(R.id.btn_pesquisa);
        textView = findViewById(R.id.textView);
        btnPesquisar.setOnClickListener(this);

        contactList[0] = new ContatoDeEmergencia("João", 88888888);
        contactList[1] = new ContatoDeEmergencia("Pedro", 12345678);
        contactList[2] = new ContatoDeEmergencia("Rose", 12938484);
        contactList[3] = new ContatoDeEmergencia("Windson", 267668340);
        contactList[4] = new ContatoDeEmergencia("Amanda", 28394823);

    }

    @Override
    public void onClick(View v){
        String value = input.getText().toString();
        for(ContatoDeEmergencia c : contactList){
            if(value.equals(c.nome)){
                textView.setText("Contato: " + c.nome + " " +
                        "[" + c.numero + "]");
                try {
                    String phoneNumber = "tel:" + Long.toString(c.numero);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(phoneNumber));
                    startActivity(intent);
                } catch (SecurityException e){
                    e.printStackTrace();
                }
            } else {
                textView.setText("Contato não encontrado");
            }
        }
    }
}
