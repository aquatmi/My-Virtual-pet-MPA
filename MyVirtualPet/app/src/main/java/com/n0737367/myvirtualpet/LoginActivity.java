package com.n0737367.myvirtualpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout Layout1, Layout2;

    Handler handler = new Handler();

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
        setContentView(R.layout.activity_login);

        Layout1 = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        Layout2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);

        handler.postDelayed(runnable, 2000);
    }
}
