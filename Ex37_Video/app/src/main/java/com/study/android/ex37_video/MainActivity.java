package com.study.android.ex37_video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    Button button2;
    VideoView videoView1;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView1 = findViewById(R.id.videoView1);
        button2 = findViewById(R.id.button2);
        button2.setEnabled(false);

        //권한 체크 후 사용자에 의해 취소되었다면 다시 요청
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1 );
        }

        if( ContextCompat.checkSelfPermission( this,
                Manifest.permission.RECORD_AUDIO )
                != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.RECORD_AUDIO},
                    1 );
        }

    }

    public void onBtn1Clicked(View v){
        recordVideo();
    }

    public void onBtn2Clicked(View v){
        showCapturedVideo();
    }

    private void recordVideo(){

        File externalStorageFile = Environment.getExternalStorageDirectory();
        File videoFilename = new File(externalStorageFile, "capture01.mp4");

        videoUri = FileProvider.getUriForFile(MainActivity.this,
                        getApplicationContext().getPackageName() + ".fileprovider",
                        videoFilename);

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == 1001){
            if(videoUri != null){
                button2.setEnabled(true);
            }
        }
    }

    private void showCapturedVideo(){
        videoView1.setVideoURI(videoUri);
        videoView1.start();
    }
}
