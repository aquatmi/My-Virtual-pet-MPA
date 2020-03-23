package com.n0737367.myvirtualpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_Username, et_Email, et_Password, et_PasswordCheck;
    private Button btn_register, btn_return;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_register);

        et_Username = (EditText) findViewById(R.id.et_username_register);
        et_Email = (EditText) findViewById(R.id.et_email_register);
        et_Password = (EditText) findViewById(R.id.et_password_register);
        et_PasswordCheck = (EditText) findViewById(R.id.et_password_check_register);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_return = (Button) findViewById(R.id.btn_returnToLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = et_Username.getText().toString().trim();
                String email = et_Email.getText().toString().trim();
                String password = et_Password.getText().toString().trim();
                String passwordCheck = et_PasswordCheck.getText().toString().trim();

                    // REQUIRED FIELDS
                if(username.isEmpty()) {
                    et_Username.setError("Username is required.");
                    return;
                }
                if(email.isEmpty()) {
                    et_Email.setError("Email is required.");
                    return;
                }
                if(password.isEmpty()) {
                    et_Password.setError("Password is required.");
                    return;
                }
                if(passwordCheck.isEmpty()) {
                    et_PasswordCheck.setError("Password is required.");
                    return;
                }
                    // FIELD ERRORS
                if(password.length() < 6){
                    et_Password.setError("Password must be 6 or more characters.");
                    return;
                }
                if(!password.equals(passwordCheck)){
                    et_PasswordCheck.setError("Passwords must match.");
                    return;
                }

                    // REGISTER USER
                registerUser(email, password, username);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(final String dbEmail, String dbPassword, final String dbUsername){
        firebaseAuth.createUserWithEmailAndPassword(dbEmail,dbPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    String userID = user.getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                    Map dbInfo = new HashMap();
                    dbInfo.put("username", dbUsername);
                    dbInfo.put("email", dbEmail);

                    current_user_db.setValue(dbInfo);
                }
            }
        });
    }
}
