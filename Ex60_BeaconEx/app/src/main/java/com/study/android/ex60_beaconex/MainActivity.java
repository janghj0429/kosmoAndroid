package com.study.android.ex60_beaconex;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private BeaconManager beaconManager;
    private Region region;

    private boolean isConnected;

    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //비콘 매니저
        beaconManager = new BeaconManager(getApplicationContext());
        //본인이 연결할 Beacon의 ID와 Major / Minor Code 를 알아야한다.
        region = new Region("ranged region",
                UUID.fromString("FDA50693-A4E2-4FB1-AFCF-C6EB07647825"), null, null);

        textView1 = findViewById(R.id.textView1);
    }

    @Override
    public void onResume(){
        super.onResume();
        //블루투스 권한 및 활성화 코드.
        SystemRequirementsChecker.checkWithDefaultDialogs(MainActivity.this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                startRangingListener();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        beaconManager.disconnect();
    }

    public void startRangingListener() {

        beaconManager.startRanging(region);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(final Region region, List list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = (Beacon) list.get(0);
                    double ratio = nearestBeacon.getRssi() * 1.0 / nearestBeacon.getMeasuredPower();
                    // accureacy = 원래 측정 거리
                    double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
                    // metter2 = 텍스트뷰 보이기 위한 용도
                    String metter2 = String.valueOf(accuracy).substring(0, 4);
                    // metter = 수신강도 재기위한 용도
                    int metter = (int) accuracy;
                    textView1.setText(metter2);

                    //거리가 5미터 이상일때 알림창을 띄운다
                    if (!isConnected && metter < 10) {
                        isConnected = true;

                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("알림")
                                .setMessage("비콘이 연결되었습니다")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create().show();
                    } else if (isConnected && metter >= 7) {

                        Toast.makeText(getApplicationContext(),
                                "연결이 끊어졌습니다",
                                Toast.LENGTH_SHORT).show();
                        isConnected = false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "연결이 되어있지 않습니다 다시 확인해주세요",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
