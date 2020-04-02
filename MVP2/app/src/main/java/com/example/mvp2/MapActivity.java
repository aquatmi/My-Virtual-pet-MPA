package com.example.mvp2;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapActivity";


    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String PET_HUNGER = "pet_hunger";
    private static final String PET_EXERCISE = "pet_exercise";
    private static final String PET_FUN = "pet_fun";
    private static final String PET_NAME = "pet_name";
    private static final String PET_SPRITE_ID = "pet_sprite_id";

    private static final String SOUND_SWITCH = "sound_switch";

    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    UserInfo userInfo;



    private GoogleMap mMap;
    private Button btn_search, btn_back;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Load user info
        userInfo = new UserInfo();
        userInfo = loadLocalData();

        // COLLECT INFO FROM VIEW
        btn_back = (Button) findViewById(R.id.back_btn);
        btn_search = (Button) findViewById(R.id.search_btn);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // ON CLICK LISTENERS
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                Random r = new Random();
                int random = r.nextInt(11);
                userInfo.setPet_hunger((userInfo.getPet_hunger() + random));
                if (userInfo.getPet_hunger() > 100){
                    userInfo.setPet_hunger(100);
                    savePet(userInfo);
                    Toast.makeText(MapActivity.this, "Your Pet is full!", Toast.LENGTH_SHORT).show();
                } else {
                    savePet(userInfo);
                    Toast.makeText(MapActivity.this, "You found " + random + " food!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(18)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(67.5f)                   // Sets the tilt of the camera to 10 degrees
                    .build();                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
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

    public void savePet(UserInfo ui){
        userInfo = ui;
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
