package com.example.mvp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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

    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String PET_HUNGER = "pet_hunger";
    private static final String PET_EXERCISE = "pet_exercise";
    private static final String PET_FUN = "pet_fun";
    private static final String PET_NAME = "pet_name";
    private static final String PET_SPRITE_ID = "pet_sprite_id";

    private static final String SOUND_SWITCH = "sound_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
        progressBar = findViewById(R.id.progress_bar_register);

        et_Username = findViewById(R.id.et_username_register);
        et_Email = findViewById(R.id.et_email_register);
        et_Password = findViewById(R.id.et_password_register);
        et_PasswordCheck = findViewById(R.id.et_password_check_register);
        btn_register = findViewById(R.id.btn_register);
        btn_return = findViewById(R.id.btn_returnToLogin);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = et_Username.getText().toString().trim();
                String email = et_Email.getText().toString().trim();
                String password = et_Password.getText().toString().trim();
                String passwordCheck = et_PasswordCheck.getText().toString().trim();

                    // REQUIRED FIELDS
                if(email.isEmpty()) {
                    et_Email.setError("Email is required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(!isValidEmail(email)){
                    et_Email.setError("Not valid Email.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(username.isEmpty()) {
                    et_Username.setError("Username is required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(password.isEmpty()) {
                    et_Password.setError("Password is required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(passwordCheck.isEmpty()) {
                    et_PasswordCheck.setError("Password is required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                    // FIELD ERRORS
                if(password.length() < 6){
                    et_Password.setError("Password must be 6 or more characters.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if(!password.equals(passwordCheck)){
                    et_PasswordCheck.setError("Passwords must match.");
                    progressBar.setVisibility(View.INVISIBLE);
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
                    Toast.makeText(RegisterActivity.this, "Register Failed, Please try again later", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    String userID = user.getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                    Map dbInfo = new HashMap();
                    dbInfo.put("pet_exercise", 50);
                    dbInfo.put("pet_fun", 50);
                    dbInfo.put("pet_hunger", 50);
                    dbInfo.put("pet_name", "Your Pet");
                    dbInfo.put("pet_sprite_ID", 0);
                    dbInfo.put("sound_switch", true);
                    dbInfo.put("user_email", dbEmail);
                    dbInfo.put("user_name", dbUsername);

                    current_user_db.setValue(dbInfo);

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt((userID+PET_EXERCISE), 50);
                    editor.putInt((userID+PET_FUN), 50);
                    editor.putInt((userID+PET_HUNGER), 50);
                    editor.putString((userID+PET_NAME), "Your Pet");
                    editor.putInt((userID+PET_SPRITE_ID), 0);
                    editor.putBoolean((userID+SOUND_SWITCH), true);
                    editor.apply();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
