package com.example.mvp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    UserInfo userInfo;
    BottomNavigationView btm_menu;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference myRef = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);


        Log.d(TAG, "OnCreate Started");
        Log.d(TAG, "Current user:" + currentUser);

        FirebaseApp.initializeApp(this);

        btm_menu = findViewById(R.id.bottom_menu);
        btm_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_pets){
                    loadFragment(new PetsFragment(userInfo, shake));
                    return true;
                } else if(item.getItemId() == R.id.nav_map) {
                    loadFragment(new MapFragment());
                    return true;
                } else if(item.getItemId() == R.id.nav_options) {
                    loadFragment(new OptionsFragment());
                    return true;
                }
                return false;
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfo = collectData(dataSnapshot);
                loadFragment(new PetsFragment(userInfo, shake));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    private void loadFragment(Fragment f) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();
    }
}
