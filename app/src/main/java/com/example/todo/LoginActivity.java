package com.example.todo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText loginemail, loginPassword;
    private Button loginButton;
    private TextView textView;
    private Toolbar toolbar;
    private FirebaseAuth fauth;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.login_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");

        textView = findViewById(R.id.loginpage_qus);
        loginemail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_pass);
        loginButton = findViewById(R.id.login_btn);

        fauth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginemail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    loginemail.setError("Email Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password Required.");
                    return;
                }
                if (password.length() < 6) {
                    loginPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                else {
                    loader.setMessage("Login Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                loader.dismiss();
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }

                    });
                }

            }
        });
    }
}








