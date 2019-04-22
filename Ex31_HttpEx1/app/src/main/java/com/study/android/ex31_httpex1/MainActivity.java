package com.study.android.ex31_httpex1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    Button btnGetAct;
    Button btnPostAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetAct = findViewById(R.id.btnGetAct);
        btnPostAct = findViewById(R.id.btnPostAct);
    }

    public void onBtnGetAct(View v){
        Intent intent = new Intent(MainActivity.this, HttpGetActivity.class);
        startActivity(intent);
    }

    public void onBtnPostAct(View v){
        Intent intent = new Intent(MainActivity.this, HttpPostActivity.class);
        startActivity(intent);
    }
}
