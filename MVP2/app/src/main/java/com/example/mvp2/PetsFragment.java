package com.example.mvp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class PetsFragment extends Fragment {
    private static final String TAG = "PetFragment";

    Animation anim;
    UserInfo userInfo;

    TextView tv_pet_name;
    ProgressBar pb_hunger, pb_fun, pb_exercise;
    ImageView petimg;

    public PetsFragment(UserInfo ui, Animation _anim) {
        userInfo = ui;
        anim = _anim;
    }

    @Override
    public void onStart() {
        super.onStart();

        updateBars(userInfo);

        tv_pet_name = (TextView) getView().findViewById(R.id.pet_name);
        tv_pet_name.setText(userInfo.getPet_name());

        petimg = (ImageView) getView().findViewById(R.id.petimg);
        petimg.setImageResource(getSprite());

        petimg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(userInfo.getPet_hunger() <= 10) {
                    Toast.makeText(getContext(), userInfo.getPet_name() + " is too hungry to play!", Toast.LENGTH_SHORT).show();
                } else {
                    petimg.startAnimation(anim);
                    ((MainActivity)getActivity()).playSound("pop");
                    if (userInfo.getPet_fun() < 100) {
                        userInfo.setPet_fun(userInfo.getPet_fun() + 1);
                    }
                    if (userInfo.getPet_fun() > 1) {
                        Random r = new Random();
                        int i = r.nextInt(3);
                        if (i == 0) {
                            userInfo.setPet_hunger(userInfo.getPet_hunger() - 1);
                        }
                    }
                    updateBars(userInfo);
                }
            }
        });
    }

    private void updateBars(UserInfo ui) {
        pb_exercise = (ProgressBar) getView().findViewById(R.id.exercise_bar);
        pb_fun = (ProgressBar) getView().findViewById(R.id.fun_bar);
        pb_hunger = (ProgressBar) getView().findViewById(R.id.hunger_bar);

        pb_exercise.setMax(100);
        pb_fun.setMax(100);
        pb_hunger.setMax(100);

        pb_exercise.setProgress(ui.getPet_exercise());
        pb_fun.setProgress(ui.getPet_fun());
        pb_hunger.setProgress(ui.getPet_hunger());
    }

    private int getSprite() {
        Log.d(TAG, String.valueOf(userInfo.getPet_sprite_ID()));
        switch (userInfo.getPet_sprite_ID()) {
            case 0:
                return R.drawable.chicken;
            case 1:
                return R.drawable.cow;
            case 2:
                return R.drawable.dog;
            case 3:
                return R.drawable.parrot;
            case 4:
                return R.drawable.penguin;
            case 5:
                return R.drawable.pig;
            case 6:
                return R.drawable.rhino;
            case 7:
                return R.drawable.sloth;
            case 8:
                return R.drawable.whale;
        }
        return R.drawable.ic_pets_black_24dp;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pets, container, false);
    }
}
