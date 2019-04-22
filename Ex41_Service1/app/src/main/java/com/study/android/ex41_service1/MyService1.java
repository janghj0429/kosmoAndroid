package com.study.android.ex41_service1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService1 extends Service {
    private static final String TAG = "lecture";

    public MyService1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Log.d(TAG,"service start");

        MyThread1 thread = new MyThread1();
        thread.start();
    }

    class MyThread1 extends Thread{
        public void run(){
            for(int i=0; i<180; i++){
                Log.d(TAG, "service called.." + i);

                try{
                    Thread.sleep(1000);;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        Log.d(TAG,"service end");
    }
}
