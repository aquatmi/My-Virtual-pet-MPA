package com.example.mvp2;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;

public class UserInfo {
    private int pet_exercise;
    private int pet_fun;
    private int pet_hunger;
    private String pet_name;
    private int pet_sprite_ID;
    private String user_email;
    private String user_name;
    private Boolean sound_switch;

    public UserInfo(){
    }

    public int getPet_exercise() {return pet_exercise;}

    public int getPet_fun() {return pet_fun;}

    public int getPet_hunger() {return pet_hunger;}

    public String getPet_name() {return pet_name;}

    public int getPet_sprite_ID() {return pet_sprite_ID;}

    public String getUser_email() {return user_email;}

    public String getUser_name() {return user_name;}

    public Boolean getSound_switch() {
        return sound_switch;
    }

    public void setSound_switch(Boolean sound_switch) {
        this.sound_switch = sound_switch;
    }

    public void setPet_exercise(int pet_exercise) {this.pet_exercise = pet_exercise;}

    public void setPet_fun(int pet_fun) {this.pet_fun = pet_fun;}

    public void setPet_hunger(int pet_hunger) {this.pet_hunger = pet_hunger;}

    public void setPet_name(String pet_name) {this.pet_name = pet_name;}

    public void setPet_sprite_ID(int pet_sprite_ID) {this.pet_sprite_ID = pet_sprite_ID;}

    public void setUser_email(String user_email) {this.user_email = user_email;}

    public void setUser_name(String user_name) {this.user_name = user_name;}

}
