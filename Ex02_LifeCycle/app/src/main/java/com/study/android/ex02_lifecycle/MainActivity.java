package com.study.android.ex02_lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "onCreate() m호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate() m호출됨");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart() m호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart() m호출됨");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume() m호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume() m호출됨");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause() m호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause() m호출됨");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop() m호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop() m호출됨");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy() m호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy() m호출됨");
    }



    public void onBtn1Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), New2Activity.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);//1대신에 상수를 넣으면 좋다.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "콜백 함수 호출됨");

        if(requestCode == 1 && resultCode == 10){
            String sData = "";
            String str = "OnActivityResult() called : " +
                    requestCode + ":" + resultCode;

            sData = data.getStringExtra("BackData");
            str = str + ":" + sData;

            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            Log.d(TAG, str);
        }
    }
}
