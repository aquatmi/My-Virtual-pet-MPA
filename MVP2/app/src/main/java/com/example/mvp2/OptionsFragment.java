package com.example.mvp2;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class OptionsFragment extends Fragment {
    private static final String TAG = "OptionsFragment";

    UserInfo userInfo;
    Switch soundSwitch;
    ImageView chicken, cow, dog, parrot, penguin, pig, rhino, sloth, whale;
    Button saveButton;

    public OptionsFragment(UserInfo ui) {
        userInfo = ui;
    }


    @Override
    public void onStart() {
        super.onStart();

        saveButton = (Button) getView().findViewById(R.id.saveButton);

        soundSwitch = (Switch) getView().findViewById(R.id.soundSwitch);
        soundSwitch.setChecked(userInfo.getSound_switch());

        chicken = (ImageView) getView().findViewById(R.id.chicken);
        cow = (ImageView) getView().findViewById(R.id.cow);
        dog = (ImageView) getView().findViewById(R.id.dog);
        parrot = (ImageView) getView().findViewById(R.id.parrot);
        penguin = (ImageView) getView().findViewById(R.id.penguin);
        pig = (ImageView) getView().findViewById(R.id.pig);
        rhino = (ImageView) getView().findViewById(R.id.rhino);
        sloth = (ImageView) getView().findViewById(R.id.sloth);
        whale = (ImageView) getView().findViewById(R.id.whale);

        highlightSelected(userInfo);

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(0);
                ((MainActivity) getActivity()).changePet(0);
            }
        });
        cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(1);
                ((MainActivity) getActivity()).changePet(1);
            }
        });
        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(2);
                ((MainActivity) getActivity()).changePet(2);
            }
        });
        parrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(3);
                ((MainActivity) getActivity()).changePet(3);
            }
        });
        penguin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(4);
                ((MainActivity) getActivity()).changePet(4);
            }
        });
        pig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(5);
                ((MainActivity) getActivity()).changePet(5);
            }
        });
        rhino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(6);
                ((MainActivity) getActivity()).changePet(6);
            }
        });
        sloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelected(7);
                ((MainActivity) getActivity()).changePet(7);
            }
        });
        whale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
                highlightSelected(8);
                ((MainActivity) getActivity()).changePet(8);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).saveOptions(soundSwitch.isChecked());
            }
        });


    }

    private void highlightSelected(int id) {
        if (userInfo.getPet_sprite_ID() == 0) {
            chicken.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 1) {
            cow.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 2) {
            dog.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 3) {
            parrot.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 4) {
            penguin.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 5) {
            pig.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 6) {
            rhino.setBackgroundResource(R.drawable.button);
        } else if(userInfo.getPet_sprite_ID()==7) {
            sloth.setBackgroundResource(R.drawable.button);
        } else if (userInfo.getPet_sprite_ID() == 8) {
            whale.setBackgroundResource(R.drawable.button);
        }
        if (id == 0) {
            chicken.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 1) {
            cow.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 2) {
            dog.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 3) {
            parrot.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 4) {
            penguin.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 5) {
            pig.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 6) {
            rhino.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 7) {
            sloth.setBackgroundResource(R.drawable.button_highlight);
        } else if (id == 8) {
            whale.setBackgroundResource(R.drawable.button_highlight);
        }
    }

    private void highlightSelected(UserInfo ui){
        Log.d(TAG, String.valueOf(userInfo.getPet_sprite_ID()));
        if (userInfo.getPet_sprite_ID() == 0) {
            chicken.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 1) {
            cow.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 2) {
            dog.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 3) {
            parrot.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 4) {
            penguin.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 5) {
            pig.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 6) {
            rhino.setBackgroundResource(R.drawable.button_highlight);
        } else if(userInfo.getPet_sprite_ID() == 7) {
            sloth.setBackgroundResource(R.drawable.button_highlight);
        } else if (userInfo.getPet_sprite_ID() == 8) {
            whale.setBackgroundResource(R.drawable.button_highlight);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }
}
