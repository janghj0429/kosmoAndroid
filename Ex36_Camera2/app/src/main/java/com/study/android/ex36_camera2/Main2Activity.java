package com.study.android.ex36_camera2;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class Main2Activity extends AppCompatActivity implements SurfaceHolder.Callback{
    private static final String TAG = "lecture";

    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private Camera camera;
    boolean previewing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //권한 체크 후 사용자에 의해 취소되었다면 다시 요청
        if(ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1 );
        }
    }

    public void onBtn1Clicked(View v){
        //촬영 기능 구현
        camera.takePicture(null, null, myPictureCallback_JPG);
    }

    //90회전하고 카메라 보이기 때문에 저장시 90도 다시 회전해서 저장해야됨.
    //지금 상태는 90회전된(카메라 보일때 90회전해서 보여줌) 원상태로 저장됨.
    Camera.PictureCallback myPictureCallback_JPG = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1){
            //Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0, arg0, length);

            //저장 기능 구현
            Uri uriTarget = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new ContentValues() );
            OutputStream imageFileOS;

            try{
                imageFileOS = getContentResolver().openOutputStream(uriTarget);
                imageFileOS.write(arg0);;
                imageFileOS.flush();
                imageFileOS.close();

                Toast.makeText(getApplicationContext(),
                        "Image saved: " + uriTarget.toString(),
                        Toast.LENGTH_LONG).show();
            }catch (FileNotFoundException e){
                Log.d(TAG, "FileNotFoundException");
                e.printStackTrace();
            }catch (IOException e){
                Log.d(TAG,"IOException");
                e.printStackTrace();
            }

            camera.startPreview();
        }
    };

    //------------------------------------------------------
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        camera = Camera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }
        if(camera != null){
            try{
                camera.setPreviewDisplay(surfaceHolder);
                camera.setDisplayOrientation(90);
                camera.startPreview();
                previewing = true;
            }catch (IOException e){
                Log.d(TAG, "surfaceChanged");
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }
}
