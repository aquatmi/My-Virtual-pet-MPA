package com.example.mvp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout Layout1, Layout2;
    Handler handler = new Handler();

    private ProgressBar progressBar;
    private EditText email, password;
    private Button btn_login, btn_forgot_password, btn_to_register;
    private FirebaseAuth firebaseAuth;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Layout1.setVisibility(View.VISIBLE);
            Layout2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        Layout1 = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        Layout2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
        handler.postDelayed(runnable, 1000);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_login);

        email = (EditText) findViewById(R.id.et_email_login);
        password = (EditText) findViewById(R.id.et_password_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot_password = (Button) findViewById(R.id.btn_forgot_password);
        btn_to_register = (Button) findViewById(R.id.btn_to_register);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_forgot_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_to_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String l_email = email.getText().toString().trim();
                String l_password = password.getText().toString().trim();

                // CHECK INPUTS
                if(l_email.isEmpty()){
                    email.setError("Field Empty");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(l_password.isEmpty()){
                    password.setError("Field Empty");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(l_email,l_password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            email.setError("Inputs do not match valid account");
                            password.setError("Inputs do not match valid account");
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}
