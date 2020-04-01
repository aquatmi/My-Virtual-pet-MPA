package com.example.mvp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LoadingActivity";
    UserInfo userInfo = new UserInfo();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference myRef = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        FirebaseApp.initializeApp(this);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfo = collectData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        switch(userInfo.getTheme()){
            case 0:     //load blue theme
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
        }


    }

    private UserInfo collectData(DataSnapshot ds) {
        UserInfo uInfo = new UserInfo();
        ds = ds.child("Users");
        uInfo.setPet_exercise(ds.child(currentUser).getValue(UserInfo.class).getPet_exercise());
        uInfo.setPet_fun(ds.child(currentUser).getValue(UserInfo.class).getPet_fun());
        uInfo.setPet_hunger(ds.child(currentUser).getValue(UserInfo.class).getPet_hunger());
        uInfo.setPet_name(ds.child(currentUser).getValue(UserInfo.class).getPet_name());
        uInfo.setPet_sprite_ID(ds.child(currentUser).getValue(UserInfo.class).getPet_sprite_ID());
        uInfo.setTheme(ds.child(currentUser).getValue(UserInfo.class).getTheme());
        uInfo.setUser_email(ds.child(currentUser).getValue(UserInfo.class).getUser_email());
        uInfo.setUser_name(ds.child(currentUser).getValue(UserInfo.class).getUser_name());

        Log.d(TAG, String.valueOf(uInfo.getPet_exercise()));
        Log.d(TAG, String.valueOf(uInfo.getPet_fun()));
        Log.d(TAG, String.valueOf(uInfo.getPet_hunger()));
        Log.d(TAG, uInfo.getPet_name());
        Log.d(TAG, String.valueOf(uInfo.getPet_sprite_ID()));
        Log.d(TAG, String.valueOf(uInfo.getTheme()));
        Log.d(TAG, uInfo.getUser_email());
        Log.d(TAG, uInfo.getUser_name());
        return uInfo;
    }

}
