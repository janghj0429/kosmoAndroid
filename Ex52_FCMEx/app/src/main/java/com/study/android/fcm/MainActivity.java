package com.study.android.fcm;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView log;
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log = (TextView)findViewById(R.id.log);

        //앱이 실행 할 떄 알림 메시지가 있었으면 데이터를 표시하도록 함.
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null){

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Noti - " + key + ":" + value );
            }
            /*
            Noti - google.delivered_priority:high
            Noti - google.sent_time:null
            Noti - google.ttl:null
            Noti - google.original_priority:high
            Noti - from:296042452497
            Noti - google.message_id:0:1537056511730816%46105f7f46105f7f
            Noti - message:여기는 처리하고자 하는 내용입니다.
            Noti - collapse_key:com.study.android.fcm
            */
             Log.d(TAG,"알림 메시지가 있어요");
             //알림을 클릭해서 앱이 실행되었을 때 메시지를 표시 한다.
            //알림 클릭 없이 앱을 바로 실행
            //데이터가 표시되지 않는다.
            //시스템 알림에 알림은 그래도 남아있다.
            String contents = intent.getStringExtra("message");
            if(contents != null){
                processIntent(intent);
            }
        }

        getRegistrationId();
    }

    public void getRegistrationId(){
        println("getRegistrationId() 호출됨.");

        regId = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "RegID:" + regId);
        println("regId : " + regId);

    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        println("onNewIntent() called.");

        if(intent != null){
            String contents = intent.getStringExtra("message");
            processIntent(intent);
        }
    }

    private void processIntent(Intent intent){

        println("DATA : " + contents);
    }

    public void println(String data){

        log.append(data + "\n");

    }
}
