package com.study.android.ex01_hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
    }
    //창 닫기
    public void onBtn2Clicked(View v){
        finish();
    }
}
