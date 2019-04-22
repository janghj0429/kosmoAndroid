package com.study.android.ex40_receiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class SMSDisplayActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsdisplay);

        Log.d(TAG,"onCreate..;");

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        //인텐트에 전달된 데이타 구하기
        Intent intent = getIntent();
        displayMessage(intent);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        Log.d(TAG,"onNewIntent...");

        displayMessage(intent);
    }

    private void displayMessage(Intent intent){
        String sender = intent.getStringExtra("sender");
        String contents = intent.getStringExtra("contents");

        editText1.setText(sender);
        editText2.setText(contents);
    }
}
