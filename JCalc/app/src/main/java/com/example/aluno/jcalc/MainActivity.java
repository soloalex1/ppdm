package com.example.aluno.jcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText textLabel1, textLabel2;
    Spinner spinner;
    Button btnCalc;
    TextView resultView;

    TextView operCount;

    private int value;
    private String TAG = Integer.toString(value);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textLabel1 = (EditText) findViewById(R.id.textLabel1);
        this.textLabel2 = (EditText) findViewById(R.id.textLabel2);

        this.spinner = findViewById(R.id.operatorSpinner);

        this.btnCalc = (Button) findViewById(R.id.btnCalc);
        this.btnCalc.setOnClickListener(this);

        this.resultView = (TextView) findViewById(R.id.resultView);
        this.operCount = (TextView) findViewById(R.id.operCount);

        this.value = 0;

        // cria o ArrayAdapter usando o array de strings de operadores e um layout de spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operatorList, android.R.layout.simple_spinner_item);

        // especifica o layout de dropdown quando a lista de opções aparece
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // aplica o adaptador ao spinner
        spinner.setAdapter(adapter);

        value = 0;

        Log.d(TAG, "Activity Created!");

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "Activity Started!");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Activity Paused!");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Activity Resumed!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "Activity Stopped!");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "Activity Destroyed!");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "Activity Restarted!");
    }


    @Override
    public void onClick (View v){
        try {

            float op1 = Float.valueOf(textLabel1.getText().toString());
            float op2 = Float.valueOf(textLabel2.getText().toString());

            if (v == btnCalc){
                int operation = spinner.getSelectedItemPosition();

                float result;

                switch(operation){
                    
                }
            }

        } catch (NumberFormatException e){

        }
    }
}
