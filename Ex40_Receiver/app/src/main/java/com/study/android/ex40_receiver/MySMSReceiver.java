package com.study.android.ex40_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MySMSReceiver extends BroadcastReceiver {
    private static final String TAG = "lecture";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.d(TAG, "onReceive() called..");

        //--------------------------------------------------
        Bundle bundle = intent.getExtras();
        Object[] objs = (Object[]) bundle.get("pdus");

        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i<objs.length; i++){
            messages[i] = SmsMessage.createFromPdu( (byte[]) objs[i]);
        }
        //-----------------------------------------------------------

        String sender = messages[0].getOriginatingAddress();
        String contents = messages[0].getMessageBody();

        Log.d(TAG, "발신번호: " + sender);
        Log.d(TAG, "내용 :" + contents);

        //브로드 캐스트 메시지 전달 중지
        abortBroadcast();;

        loadDisplayActivity(context, sender, contents);
    }

    private void loadDisplayActivity(Context context, String sender, String contents){

        Intent intent = new Intent(context, SMSDisplayActivity.class);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);
    }
}
