package com.example.aluno.contactlist.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login Firebase";
    private FirebaseAuth mAuth;

    TextView stats, detail;
    EditText emailField, passwordField;
    LinearLayout layoutLogin, camposLogin, btnsLogin, layoutLogado;
    Button btnLogin, btnCadastro, btnLogout, btnVerify;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        stats = findViewById(R.id.status);
        detail = findViewById(R.id.detail);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.password);

        // Botões
        btnLogin = findViewById(R.id.btnSignIn);
        btnCadastro = findViewById(R.id.btnRegister);
        btnLogout = findViewById(R.id.btnSignOut);
        btnVerify = findViewById(R.id.btnVerify);

        // Layouts
        layoutLogado = findViewById(R.id.layoutLogado);
        layoutLogin = findViewById(R.id.layoutLogin);
        btnsLogin = findViewById(R.id.btnsLogin);
        camposLogin = findViewById(R.id.camposLogin);

        // Listeners
        btnLogin.setOnClickListener(this);
        btnCadastro.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnVerify.setOnClickListener(this);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() { // Verifica se o usuário não é nulo e atualiza a UI de acordo
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void criarConta(String email, String password) {
        Log.d(TAG, "criar conta: " + email);
        if (!validateForm()) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // Se der certo, atualiza a UI com as infos do novo usuário
                            Log.d(TAG, "criarConta:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else { // Se não der, mostra uma mensagem pro usuário
                            Log.w(TAG, "criarConta:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) return;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        btnVerify.setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        btnVerify.setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            stats.setText("Sucesso no cadastro!");
            detail.setText("ID de usuário: " + user.getUid());

            btnsLogin.setVisibility(View.GONE);
            camposLogin.setVisibility(View.GONE);
            layoutLogado.setVisibility(View.VISIBLE);

            btnVerify.setEnabled(!user.isEmailVerified());
        } else {
//          detail.setText("");
            btnsLogin.setVisibility(View.VISIBLE);
            camposLogin.setVisibility(View.VISIBLE);
            layoutLogado.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == btnCadastro.getId()) {
            criarConta(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == btnLogin.getId()) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == btnLogout.getId()) {
            signOut();
        } else if (i == btnVerify.getId()) {
            sendEmailVerification();
        }
    }
}