package com.study.android.project01_01;

import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Fragment1 extends Fragment {
    private static final String TAG = "lecture";
    private static final int THREAD_ID = 10000;

    String strUrl;
    String cityName;
    String key;

    Handler handler;

    TextView textView0;
    TextView textView1;
    TextView textView2;
    TextView textView3;

    String[] city = {"Seoul", "Daejeon", "Daegu", "Busan", "Kwangju","Incheon",
            "Ulsan", "Sejong"};
    TextView[] textViews = new TextView[4];
    Integer[] textViewIds = {R.id.textView0, R.id.textView1, R.id.textView2, R.id.textView3};

    ArrayList<String> adapter = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        final ViewGroup rootView =
                (ViewGroup)inflater.inflate(R.layout.fragment_fragment1, container, false);

        handler = new Handler();

        strUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
        key = "&unit=metric&appid=2683694ec56def1ca521698e198f8714";

//        WeatherThread weatherThread = new WeatherThread();
//        weatherThread.start();
        //String cityID = "id=1835848";//서울
        new Thread(new Runnable() {
            @Override
            public void run() {
//                for(int i=0; i<textViewIds.length; i++){
//                    textViews[i] = rootView.findViewById(textViewIds[i]);
//                    cityName = city[i];
//                    strUrl = strUrl + cityName + key;
//                }
                TrafficStats.setThreadStatsTag(THREAD_ID);
                String receiveMsg;
                Log.d(TAG, "11");
                for(int i=0; i<city.length; i++){
                    cityName = city[i];
                    strUrl = strUrl + cityName + key;
                    try{
                        URL url = new URL(strUrl);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        //onn.setConnectTimeout(10000);
                        //conn.set
                        InputStream is = url.openStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        String data;
                        StringBuffer buffer = new StringBuffer();
                        while ((data = rd.readLine()) != null) {
                            buffer.append(data);
                        }
                        receiveMsg = buffer.toString();
                        Log.d(TAG, "receive" + receiveMsg);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0; i<textViewIds.length; i++) {
                                    textViews[i] = rootView.findViewById(textViewIds[i]);

                                }
                            }
                        });
                    }catch (Exception e){
                        Log.d(TAG, "22");
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return rootView;
    }

    class WeatherThread extends Thread{
        public void run() {
            TrafficStats.setThreadStatsTag(THREAD_ID);
            String receiveMsg;
            Log.d(TAG, "1");
            try {
                for(int i=0; i<city.length; i++){
                    strUrl = strUrl + city[i] + key;
                    URL url = new URL(strUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //onn.setConnectTimeout(10000);
                    //conn.set
                    InputStream is = url.openStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String data;
                    StringBuffer buffer = new StringBuffer();
                    while ((data = rd.readLine()) != null) {
                        buffer.append(data);
                    }
                    receiveMsg = buffer.toString();
                    Log.d(TAG, "receive" + receiveMsg);
                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    String weather = weatherArray.getJSONObject(0).getString("main");
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    String temp = mainObject.getString("temp");
                    String humidity = mainObject.getString("humidity");
                    Log.d(TAG,"날씨 " + weather+temp+humidity);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class WeatherHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            String data = bundle.getString("data");

            try{
                JSONObject jsonObject = new JSONObject(data);
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                String weather = weatherArray.getJSONObject(0).getString("main");
                JSONObject mainObject = jsonObject.getJSONObject("main");
                String temp = mainObject.getString("temp");
                String humidity = mainObject.getString("humidity");
                for(int i=0; i<8; i++){

                }
//                if(city.equals("Seoul")){
//                    Log.d(TAG, "날씨 : " + weather);
//                    Log.d(TAG, "온도" + temp);
//                    textView0.setText("날씨 : " + weather);
//                    textView1.setText("온도 : " + temp + " 도");
//                }else if(city.equals("Daejeon")){
//                    textView2.setText("날씨 : " + weather);
//                    textView3.setText("온도 : " + temp + " 도");
//                }

//                for(int i=0; i<listArray.length(); i++){
//                    try{
//                        JSONObject mainObject = listArray[i].getJSONObject("main");
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
