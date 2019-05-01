package com.example.aluno.contactlist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText input;
    TextView textView;
    Button btnPesquisar, btnCadastrar;
    ContatoDeEmergencia[] contactList = new ContatoDeEmergencia[5];

    static final int PICK_CONTACT_REQUEST = 1; // código da requisição de contato

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input1);
        btnPesquisar = findViewById(R.id.btnPesquisa);
        btnCadastrar = findViewById(R.id.btnCadastro);
        textView = findViewById(R.id.textView);
        btnPesquisar.setOnClickListener(this);
        btnCadastrar.setOnClickListener(this);

//        contactList[0] = new ContatoDeEmergencia("João", 88888888);
//        contactList[1] = new ContatoDeEmergencia("Pedro", 12345678);
//        contactList[2] = new ContatoDeEmergencia("Rose", 12938484);
//        contactList[3] = new ContatoDeEmergencia("Windson", 267668340);
//        contactList[4] = new ContatoDeEmergencia("Amanda", 28394823);

    }

    @Override
    public void onClick(View v){

        switch(v.getId()){

            case R.id.btnCadastro:
                Intent contactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                contactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(contactIntent, PICK_CONTACT_REQUEST);
                break;

            case R.id.btnPesquisa:
                String value = input.getText().toString();
                for(ContatoDeEmergencia c : contactList){
                    if(value.equals(c.nome)){
                        textView.setText(getApplicationContext().getString(R.string.contact_data, c.nome, Long.toString(c.numero)));
                        try {
                            String phoneNumber = "tel:" + c.numero;
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(phoneNumber));
                            startActivity(intent);
                        } catch (SecurityException e){
                            e.printStackTrace();
                            throw e;
                        }
                    } else {
                        textView.setText(getResources().getString(R.string.contact_not_found));
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Verifica qual reqiusição tá sendo atentida
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Certifica de que a requisição foi um sucesso
            if (resultCode == RESULT_OK) {
                // o contato foi selecionado, agora só pegar a Uri da Intent pra saber qual foi
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Recupera o nome e o número do contato
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                this.salvarContatoNaLista(contactName, contactNumber);
            }
        }
    }

    private void salvarContatoNaLista(String nome, String numero){
        SharedPreferences prefContato = getSharedPreferences("contato", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefContato.edit();
        editor.putString(nome, numero);
        // preciso jogar pra persistência direto, por isso uso commit() e não apply()
        editor.commit();
    }
}
