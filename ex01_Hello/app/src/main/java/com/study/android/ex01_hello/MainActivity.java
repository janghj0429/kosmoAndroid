package com.study.android.ex01_hello;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setBackgroundColor(Color.BLUE);
        button1.setTextColor(Color.WHITE);
        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Log.d("lecture","로그 출력");

                Toast.makeText(getApplicationContext(), "긴 토스트", Toast.LENGTH_LONG).show();
            }                 //this 적어도 가능, 토스트 롱or 숏 만 가능 큰차이없음
        });
    }

    //버튼2
    //인텐트 만들어 웹브라우저 띄우기
    public void onButton2Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(intent);
    }

    //버튼3
    //인텐트 만들어 전화연결
    public void onButton3Clicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:01099154055"));
        startActivity(intent);
    }

    //버튼4
    //editText 값 읽어와서 textview에 할당하기
    public void onBtn4Clicked(View v){
        EditText editText = findViewById(R.id.editText1);
        TextView textView = findViewById(R.id.textView1);

        textView.setText(editText.getText());
    }


    //버튼5
    //새창 띄우기
    public void onBtn5Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        startActivity(intent);
    }
}
