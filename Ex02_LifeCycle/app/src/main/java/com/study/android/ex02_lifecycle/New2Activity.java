package com.study.android.ex02_lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class New2Activity extends AppCompatActivity {
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new2);

        Toast.makeText(getApplicationContext(), "onCreate() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate() 호출됨");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart() 호출됨");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume() 호출됨");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause() 호출됨");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop() 호출됨");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy() 호출됨", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy() 호출됨");
    }

    //창 닫기
    public void onBtn2Clicked(View v){
        //현재 인텐트 종료시 인텐트에 전달할 데이터 셋팅
        Intent intent = new Intent();
        intent.putExtra("BackData", "강감찬");
        setResult(10, intent);
        finish();
    }
}
