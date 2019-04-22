package com.study.android.ex36_camera2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private static final String TAG = "lecture";

    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private Camera camera;
    boolean previewing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //권한 체크 후 사용자에 의해 취소되었다면 다시 요청
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    1 );
        }
    }

    public void onBtn1Clicked(View v){
        if(!previewing){
            camera = Camera.open();
            if(camera != null){
                try{
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.setDisplayOrientation(90);
                    camera.startPreview();
                    previewing = true;
                }catch (IOException e){
                    Log.d(TAG, "btn1clicked");
                }
            }
        }
    }

    public void onBtn2Clicked(View v){
        if(camera != null && previewing){
            camera.stopPreview();
            camera.release();
            camera = null;
            previewing = false;
        }
    }

    public void onBtn3Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }
}
