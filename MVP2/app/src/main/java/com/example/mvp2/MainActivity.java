package com.example.mvp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.animation.MotionSpec;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String PET_HUNGER = "pet_hunger";
    private static final String PET_EXERCISE = "pet_exercise";
    private static final String PET_FUN = "pet_fun";
    private static final String PET_NAME = "pet_name";
    private static final String PET_SPRITE_ID = "pet_sprite_id";

    private static final String SOUND_SWITCH = "sound_switch";

    UserInfo userInfo = new UserInfo();
    BottomNavigationView btm_menu;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference myRef = db.getReference();
    boolean playSound;
    MediaPlayer popSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        popSound = MediaPlayer.create(this, R.raw.cartoon_pop);

        Log.d(TAG, "OnCreate Started");
        Log.d(TAG, "Current user:" + currentUser);

        FirebaseApp.initializeApp(this);
        userInfo = loadLocalData();
        playSound = userInfo.getSound_switch();
        updateDataBase();


        btm_menu = findViewById(R.id.bottom_menu);
        btm_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_pets){
                    loadFragment(new PetsFragment(userInfo, shake));
                    return true;
                } else if(item.getItemId() == R.id.nav_map) {
                    loadFragment(new MapFragment(userInfo));
                    return true;
                } else if(item.getItemId() == R.id.nav_options) {
                    loadFragment(new OptionsFragment(userInfo));
                    return true;
                }
                return false;
            }
        });

        loadFragment(new MapFragment(userInfo));
    }

    public void saveOptions(Boolean sound){
        playSound = sound;
        userInfo.setSound_switch(sound);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean((currentUser+SOUND_SWITCH), sound);
        editor.apply();

        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }

    private UserInfo loadLocalData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userInfo.setPet_exercise(sharedPreferences.getInt((currentUser+PET_EXERCISE), 0));
        userInfo.setPet_fun(sharedPreferences.getInt((currentUser+PET_FUN), 0));
        userInfo.setPet_hunger(sharedPreferences.getInt((currentUser+PET_HUNGER), 0));
        userInfo.setPet_name(sharedPreferences.getString((currentUser+PET_NAME), "Your Pet"));
        userInfo.setPet_sprite_ID(sharedPreferences.getInt((currentUser+PET_SPRITE_ID), 0));
        userInfo.setSound_switch(sharedPreferences.getBoolean((currentUser+SOUND_SWITCH), true));
        return userInfo;
    }

    private void updateDataBase() {
        DatabaseReference current_user_db = myRef.child("Users").child(currentUser);

        Map dbInfo = new HashMap();
        dbInfo.put("pet_exercise", 50);
        dbInfo.put("pet_fun", 50);
        dbInfo.put("pet_hunger", 50);
        dbInfo.put("pet_name", "Your Pet");
        dbInfo.put("pet_sprite_ID", 0);
        dbInfo.put("sound_switch", true);

        current_user_db.setValue(dbInfo);
    }

    private UserInfo loadDataBase(DataSnapshot ds) {
        UserInfo uInfo = new UserInfo();
        ds = ds.child("Users");
        uInfo.setPet_exercise(ds.child(currentUser).getValue(UserInfo.class).getPet_exercise());
        uInfo.setPet_fun(ds.child(currentUser).getValue(UserInfo.class).getPet_fun());
        uInfo.setPet_hunger(ds.child(currentUser).getValue(UserInfo.class).getPet_hunger());
        uInfo.setPet_name(ds.child(currentUser).getValue(UserInfo.class).getPet_name());
        uInfo.setPet_sprite_ID(ds.child(currentUser).getValue(UserInfo.class).getPet_sprite_ID());
        uInfo.setUser_email(ds.child(currentUser).getValue(UserInfo.class).getUser_email());
        uInfo.setUser_name(ds.child(currentUser).getValue(UserInfo.class).getUser_name());

        Log.d(TAG, String.valueOf(uInfo.getPet_exercise()));
        Log.d(TAG, String.valueOf(uInfo.getPet_fun()));
        Log.d(TAG, String.valueOf(uInfo.getPet_hunger()));
        Log.d(TAG, uInfo.getPet_name());
        Log.d(TAG, String.valueOf(uInfo.getPet_sprite_ID()));
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

    public void playSound(String sound) {
        if (playSound){
            switch(sound){
                case "pop":
                    popSound.start();
            }
        }
    }

    public void changePet(int pet){
        switch (pet){
            case 0:
                userInfo.setPet_sprite_ID(0);
                savePet();
                return;
            case 1:
                userInfo.setPet_sprite_ID(1);
                savePet();
                return;
            case 2:
                userInfo.setPet_sprite_ID(2);
                savePet();
                return;
            case 3:
                userInfo.setPet_sprite_ID(3);
                savePet();
                return;
            case 4:
                userInfo.setPet_sprite_ID(4);
                savePet();
                return;
            case 5:
                userInfo.setPet_sprite_ID(5);
                savePet();
                return;
            case 6:
                userInfo.setPet_sprite_ID(6);
                savePet();
                return;
            case 7:
                userInfo.setPet_sprite_ID(7);
                savePet();
                return;
            case 8:
                userInfo.setPet_sprite_ID(8);
                savePet();
                return;
        }
    }

    private void savePet(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt((currentUser+PET_EXERCISE), userInfo.getPet_exercise());
        editor.putInt((currentUser+PET_FUN), userInfo.getPet_fun());
        editor.putInt((currentUser+PET_HUNGER), userInfo.getPet_hunger());
        editor.putString((currentUser+PET_NAME), userInfo.getPet_name());
        editor.putInt((currentUser+PET_SPRITE_ID), userInfo.getPet_sprite_ID());
        editor.apply();
    }
}
