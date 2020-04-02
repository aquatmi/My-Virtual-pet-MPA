package com.example.mvp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


public class MapAct extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapAct";

    public MapAct(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        SupportMapFragment mapf = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapf != null) {
            mapf.getMapAsync(this);
        } else {
            Log.d(TAG, "FUFCCCCCCCCCAIOUWHRFIUABNFIAUBWIOUYBGIJHABWIOUYBOUIYABWORIUBY");
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.getUiSettings().setCompassEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(39.87266,-4.028275))
                .zoom(18)
                .tilt(67.5f)
                .bearing(314)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}