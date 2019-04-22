package com.study.android.myapplication;

import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    //    URL url;
    String strUrl;
    Document doc = null;
    String data;

    TextView textView;

    ArrayList<String> items = new ArrayList<>();
    String[] items2 = {"mike", "angel"};
    String[] items3 = {"mike", "angel", "crow", "john"};
    ArrayList<String> names = new ArrayList<>();
    ArrayAdapter<String> spin1, spin2, spin3;

    String s1, s2, s3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.enableDefaults();
        boolean initem = false, inCode = false, inName = false, inrNum = false;
        String code = null, name = null, rnum = null;

        textView = findViewById(R.id.textView1);

//        String ss1 = "자연";
//        String ss2 = "인문";
//        String ss3 = "레포츠";
//        items.add(ss1);
//        items.add(ss2);
//        items.add(ss3);
//
//        String[] temp = new String[items.size()];
//        items.toArray(temp);
//        Log.d(TAG, "temp[]" + temp[0] + temp[1] + temp[2]);
//
        String category = "http://api.visitkorea.or.kr/openapi/service/rest/" +
                "KorService/categoryCode?ServiceKey=" +
                "iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrSZzUpJjVSyo1Wp%2BBEvqJ8QGR" +
                "0GmwOwodXK%2F9jKoZQ%3D%3D";
        strUrl = category + "&contentTypeId=&MobileOS=ETC&MobileApp=AppTest";
//        MenuRequestThread thread = new MenuRequestThread();
//        thread.start();

//        getXmlData(strUrl);
        try {
            Log.d(TAG, "1");
            URL url = new URL(strUrl);
            Log.d(TAG, "2");
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            Log.d(TAG, "3");
            XmlPullParser parser = parserCreator.newPullParser();
            Log.d(TAG, "4");
            parser.setInput(url.openStream(), null);
            Log.d(TAG, "5");
            int parserEvent = parser.getEventType();
            System.out.println("파싱시작");
            Log.d(TAG, "6");
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("code")) {
                            inCode = true;
                        }
                        if (parser.getName().equals("name")) {
                            inName = true;
                        }
                        if (parser.getName().equals("rnum")) {
                            inrNum = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (inCode) {
                            code = parser.getText();
                            inCode = false;
                        }
                        if (inName) {
                            name = parser.getText();
                            names.add(name);
                            inName = false;
                        }
                        if (inrNum) {
                            rnum = parser.getText();
                            inrNum = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            textView.setText(textView.getText() + "code : " + code + "\n name:" + name + "\nrnum:" + rnum);
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            textView.setText("파싱 에러");
        }
//        RequestThread thread = new RequestThread();
//        thread.start();
//        Log.d(TAG, "names = " + names);
//
        spin1 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, names);
        final Spinner spinner1 = findViewById(R.id.spinner1);
        spinner1.setAdapter(spin1);
        spinner1.setSelection(0, false);
//
//
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //아이템이 선택되었을 때 호출됨
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int position, long id) {

                s1 = spinner1.getSelectedItem().toString();

                spin2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, items2);
                final Spinner spinner2 = findViewById(R.id.spinner2);
                spinner2.setAdapter(spin2);
                spinner2.setSelection(0, false);

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        s2 = spinner2.getSelectedItem().toString();

                        spin3 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, items3);
                        final Spinner spinner3 = findViewById(R.id.spinner3);
                        spinner3.setAdapter(spin3);

                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                s3 = spinner3.getSelectedItem().toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            //아무것도 선택되지 않았을 때 호출됨
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView.setText("");
            }
        });
    }

    public void getXmlData(final String strUrl) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                data(strUrl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

    void data(String strUrl) {
        boolean initem = false, inCode = false, inName = false, inrNum = false;
        String code = null, name = null, rnum = null;

        try {

            Log.d(TAG, "1");
            URL url = new URL(strUrl);
            Log.d(TAG, "2");
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            Log.d(TAG, "3");
            XmlPullParser parser = parserCreator.newPullParser();
            Log.d(TAG, "4");
            parser.setInput(url.openStream(), null);
            Log.d(TAG, "5");
            int parserEvent = parser.getEventType();
            System.out.println("파싱시작");
            Log.d(TAG, "6");
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("code")) {
                            inCode = true;
                        }
                        if (parser.getName().equals("name")) {
                            inName = true;
                        }
                        if (parser.getName().equals("rnum")) {
                            inrNum = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (inCode) {
                            code = parser.getText();
                            inCode = false;
                        }
                        if (inName) {
                            name = parser.getText();
                            names.add(name);
                            inName = false;
                        }
                        if (inrNum) {
                            rnum = parser.getText();
                            inrNum = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            textView.setText(textView.getText() + "code : " + code + "\n name:" + name + "\nrnum:" + rnum);
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            textView.setText("파싱 에러");
        }
    }

    class MenuRequestThread extends Thread{
        public void run(){

        }
    }

    public void onBtn1Clicked(View v) {
        Log.d(TAG, "s1:" + s1 + "s2:" + s2 + "s3:" + s3);
    }
}
//
//    class RequestThread extends Thread{
//        public void run() {
//            data = getXmlData();
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    textView.setText(data);
//                }
//            });
//        }
//    }
//
//    String getXmlData(){
//        StringBuffer buffer = new StringBuffer();
//
//        String queryUrl = strUrl;
//
//        try{
//            url = new URL(strUrl);
//            InputStream is = url.openStream();
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
//            XmlPullParser xpp= factory.newPullParser();
//            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
//
//            String tag;
//
//            xpp.next();
//            int eventType= xpp.getEventType();
//
//            while( eventType != XmlPullParser.END_DOCUMENT ) {
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작...\n\n");
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag = xpp.getName();//태그 이름 얻어오기
//
//                        if (tag.equals("item")) ;// 첫번째 검색결과
//                        else if (tag.equals("code")) {
//                            buffer.append("코드 : ");
//                            xpp.next();
//                            buffer.append(xpp.getText());//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        } else if (tag.equals("name")) {
//                            buffer.append("이름 : ");
//                            xpp.next();
//                            buffer.append(xpp.getText());
//                            buffer.append("\n");
//                        } else if (tag.equals("rnum")) {
//                            buffer.append("알넘 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//cpId
//                            buffer.append("\n");
//                        }
//                        break;
//                    case XmlPullParser.TEXT:
//                        break;
//                    case XmlPullParser.END_TAG:
//                        tag = xpp.getName(); //태그 이름 얻어오기
//
//                        if (tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
//
//                        break;
//                }
//                eventType = xpp.next();
//            }
//        }catch (Exception e){
//
//        }
//
//        buffer.append("파싱 끝");
//        return buffer.toString();//StringBuffer 문자열 객체 반환
//    }
//}
