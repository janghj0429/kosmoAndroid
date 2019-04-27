package com.study.android.project01_01;


import android.content.Intent;
import android.net.TrafficStats;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Fragment2 extends Fragment {
    private static final String TAG = "lecture";
    private static final int THREAD_ID = 10000;
    private static final int THREAD_ID2 = 10001;

    menuInfoHandler handler;
    ListHandler handler2;

    int step;

    String category = "http://api.visitkorea.or.kr/openapi/service/rest/" +
            "KorService/categoryCode?ServiceKey=" +
            "iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrSZzUpJjVSyo1Wp%2BBEvqJ8QGR" +
            "0GmwOwodXK%2F9jKoZQ%3D%3D";

    String typeIdNum = "";      // 여행 타입
    String bigCategory = "";    //대분류
    String middle = "";          //중분류
    String small = "";          //소분류
    String area = "";           //시도
    String sigungu = "";        //시군구

    String total = "";

    String strUrl;

    ArrayAdapter<String> menuSpin1, menuSpin2, menuSpin3, menuSpin4, menuSpin5, menuSpin6;
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;

    ListInfoItem item;
    ListInfoAdapter listInfoAdapter;
    RecyclerView mRecyclerView;

    String[] types = {"선택없음", "관광지", "문화시설", "행사/공연/축제", "여행코스", "레포츠",
            "숙박", "쇼핑", "음식점"};   //타입
    String[] sido = {"서울","인천","대전","대구","광주","부산","울산","세종특별자치시",
            "경기도","강원도","충청북도","충청남도","경상북도","경상남도","전라북도","전라남도",
            "제주도"}; //시도 지역선택

    ArrayList<String> bigNames = new ArrayList<>();
    HashMap<String, String> bigNameCode = new HashMap<>();

    ArrayList<String> middleNames = new ArrayList<>();
    HashMap<String, String> middleNameCode = new HashMap<>();

    ArrayList<String> smallNames = new ArrayList<>();
    HashMap<String, String> smallNameCode = new HashMap<>();

    ArrayList<String> sigunguNames = new ArrayList<>();
    HashMap<String, String> sigunguNameCode = new HashMap<>();

    TextView totalView;
    public Fragment2(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        handler = new menuInfoHandler();
        handler2 = new ListHandler();
        listInfoAdapter = new ListInfoAdapter(getContext());

        bigNames.clear();
        bigNameCode.clear();
        middleNames.clear();
        middleNameCode.clear();
        smallNames.clear();
        smallNameCode.clear();
        sigunguNames.clear();
        sigunguNameCode.clear();

        final ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_fragment2, container, false);

        Button btnSearch = rootView.findViewById(R.id.btnSearch);

        totalView = rootView.findViewById(R.id.totalView);

        spinner1 = rootView.findViewById(R.id.spinner1);
        spinner2 = rootView.findViewById(R.id.spinner2);
        spinner3 = rootView.findViewById(R.id.spinner3);
        spinner4 = rootView.findViewById(R.id.spinner4);
        spinner5 = rootView.findViewById(R.id.spinner5);
        spinner6 = rootView.findViewById(R.id.spinner6);

        menuSpin1 = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, types);
        spinner1.setAdapter(menuSpin1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (types[position].equals("선택없음")) {
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
                        "&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest";
                Log.d(TAG, "g2");

                bigNames.clear();
                bigNameCode.clear();
                middleNames.clear();
                middleNameCode.clear();
                smallNames.clear();
                smallNameCode.clear();

                step = 0;
                RequestThread thread = new RequestThread();
                thread.start();

                Log.d(TAG, "g3");
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
                        "&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest";

                middleNames.clear();
                middleNameCode.clear();
                smallNames.clear();
                smallNameCode.clear();

                step = 1;
                RequestThread thread = new RequestThread();
                thread.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 스피너 3 선택시 소분류(스피너4) 에 대한 메뉴 보이게 받아오기
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                middle = middleNameCode.get(spinner3.getSelectedItem().toString());

                strUrl = category + "&contentTypeId=" + typeIdNum + "&cat1=" + bigCategory +
                        "&cat2=" + middle +
                        "&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest";

                smallNames.clear();
                smallNameCode.clear();

                step = 2;
                RequestThread thread = new RequestThread();
                thread.start();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                small = smallNameCode.get(spinner4.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        menuSpin5 = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, sido);
        spinner5.setAdapter(menuSpin5);

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (sido[position].equals("서울")) {
                    area = "1";
                }else if (sido[position].equals("인천")) {
                    area = "2";
                }else if (sido[position].equals("대전")) {
                    area = "3";
                }else if (sido[position].equals("대구")) {
                    area = "4";
                }else if (sido[position].equals("광주")) {
                    area = "5";
                }else if (sido[position].equals("부산")) {
                    area = "6";
                }else if (sido[position].equals("울산")) {
                    area = "7";
                }else if (sido[position].equals("세종특별자치시")) {
                    area = "8";
                }else if (sido[position].equals("경기도")) {
                    area = "31";
                }else if (sido[position].equals("강원도")) {
                    area = "32";
                }else if (sido[position].equals("충청북도")) {
                    area = "33";
                }else if (sido[position].equals("충청남도")) {
                    area = "34";
                }else if (sido[position].equals("경상북도")) {
                    area = "35";
                }else if (sido[position].equals("경상남도")) {
                    area = "36";
                }else if (sido[position].equals("전라북도")) {
                    area = "37";
                }else if (sido[position].equals("전라남도")) {
                    area = "38";
                }else if (sido[position].equals("제주도")) {
                    area = "39";
                }

                sigunguNames.clear();
                sigunguNameCode.clear();

                strUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode" +
                        "?ServiceKey=iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrSZzU" +
                        "pJjVSyo1Wp%2BBEvqJ8QGR0GmwOwodXK%2F9jKoZQ%3D%3D&areaCode=" + area +
                        "&numOfRows=200&MobileOS=ETC&MobileApp=AppTest";

                step = 3;
                RequestThread thread1 = new RequestThread();
                thread1.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //시군구 선택시 작용
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sigungu = sigunguNameCode.get(spinner6.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mRecyclerView = rootView.findViewById(R.id.mRecyclerView);

        //버튼클릭메서드
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "type:" + typeIdNum + " big:" + bigCategory + " middle:" + middle +
                        " small:" + small + " area:" + area + " sigungu:" + sigungu);

                strUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?" +
                        "ServiceKey=iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrS" +
                        "ZzUpJjVSyo1Wp%2BBEvqJ8QGR0GmwOwodXK%2F9jKoZQ%3D%3D&" +
                        "contentTypeId=" + typeIdNum + "&areaCode=" + area + "&sigunguCode=" + sigungu +
                        "&cat1=" + bigCategory + "&cat2=" + middle + "&cat3=" + small + "&listYN=Y" +
                        "&MobileOS=ETC&MobileApp=AppTest&arrange=A&numOfRows=300";
                Log.d(TAG,"url"+strUrl);

                listInfoAdapter.items.clear();
                mRecyclerView.setAdapter(null);
                mRecyclerView.setLayoutManager(null);
                mRecyclerView.setItemAnimator(null);
                mRecyclerView.setAdapter(listInfoAdapter);
                listInfoAdapter.notifyDataSetChanged();


               // mRecyclerView.invalidate();
                ListRequestThread thread = new ListRequestThread();
                thread.start();
                //arrange = A 는 제목순임. numrow 30000 으로 하고 테스트시에는 5으로&pageNo=1
            }
        });

        //리싸이클 뷰 클릭메서드
        listInfoAdapter.setItemClick(new ListInfoAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                ListInfoItem detail = (ListInfoItem)listInfoAdapter.getItem(position);
                String contentId = detail.getContentId();
                String contentTypeId = detail.getContentTypeId();
                Intent intent = new Intent(getActivity(), Popup.class);
                intent.putExtra("contentId",contentId);
                intent.putExtra("contentTypeId",contentTypeId); // 검색시 필요

                String addr1 = detail.getAddr1();
                String addr2 = detail.getAddr2();
                String title = detail.getTitle();
                String mapX = detail.getMapX();
                String mapY = detail.getMapY();
                String zipcode = detail.getZipCode();
                String tel = detail.getTel();

                intent.putExtra("addr1",addr1);
                intent.putExtra("addr2",addr2);
                intent.putExtra("title",title);
                intent.putExtra("mapX",mapX);
                intent.putExtra("mapY",mapY);
                intent.putExtra("zipCode", zipcode);
                intent.putExtra("tel", tel);

                startActivity(intent);
            }
        });


        return rootView;
    }

    class RequestThread extends Thread{
        synchronized public void run() {
            TrafficStats.setThreadStatsTag(THREAD_ID);
            boolean inCode = false, inName = false, inrNum = false;
            String code = null, name = null, rnum = null;

            try {
                URL url = new URL(strUrl);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();
                parser.setInput(url.openStream(), null);
                int parserEvent = parser.getEventType();
                System.out.println("파싱시작");
                System.out.println(strUrl);
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();

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
                                Log.d(TAG, "code: " + code);
                                inCode = false;
                            }
                            if (inName) {
                                name = parser.getText();
                                Log.d(TAG, "name: " + name);

                                inName = false;
                            }
                            if (inrNum) {
                                rnum = parser.getText();
                                inrNum = false;
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                Log.d(TAG,"end tag");
                                bundle.putString("data1", code);
                                bundle.putString("data2", name);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                                Log.d(TAG,"전송");
                            }
                            if (parser.getName().equals("body")){
                                Log.d(TAG,"end body");
                                bundle.putString("data3", "end");
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "exception" + e);
            }
        }
    }

    class menuInfoHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            String data1 = bundle.getString("data1");
            String data2 = bundle.getString("data2");
            String data3 = bundle.getString("data3");
            Log.d(TAG, "d: " + data1 + data2 + data3);

            if(step == 0 && data3 == null){
                bigNames.add(data2);
                Log.d(TAG, "1");
                bigNameCode.put(data2,data1);
                Log.d(TAG, "2");
                Log.d(TAG, "big:" +bigNames);
            }else if(step == 0 && data3 != null){
                bigNames.add("선택없음");
                bigNameCode.put("선택없음","");
                menuSpin2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, bigNames);
                spinner2.setAdapter(menuSpin2);
                spinner2.setSelection(0);
            }
            if(step == 1 && data3 == null){
                middleNames.add(data2);
                Log.d(TAG, "3");
                middleNameCode.put(data2,data1);
                Log.d(TAG, "4");
                Log.d(TAG, "middle:" + middleNames);
            }else if(step == 1 && data3 != null){
                middleNames.add("선택없음");
                middleNameCode.put("선택없음","");
                menuSpin3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, middleNames);
                spinner3.setAdapter(menuSpin3);
                //spinner3.setSelection(0);
            }
            if(step == 2 && data3 == null){
                smallNames.add(data2);
                Log.d(TAG, "3");
                smallNameCode.put(data2,data1);
                Log.d(TAG, "4");
                Log.d(TAG, "small:" + smallNames);
            }else if(step == 2 && data3 != null){
                smallNames.add("선택없음");
                smallNameCode.put("선택없음","");
                menuSpin4 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, smallNames);
                spinner4.setAdapter(menuSpin4);
                //spinner4.setSelection(0);
            }
            if(step == 3 && data3 == null){
                sigunguNames.add(data2);
                Log.d(TAG, "3");
                sigunguNameCode.put(data2,data1);
                Log.d(TAG, "4");
                Log.d(TAG, "sigungu:" + sigunguNames);
            }else if(step == 3 && data3 != null){
                sigunguNames.add("선택없음");
                sigunguNameCode.put("선택없음","");
                menuSpin6 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sigunguNames);
                spinner6.setAdapter(menuSpin6);
                //spinner6.setSelection(0);
            }
        }
    }

    class ListRequestThread extends Thread{
        synchronized public void run() {
            boolean nAddr1=false , nAddr2=false , nAreaCode=false , nCat1=false , nCat2=false ,
                    nCat3=false , nContentId=false, nContentTypeId=false, nCreatedTime=false,
                    nFirstImage=false, nFirstImage2=false, nMapX=false, nMapY=false,
                    nMlevel=false, nModifiedTime=false, nOverView=false,nReadCount=false,
                    nSigunguCode=false, nTel=false, nTitle=false, nZipCode=false, nTotal=false;

            String addr1="", addr2="", areaCode="", cat1="", cat2="", cat3="",
                    contentId="", contentTypeId="", createdTime="", firstImage="",
                    firstImage2="", mapX="", mapY="", mLevel="", modifiedTime="",
                    overView="", readCount="", sigunguCode="", tel="", title="", zipcode="";

            try {
                TrafficStats.setThreadStatsTag(THREAD_ID2);
                URL url = new URL(strUrl);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();
                parser.setInput(url.openStream(), null);
                int parserEvent = parser.getEventType();
                System.out.println("파싱시작");
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    Message msg = handler2.obtainMessage();
                    Bundle bundle = new Bundle();
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("addr1")) {
                                nAddr1 = true;
                            }
                            if (parser.getName().equals("addr2")) {
                                nAddr2 = true;
                            }
                            if (parser.getName().equals("areacode")) {
                                nAreaCode = true;
                            }
                            if (parser.getName().equals("cat1")) {
                                nCat1 = true;
                            }
                            if (parser.getName().equals("cat2")) {
                                nCat2 = true;
                            }
                            if (parser.getName().equals("cat3")) {
                                nCat3 = true;
                            }
                            if (parser.getName().equals("contentid")) {
                                nContentId = true;
                            }
                            if (parser.getName().equals("contenttypeid")) {
                                nContentTypeId = true;
                            }
                            if (parser.getName().equals("createdtime")) {
                                nCreatedTime = true;
                            }
                            if (parser.getName().equals("firstimage")) {
                                nFirstImage = true;
                            }
                            if (parser.getName().equals("firstimage2")) {
                                nFirstImage2 = true;
                            }
                            if (parser.getName().equals("mapx")) {
                                nMapX = true;
                            }
                            if (parser.getName().equals("mapy")) {
                                nMapY = true;
                            }
                            if (parser.getName().equals("mlevel")) {
                                nMlevel = true;
                            }
                            if (parser.getName().equals("modifiedtime")) {
                                nModifiedTime = true;
                            }
                            if (parser.getName().equals("overview")) {
                                nOverView = true;
                            }
                            if (parser.getName().equals("readcount")) {
                                nReadCount = true;
                            }
                            if (parser.getName().equals("sigungucode")) {
                                nSigunguCode = true;
                            }
                            if (parser.getName().equals("tel")) {
                                nTel = true;
                            }
                            if (parser.getName().equals("title")) {
                                nTitle = true;
                            }
                            if (parser.getName().equals("zipcode")) {
                                nZipCode = true;
                            }
                            if (parser.getName().equals("totalCount")) {
                                nTotal = true;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if (nAddr1) {
                                addr1 = parser.getText();
                                nAddr1 = false;
                            }
                            if (nAddr2) {
                                addr2 = parser.getText();
                                nAddr2 = false;
                            }
                            if (nAreaCode) {
                                areaCode = parser.getText();
                                nAreaCode = false;
                            }
                            if (nCat1) {
                                cat1 = parser.getText();
                                nCat1 = false;
                            }
                            if (nCat2) {
                                cat2 = parser.getText();
                                nCat2 = false;
                            }
                            if (nCat3) {
                                cat3 = parser.getText();
                                nCat3 = false;
                            }
                            if (nContentId) {
                                contentId = parser.getText();
                                nContentId = false;
                            }
                            if (nContentTypeId) {
                                contentTypeId = parser.getText();
                                nContentTypeId = false;
                            }
                            if (nCreatedTime) {
                                createdTime = parser.getText();
                                nCreatedTime = false;
                            }
                            if (nFirstImage) {
                                firstImage = parser.getText();
                                nFirstImage = false;
                            }
                            if (nFirstImage2) {
                                firstImage2 = parser.getText();
                                nFirstImage2 = false;
                            }
                            if (nMapX) {
                                mapX = parser.getText();
                                nMapX = false;
                            }
                            if (nMapY) {
                                mapY = parser.getText();
                                nMapY = false;
                            }
                            if (nMlevel) {
                                mLevel = parser.getText();
                                nMlevel = false;
                            }
                            if (nModifiedTime) {
                                modifiedTime = parser.getText();
                                nModifiedTime = false;
                            }
                            if (nOverView) {
                                overView = parser.getText();
                                nOverView = false;
                            }
                            if (nReadCount) {
                                readCount = parser.getText();
                                nReadCount = false;
                            }
                            if (nSigunguCode) {
                                sigunguCode = parser.getText();
                                nSigunguCode = false;
                            }
                            if (nTel) {
                                tel = parser.getText();
                                nTel = false;
                            }
                            if (nTitle) {
                                title = parser.getText();
                                nTitle = false;
                            }
                            if (nZipCode) {
                                zipcode = parser.getText();
                                nZipCode = false;
                            }
                            if (nTotal) {
                                total = parser.getText();
                                nTotal = false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                bundle.putString("addr1",addr1);
                                bundle.putString("addr2",addr2);
                                bundle.putString("areaCode",areaCode);
                                bundle.putString("cat1",cat1);
                                bundle.putString("cat2",cat2);
                                bundle.putString("cat3",cat3);
                                bundle.putString("contentId",contentId);
                                bundle.putString("contentTypeId",contentTypeId);
                                bundle.putString("createdTime",createdTime);
                                bundle.putString("firstImage",firstImage);
                                bundle.putString("firstImage2",firstImage2);
                                bundle.putString("mapX",mapX);
                                bundle.putString("mapY",mapY);
                                bundle.putString("mLevel",mLevel);
                                bundle.putString("modifiedTime",modifiedTime);
                                bundle.putString("overView",overView);
                                bundle.putString("readCount",readCount);
                                bundle.putString("sigunguCode",sigunguCode);
                                bundle.putString("tel",tel);
                                bundle.putString("title",title);
                                bundle.putString("zipcode",zipcode);
                                msg.setData(bundle);
                                handler2.sendMessage(msg);
                                Log.d(TAG,"전송");

                                firstImage = null;
                            }
                            if (parser.getName().equals("body")){
                                //bundle.putString("totalCount",total);
                                totalView.setText("총 검색결과 : "+total);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ListHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String addr1 = bundle.getString("addr1");
            String addr2 = bundle.getString("addr2");
            String areaCode = bundle.getString("areaCode");
            String cat1 = bundle.getString("cat1");
            String cat2 = bundle.getString("cat2");
            String cat3 = bundle.getString("cat3");
            String contentId = bundle.getString("contentId");
            String contentTypeId = bundle.getString("contentTypeId");
            String createdTime = bundle.getString("createdTime");
            String firstImage = bundle.getString("firstImage");
            String firstImage2 = bundle.getString("firstImage2");
            String mapX = bundle.getString("mapX");
            String mapY = bundle.getString("mapY");
            String mLevel = bundle.getString("mLevel");
            String modifiedTime = bundle.getString("modifiedTime");
            String overView = bundle.getString("overView");
            String readCount = bundle.getString("readCount");
            String sigunguCode = bundle.getString("sigunguCode");
            String tel = bundle.getString("tel");
            String title = bundle.getString("title");
            String zipcode = bundle.getString("zipcode");
            //String total = bundle.getString("totalCount");

            Log.d(TAG,"list item:" + addr1 + readCount + title);

            item = new ListInfoItem(addr1,addr2,areaCode,cat1,cat2,
                    cat3,contentId,contentTypeId,createdTime,firstImage,firstImage2,
                    mapX,mapY,mLevel,modifiedTime,overView,readCount,sigunguCode,
                    tel,title,zipcode);
            //listInfoAdapter = new ListInfoAdapter(getContext());
            listInfoAdapter.addItem(item);
            listInfoAdapter.notifyDataSetChanged();
            Log.d(TAG, "item count : " + listInfoAdapter.getItemCount());

            mRecyclerView.setAdapter(listInfoAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            Log.d(TAG,"6");
        }
    }
}
