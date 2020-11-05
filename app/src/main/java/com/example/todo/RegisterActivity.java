package com.example.todo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText Regname,Regemail,Regphone,Regpassword;
    private Button SignInBtn;
    private Toolbar toolbar;
    private TextView textView;
    private FirebaseAuth fauth;
    private ProgressDialog loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        toolbar=findViewById(R.id.register_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");


        Regname=findViewById(R.id.name_input);
        Regemail=findViewById(R.id.email_input);
        Regphone=findViewById(R.id.phone_input);
        Regpassword=findViewById(R.id.password_input);
        SignInBtn=findViewById(R.id.signupbtn_id);
        textView=findViewById(R.id.signinpage_qus);

        fauth=FirebaseAuth.getInstance();
        loader=new ProgressDialog(this);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= Regname.getText().toString().trim();
                String email=Regemail.getText().toString().trim();
                String phone=Regphone.getText().toString().trim();
                String password=Regpassword.getText().toString().trim();


                if (TextUtils.isEmpty(name)){
                  Regname.setError("Name Required");
                  return;
                }
                if (TextUtils.isEmpty(email)){
                    Regemail.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Regphone.setError("PhoneNumber Required");
                    return;
                }

                 if (TextUtils.isEmpty(password)){
                    Regpassword.setError("Password Required");
                    return;
                }
                 if(password.length() < 6){
                    Regpassword.setError("Password Must be>=6 Characters");
                    return;

                }
                else {

                    loader.setMessage("Registration Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    // Log.d("myerror===>", "onClick: email:"+email);
                    fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else {
                                //Log.d("myerror==>", "onComplete: "+task.getException().getMessage());
                                Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }

                        }
                    });


                }


            }
        });
    }
}
