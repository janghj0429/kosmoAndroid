package com.study.android.project01_01;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapCheck extends AppCompatActivity{
    private static final String TAG = "lecture";

    SupportMapFragment mapFragment;
    GoogleMap map;

    MarkerOptions myLocationMarker;

    MapAdapter adapter;
//    String address;
//    String title;
//    String mapX;
//    String mapY;
//
//    Double Lat;
//    Double Lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_check);

//        Intent intent = getIntent();
//        address = intent.getStringExtra("address");
//        title = intent.getStringExtra("title");
//        mapX = intent.getStringExtra("mapX");
//        mapY = intent.getStringExtra("mapY");
//
//        Lat = Double.parseDouble(mapX);
//        Lng = Double.parseDouble(mapY);
//        Log.d(TAG, "위치 " + Lat + "   " + Lng);

//        adapter = new MapAdapter(this);
//        final mapItem item = new mapItem(address, title, mapX, mapY);
//        adapter.addItem(item);
//
        final TextView textView = findViewById(R.id.textView);
//        textView.setText(address +"  "+ title+"\n" + mapX + "    " + mapY);

        if (ContextCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                Log.d(TAG, "GoogleMap is ready.");

                Intent intent = getIntent();
                String address = intent.getStringExtra("address");
                String title = intent.getStringExtra("title");
                String mapX = intent.getStringExtra("mapX");
                String mapY = intent.getStringExtra("mapY");

                textView.setText(title + "   " + address + "\n" + mapY + "   " + mapX);

                Double Lat = Double.parseDouble(mapX);
                Double Lng = Double.parseDouble(mapY);
                Log.d(TAG, "위치 " + Lat + "   " + Lng);

                Location location = new Location("current");
                location.setLatitude(Lng);
                location.setLongitude(Lat);
                showCurrentLocation(location, address, title);
//                Log.d(TAG,"내위치");
//                LatLng current = new LatLng(Lat, Lng);
//                Log.d(TAG,"1" + Lat + Lng);
//                map.addMarker(new MarkerOptions()
//                        .position(current)
//                        .title(title).snippet(mapX)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation)));
//                Log.d(TAG,"2" + mapX + mapX);
//                map.getUiSettings().setZoomControlsEnabled(true);
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(current, 10);
//                map.animateCamera(cameraUpdate);
//                Log.d(TAG,"3");
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if(map != null){
//            LatLng selection = new LatLng(Lat, Lng);
//            final Marker destination = map.addMarker(new MarkerOptions()
//                    .position(selection)
//                    .title(title)
//                    .snippet(address)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation)));
//            map.getUiSettings().setZoomControlsEnabled(true);
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(selection, 10);
//            map.animateCamera(cameraUpdate);
//
//        }
    }

//    private void requestMyLocation() {
//        LocationManager manager =
//                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        try {
//            long minTime = 10000;
//            float minDistance = 0;
//            manager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            showCurrentLocation(location);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    }
//            );
//
//            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastLocation != null) {
//                showCurrentLocation(lastLocation);
//            }
//
//            manager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,
//                    minTime,
//                    minDistance,
//                    new LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                            showCurrentLocation(location);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    }
//            );
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }

    private void showCurrentLocation(Location location, String address, String title) {


        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        showMyLocationMarker(location, address, title);
    }

    private void showMyLocationMarker(Location location, String address, String title) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myLocationMarker.title(title);
            myLocationMarker.snippet(address);
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);

        } else {
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

//        if (map != null) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            map.setMyLocationEnabled(false);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (map != null) {
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            map.setMyLocationEnabled(true);
//        }
    }

    public void btnCloseClicked(View v){
        finish();
    }
}
