package com.study.android.project01_01;

import android.content.Intent;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.BufferUnderflowException;

public class Weather extends AppCompatActivity {
    private static final String TAG = "lecture";

    String strUrl;
    private static final int THREAD_ID = 10000;

//    WeatherHandler handler;
//    WheatherItem item;
//    WheatherAdapter adapter;
    RecyclerView wRecyclerView;
    String receiveMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        strUrl = intent.getStringExtra("strUrl");

//        WeatherThread weatherThread = new WeatherThread();
//        weatherThread.start();


    }

//    class WeatherThread extends Thread{
//        public void run(){
//            TrafficStats.setThreadStatsTag(THREAD_ID);
//
//            try{
//                URL url = new URL(strUrl);
//
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                //onn.setConnectTimeout(10000);
//                //conn.set
//                InputStream is = url.openStream();
//                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                String data;
//                StringBuffer buffer = new StringBuffer();
//                while( (data = rd.readLine()) != null){
//                    buffer.append(data);
//                }
//                receiveMsg = buffer.toString();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            Log.d(TAG,"receive" + receiveMsg);
//
////            try{
////                JSONObject jsonObject = new JSONObject(receiveMsg);
////                JSONArray listArray = jsonObject.getJSONArray("list");
////                Log.d(TAG, "list어레이" + listArray);
////
////                try{
////                    for(int i=0; i<listArray.length(); i++)
////                    //JSONObject mainObject = listArray[i].getJSONObject("main");
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////
////
////            }catch (Exception e){
////                e.printStackTrace();
////            }
//
//
////            Message msg = handler.obtainMessage();
////            Bundle bundle = new Bundle();
////            bundle.putString("data", receiveMsg);
////            msg.setData(bundle);
////            handler.sendMessage(msg);
//
//        }
//    }
//
//    class WeatherHandler extends Handler{
//        @Override
//        public void handleMessage(Message msg){
//            Bundle bundle = msg.getData();
//            String data = bundle.getString("data");
//
//            try{
//                JSONObject jsonObject = new JSONObject(data);
//                JSONArray listArray = jsonObject.getJSONArray("list");
//                Log.d(TAG, "list어레이" + listArray);
////                for(int i=0; i<listArray.length(); i++){
////                    try{
////                        JSONObject mainObject = listArray[i].getJSONObject("main");
////                    }catch (Exception e){
////                        e.printStackTrace();
////                    }
////                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//    }
}
