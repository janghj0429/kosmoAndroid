package com.study.android.ex47_locationex;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.textView);

        //권한 체크후 사용자에 의해 취소되었다면 다시 요청
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    1 );
        }
    }

    public void onBtnClicked(View v){
        //위치 관리자에 대한 참조값을 구한다.
        LocationManager locationManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //위치가 업데이트 되면 호출 되는 리스너를 정의한다.
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                //새로운 위치가 발견되면 위치 제공자에 의하여 호출된다.
                status.setText( "위도: " + location.getLatitude() +
                            "\n경도: " + location.getLongitude() +
                            "\n고도: " + location.getAltitude());
            }


            public void onStatusChanged(String provider, int status, Bundle extras) {

            }


            public void onProviderEnabled(String provider) {

            }


            public void onProviderDisabled(String provider) {

            }
        };

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            //위치를 업데이트 받기 위하여 리스너를 위치 관리자에 등록한다.
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000,
                    1, locationListener);
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    1, locationListener);
        }
    }

    public void requestLocationUpdates(String provider, long minTime, float minDistance, LocationListener listener) {
        throw new RuntimeException("Stub!");
    }

}
