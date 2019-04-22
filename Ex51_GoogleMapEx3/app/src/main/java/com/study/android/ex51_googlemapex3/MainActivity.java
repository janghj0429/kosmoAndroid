package com.study.android.ex51_googlemapex3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private static final LatLng SEOUL   = new LatLng(37.566535, 126.977969);
    private static final LatLng DAEJEON = new LatLng(36.350412, 127.384548);
    private static final LatLng SUWEON  = new LatLng(37.263573, 127.028601);
    private static final LatLng BUSAN   = new LatLng(35.179554, 129.075642);
    private static final LatLng KWANGJU = new LatLng(35.159545, 126.852601);

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "GoogleMap is ready.");

                map = googleMap;

                requestMapDraw();
            }
        });

        try{
            MapsInitializer.initialize(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void requestMapDraw(){
        //폴리라인 그리기
        PolylineOptions myLine = new PolylineOptions()
                                    .width(5)
                                    .color(Color.BLUE);
        map.addPolyline((myLine)
                    .add(SEOUL, BUSAN, KWANGJU, DAEJEON, SUWEON));

        //Move the camera to the user's location and zoon in!
        //Zoom Level : 2.0 ~ 21.0
        //map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 7.0f));
    }
}
