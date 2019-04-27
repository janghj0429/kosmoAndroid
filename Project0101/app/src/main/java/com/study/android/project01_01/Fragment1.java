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

public class Fragment1 extends Fragment {
    private static final String TAG = "lecture";
    private static final int THREAD_ID = 10000;

    String strUrl;
    String cityName;
    String key;

    WeatherHandler handler;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        ViewGroup rootView =
                (ViewGroup)inflater.inflate(R.layout.fragment_fragment1, container, false);

        handler = new WeatherHandler();

        textView1 = rootView.findViewById(R.id.textView1);
        textView2 = rootView.findViewById(R.id.textView2);
        textView3 = rootView.findViewById(R.id.textView3);
        textView4 = rootView.findViewById(R.id.textView4);

        strUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
        key = "&appid=2683694ec56def1ca521698e198f8714";

        cityName = "Seoul";

        strUrl = strUrl + cityName + key;

        WeatherThread weatherThread = new WeatherThread();
        weatherThread.start();
        //String cityID = "id=1835848";//서울
//        Button button = rootView.findViewById(R.id.btnSeoul);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cityID = "id=1835848";//서울
//                strUrl = strUrl + cityID + key;
//                Log.d(TAG, "fff111" + strUrl);
//                Intent intent = new Intent(getActivity(), Weather.class);
//                intent.putExtra("strUrl" , strUrl);
//                startActivity(intent);
//            }
//        });
        return rootView;
    }

    class WeatherThread extends Thread{
        public void run() {
            TrafficStats.setThreadStatsTag(THREAD_ID);
            String receiveMsg;
            try {
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
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("data", receiveMsg);
                msg.setData(bundle);
                handler.sendMessage(msg);
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
                Log.d(TAG, "날씨 : " + weather);
                textView1.setText("날씨 : " + weather);
                textView2.setText("습도 : " + humidity);
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
