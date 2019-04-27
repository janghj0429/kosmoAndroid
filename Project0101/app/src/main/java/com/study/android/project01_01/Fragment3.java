package com.study.android.project01_01;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/*
 Fragment Life Style
 1. Fragment is added
 2. onAttach()         Fragment가 Activty에 붙을때 호출
 3. onCreate()          Activty에서의 onCreate()와 비슷하나 ui 관련 작업은 할 수 없다.
 4. onCreateView()      Layout을 inflater 을 하여 View 작업을 하는 곳
 5. onActivityCreated()  Activity에서 Fragment를 모두생성하고난 다음에 호출됨. Activty의 onCreate()에서 setContentView()한 다음과 같다
 6. onStart()           Fragment가 화면에 표시될때 호출, 사용자의 Action과 상호 작용이 불가능함
 7. onResume()          Fragment가 화면에 완전히 그렸으며,사용자의 Action과 상호 작용이 가능함
 8. Fragment is active
 9. User navigates backward or fragment is removed/replaced  or Fragment is added to the back stack, then removed/replaced
 10. onPause()
 11. onStop()           Fragment가 화면에서 더이상 보여지지 않게됬을때
 12. onDestroy()        view 리소스를 해제할수있도록 호출. backstack을 사용했다면 Fragment를 다시 돌아갈때 onCreateView()가 호출됨
 13. onDetached()
 14. Fragment is destroyed
 */

/**
 * Google Map CallStack
 * 1. onCreate()
 * 2. onCreateView()
 * 3. onActivityCreated()
 * 4. onStart();
 * 5. onResume();
 * 5-2. onMapReady();
 * 6. onPause();
 * 7. onSaveInstanceState();
 * 8. onMapReady();
 */





public class Fragment3 extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "lecture";

    private MapView mapView;
    private GoogleMap map;
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;
    MarkerOptions markerOptions;


    String address;
    String title;
    String mapX;
    String mapY;

    public static Fragment3 newInstance(){
//        Fragment3 fragment = new Fragment3();
//        Bundle mapData = new Bundle();
//        fragment.setArguments(bundle);
//        mapData.putString("address", address);
//        mapData.putString("title", title);
//        mapData.putString("mapX", mapX);
//        mapData.putString("mapY", mapY);
//        return fragment;
        return  new Fragment3();
    }

    public Fragment3() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
//        if(savedInstanceState != null )
//        {
//            onMapReady(map);
//        }
        Log.d(TAG, "onCreate3");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(getArguments() != null){
            address = getArguments().getString("address");
            title = getArguments().getString("title");
            mapX = getArguments().getString("mapX");
            mapY = getArguments().getString("mapY");
            Log.d(TAG, "oncreaterview지도보기 넘어온 정보" + address + title + mapX + mapY);

        }
        Log.d(TAG, "on Create View3");
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_fragment3, container, false);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest
                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        Log.d(TAG, "map 뷰 전3");
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        //mapView.onResume();
        mapView.getMapAsync(this);
        Log.d(TAG, "map 뷰 후3");
        //map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
//        try {
//            MapsInitializer.initialize(this.getActivity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Updates the location and zoom of the MapView
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        //map.animateCamera(cameraUpdate);

        Button btnMyLocation = rootView.findViewById(R.id.btnMyLocation);
        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
                //사용자 현재위치
                map.clear();
                Location userLocation = getMyLocation();
                if(userLocation != null){
                    double latitude = userLocation.getLatitude();
                    double longitude = userLocation.getLongitude();
                    LatLng userCurrent = new LatLng(latitude, longitude);

                    map.addMarker(new MarkerOptions()
                            .position(userCurrent)
                            .title("내위치").snippet("요기요~~~"));
                    map.getUiSettings().setZoomControlsEnabled(true);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userCurrent, 13);
                    map.animateCamera(cameraUpdate);
