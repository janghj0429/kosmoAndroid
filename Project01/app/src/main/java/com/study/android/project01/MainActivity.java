package com.study.android.project01;

import android.os.AsyncTask;
import android.os.Looper;

import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView textView1;
    Button btn1;

    Document doc = null;    //기본 메뉴 xml
    URL url;
    String category = "http://api.visitkorea.or.kr/openapi/service/rest/" +
            "KorService/categoryCode?ServiceKey=" +
            "iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrSZzUpJjVSyo1Wp%2BBEvqJ8QGR" +
            "0GmwOwodXK%2F9jKoZQ%3D%3D";

    String typeIdNum = "";      // 여행 타입
    String bigCategory = "";    //대분류
    String middle ="";          //중분류
    String small = "";          //소분류
    String area = "";           //시도
    String sigungu = "";        //시군구

    boolean initem = false, inCode = false, inName = false, inrNum = false;
    String code = null, name = null, rnum = null;

    String strUrl;

    ArrayAdapter<String> adapter, menuSpin1, menuSpin2, menuSpin3, menuSpin4;

    String[] types = {"타입", "관광지", "문화시설", "행사/공연/축제", "여행코스", "레포츠",
            "숙박", "쇼핑", "음식점"};   //타입
    ArrayList<String> bigNames = new ArrayList<>();
    HashMap<String, String> bigNameCode = new HashMap<>();

    ArrayList<String> middleNames = new ArrayList<>();
    HashMap<String, String> middleNameCode = new HashMap<>();

    ArrayList<String> smallNames = new ArrayList<>();
    HashMap<String, String> smallNameCode = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bigNames.clear();
        bigNameCode.clear();
        middleNames.clear();
        middleNameCode.clear();
        smallNames.clear();
        smallNameCode.clear();

        textView1 = findViewById(R.id.textView1);
        btn1 = findViewById(R.id.btnSearch);

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        menuSpin1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spinner1.setAdapter(menuSpin1);
        spinner1.setSelection(0, false);

        final Spinner spinner2 = findViewById(R.id.spinner2);
        final Spinner spinner3 = findViewById(R.id.spinner3);
        final Spinner spinner4 = findViewById(R.id.spinner4);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "g1");
                textView1.setText(types[position]);
                if (types[position].equals("타입")) {
                    typeIdNum = "";
                } else if (types[position].equals("관광지")) {
                    typeIdNum = "12";
                } else if (types[position].equals("문화시설")) {
                    typeIdNum = "14";
                } else if (types[position].equals("행사/공연/축제")) {
                    typeIdNum = "15";
                } else if (types[position].equals("여행코스")) {
                    typeIdNum = "25";
                } else if (types[position].equals("레포츠")) {
                    typeIdNum = "28";
                } else if (types[position].equals("숙박")) {
                    typeIdNum = "32";
                } else if (types[position].equals("쇼핑")) {
                    typeIdNum = "38";
                } else if (types[position].equals("음식점")) {
                    typeIdNum = "39";
                }
                strUrl = category + "&contentTypeId=" + typeIdNum +
                        "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest";
                Log.d(TAG, "g2");

                bigNames.clear();
                bigNameCode.clear();
                middleNames.clear();
                middleNameCode.clear();
                smallNames.clear();
                smallNameCode.clear();

                try {
                    StrictMode.enableDefaults();
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
                                    bigNames.add(name);
                                    bigNameCode.put(name, code);
                                    inName = false;
                                }
                                if (inrNum) {
                                    rnum = parser.getText();
                                    inrNum = false;
                                }

                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("item")) {
                                    //textView1.setText(textView1.getText() + "code : " + code + "\n name:" + name + "\nrnum:" + rnum);
                                    initem = false;
                                }
                                break;
                        }
                        parserEvent = parser.next();
                    }
                } catch (Exception e) {
                    textView1.setText("파싱 에러");
                }

                Log.d(TAG, "g3");
                menuSpin2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, bigNames);
                spinner2.setAdapter(menuSpin2);
                spinner2.setSelection(0, false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 스피너 2 선택시 중분류(스피너3) 메뉴 보이기
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                bigCategory = bigNameCode.get(spinner2.getSelectedItem().toString());
                Log.d(TAG, "sp2 : " + spinner2.getSelectedItem().toString());

                strUrl = category + "&contentTypeId=" + typeIdNum + "&cat1=" + bigCategory +
                        "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest";


                middleNames.clear();
                middleNameCode.clear();

                try {
                    StrictMode.enableDefaults();
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
                                    middleNames.add(name);
                                    inName = false;
                                    middleNameCode.put(name, code);
                                }
                                if (inrNum) {
                                    rnum = parser.getText();
                                    inrNum = false;
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("item")) {
                                    //textView1.setText(textView1.getText() + "code : " + code + "\n name:" + name + "\nrnum:" + rnum);
                                    initem = false;
                                }
                                break;
                        }
                        parserEvent = parser.next();
                    }
                } catch (Exception e) {
                    textView1.setText("파싱 에러");
                }
                menuSpin3 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, middleNames);
                spinner3.setAdapter(menuSpin3);
                spinner3.setSelection(0, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 스피너 3 선택시 소분류(스피너4) 에 대한 메뉴 보이게 받아오기
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                middle = middleNameCode.get(spinner3.getSelectedItem().toString());

                strUrl = category + "&contentTypeId=" + typeIdNum + "&cat1=" + bigCategory +
                        "&cat2=" + middle +
                        "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest";

                smallNames.clear();
                smallNameCode.clear();
                try {
                    StrictMode.enableDefaults();
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
                                    smallNames.add(name);
                                    inName = false;
                                    smallNameCode.put(name, code);
                                }
                                if (inrNum) {
                                    rnum = parser.getText();
                                    inrNum = false;
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (parser.getName().equals("item")) {
                                    //textView1.setText(textView1.getText() + "code : " + code + "\n name:" + name + "\nrnum:" + rnum);
                                    initem = false;
                                }
                                break;
                        }
                        parserEvent = parser.next();
                    }
                } catch (Exception e) {
                    textView1.setText("파싱 에러");
                }
                menuSpin4 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, smallNames);
                spinner4.setAdapter(menuSpin4);
                spinner4.setSelection(0, false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                small = smallNameCode.get(spinner4.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onBtn1Clicked(View v) {
        Log.d(TAG, "typeID : " + typeIdNum + " 대분류 : " + bigCategory +" 중분류:"+middle+" 소분류:"+small);
    }

    public void onBtn2Clicked(View v){
        strUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?" +
                "ServiceKey=iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrSZzUpJjV" +
                "Syo1Wp%2BBEvqJ8QGR0GmwOwodXK%2F9jKoZQ%3D%3D"+ "&contentTypeId=" + typeIdNum +
                "&areaCode=" + area + "&sigunguCode=" + sigungu + "&cat1=" + bigCategory +
                "&cat2=" + middle + "&cat3=" + small + "&listYN=Y&MobileOS=ETC&MobileApp=" +
                "TourAPI3.0_Guide&arrange=A&numOfRows=4&pageNo=1";

        try {
            StrictMode.enableDefaults();
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
                            inName = false;
                        }
                        if (inrNum) {
                            rnum = parser.getText();
                            inrNum = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            //textView1.setText(textView1.getText() + "code : " + code + "\n name:" + name + "\nrnum:" + rnum);
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            textView1.setText("파싱 에러");
        }
    }
}

//    protected class GetMenuXmlTask extends AsyncTask<String, Void, Document>{
//        @Override
//        protected Document doInBackground(String... urls){
//
//            try{
//                url = new URL(strUrl);
//                Log.d(TAG, "strUrl = " + strUrl);
//                Log.d(TAG, "url = " + url);
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                Log.d(TAG, "1");
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Log.d(TAG, "2");
//                doc = db.parse(new InputSource(url.openStream()));
//                Log.d(TAG, "3");
//                doc.getDocumentElement().normalize();
//                Log.d(TAG, "4");
//            }catch (Exception e){
//                Looper.prepare();
//                Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//                Log.d(TAG, "exception");
//            }
//
//            return doc;
//        }
//
//        @Override
//        protected void onPostExecute(Document doc) {
//            Log.d(TAG, "진행");
//
//            String tempC = "";
//            String tempN = "";
//            String r = "";
//
//            NodeList nodeList = doc.getElementsByTagName("item");
//
//            for(int i = 0; i< nodeList.getLength(); i++){
//
//                Node node = nodeList.item(i);
//                Element fstElmnt = (Element) node;
//
//                NodeList code = fstElmnt.getElementsByTagName("code");
//                //c += "code = "+  code.item(0).getChildNodes().item(0).getNodeValue() +"\n";
//                tempC = code.item(0).getChildNodes().item(0).getNodeValue();
//
//                NodeList name = fstElmnt.getElementsByTagName("name");
//                //n += "name = "+  name.item(0).getChildNodes().item(0).getNodeValue() +"\n";
//                tempN = name.item(0).getChildNodes().item(0).getNodeValue();
//
//                NodeList rnum  = fstElmnt.getElementsByTagName("rnum");
//                r += "rnum = "+ rnum.item(0).getChildNodes().item(0).getNodeValue() +"\n";
//
//                codes.add(tempC);
//                names.add(tempN);
//                nameCode.put(tempN, tempC);
//                // names.add(tempN);
//                //codes = new String[nodeList.getLength()];
//                Log.d(TAG, "0000리스트codes : " +codes);
//                Log.d(TAG, "0000리스트codes : " +names);
//               // Log.d(TAG, "0000배열 names : " +names[i]);
//            }
//
//            //super.onPostExecute(doc);
//        }
//
//    }
//
//
//    ////////////////////////////////////////////////////////////////////////////
//    class RequestThread extends Thread{
//        public void run() {
//            try {
//                url = new URL(strUrl);
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                Log.d(TAG, "thread");
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                doc = db.parse(new InputSource(url.openStream()));
//                doc.getDocumentElement().normalize();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            String tempC = "";
//            String tempN = "";
//            String r = "";
//
//            NodeList nodeList = doc.getElementsByTagName("item");
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//
//                Node node = nodeList.item(i);
//                Element fstElmnt = (Element) node;
//
//                NodeList code = fstElmnt.getElementsByTagName("code");
//                //c += "code = "+  code.item(0).getChildNodes().item(0).getNodeValue() +"\n";
//                tempC = code.item(0).getChildNodes().item(0).getNodeValue();
//
//                NodeList name = fstElmnt.getElementsByTagName("name");
//                //n += "name = "+  name.item(0).getChildNodes().item(0).getNodeValue() +"\n";
//                tempN = name.item(0).getChildNodes().item(0).getNodeValue();
//
//                NodeList rnum = fstElmnt.getElementsByTagName("rnum");
//                r += "rnum = " + rnum.item(0).getChildNodes().item(0).getNodeValue() + "\n";
//
////                Message msg = handler.obtainMessage();
////
////                Bundle bundle = new Bundle();
////                bundle.putString("data1", tempC);
////                bundle.putString("data2", tempN);
//
//                codes.add(tempC);
//                names.add(tempN);
//                nameCode.put(tempN, tempC);
//
//            }
//        }
//    }
//}