//                    userCurrent.setLat(latitude);
//                    userCurrent.setLon(longitude);
                    Log.d(TAG, "현재내위치: " +latitude +","+ longitude );
                }
            }
        });

        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        Double Lat = 37.56;
        Double Lng = 126.97;
        String name = "시작";
        String snippet = "지도입니다";

        if(title != null){
//            address = getArguments().getString("address");
//            title = getArguments().getString("title");
//            mapX = getArguments().getString("mapX");
//            mapY = getArguments().getString("mapY");
//            Log.d(TAG, "onmapready지도보기 넘어온 정보3" + address + title + mapX + mapY);

            Lat = Double.parseDouble(mapX);
            Lng = Double.parseDouble(mapY);
            name = title;
            snippet = address;
            searched();
        }

        Log.d(TAG, "정보 다음3");
        LatLng start = new LatLng(Lat, Lng);
        markerOptions = new MarkerOptions();
        markerOptions.position(start);
        markerOptions.title(name).snippet(snippet);
        map.addMarker(markerOptions);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 16));
        Log.d(TAG, "movecamera 다음3");

        if(getArguments() != null){
            address = getArguments().getString("address");
            title = getArguments().getString("title");
            mapX = getArguments().getString("mapX");
            mapY = getArguments().getString("mapY");
            Log.d(TAG, "onmapready지도보기 넘어온 정보3" + address + title + mapX + mapY);

            Lat = Double.parseDouble(mapX);
            Lng = Double.parseDouble(mapY);
            name = title;
            snippet = address;
            searched();
        }



        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Geocoder geocoder = new Geocoder(getContext());
                String address = "";
                try{
                    List<Address> resultList = geocoder.getFromLocation(
                            point.latitude, point.longitude,1
                    );
                    Log.d(TAG,"주소 : " + resultList.get(0).getAddressLine(0));
                    address = resultList.get(0).getAddressLine(0);
                }catch (IOException e){
                    e.printStackTrace();
                    Log.d(TAG,"주소실패3");
                }
                Log.d(TAG,"address : " + address);

                map.clear();
                map.addMarker(new MarkerOptions().position(point).title("주소").snippet(address));
                map.getUiSettings().setZoomControlsEnabled(true);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(point, 13);
                map.animateCamera(cameraUpdate);
            }
        });
    }
    public void searched(){
        Double Lat = Double.parseDouble(mapX);
        Double Lng = Double.parseDouble(mapY);
        String name = title;
        String snippet = address;

        LatLng start = new LatLng(Lat, Lng);
        markerOptions = new MarkerOptions();
        markerOptions.position(start);
        markerOptions.title(name).snippet(snippet);
        map.addMarker(markerOptions);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 16));


    }

    private Location getMyLocation(){
        Location currentLocation = null;
        //Register the listener with the Location Mannger to receive location update
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            System.out.println("////// 사용자에게 권한을 요청해야됨");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    this.REQUEST_CODE_LOCATION);
            getMyLocation();
        }else{
            //수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if(currentLocation != null){
                double lat = currentLocation.getLatitude();
                double lng = currentLocation.getLongitude();
            }
        }
        return currentLocation;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d(TAG, "onStart3");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        Log.d(TAG, "onStop3");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState3");
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.d(TAG, "onResume3");
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.d(TAG, "onPause3");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        Log.d(TAG, "onlowmemory3");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
        Log.d(TAG, "ondestroy3");
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//
////        MapsInitializer.initialize(getActivity().getApplicationContext());
//        //액티비티가 처음 생성될 때 실행되는 함수
//
//        if(mapView != null)
//        {
//            mapView.onCreate(savedInstanceState);
//        }
//
//    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        map = googleMap;
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//
//        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest
//                .permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//            } else {
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            }
//            map.setMyLocationEnabled(true);
//        }
//
//
//        //1번방법
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("서울");
//        markerOptions.snippet("수도");
//        googleMap.addMarker(markerOptions);
//        googleMap.getUiSettings().setZoomControlsEnabled(true);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
//        //googleMap.addMarker(new MarkerOptions().position(/*some location LATLNG latLNG*/));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
//
////
////        //2번방법
////        MapsInitializer.initialize(this.getActivity());
////
////        //update the location and zoom od the mapView
////        map.addMarker(new MarkerOptions()
////                .position(new LatLng(35.141233, 126.925594))
////                .title("루프리코리아"));
////
////        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.141233, 126.925594), 14);
////        map.animateCamera(cameraUpdate);
//    }

}
