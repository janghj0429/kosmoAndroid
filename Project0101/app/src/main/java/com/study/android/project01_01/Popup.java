package com.study.android.project01_01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.study.android.project01_01.R.layout.*;

public class Popup extends Activity {
    private static final String TAG = "lecture";
    private static final int THREAD_ID3 = 10003;
    private static final int THREAD_ID4 = 10004;

    TextView titleView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView7;
    ImageView imageView1;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Button btnMap;
    Button btnClose;

    String strUrl;

    DetailHandler detailHandler;
    Handler handler = new Handler();

    String addr1;
    String addr2;
    String title;
    String mapX;
    String mapY ;
    String zipCode;
    String tel;

    Fragment3 fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(activity_popup);

        StrictMode.enableDefaults();
        detailHandler = new DetailHandler();
        fragment3 = new Fragment3();



        titleView = findViewById(R.id.titleView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView7 = findViewById(R.id.textView7);
        textView7.setMovementMethod(new ScrollingMovementMethod());
        imageView1 = findViewById(R.id.imageView1);

        //데이터 가져오기
        Intent intent = getIntent();
        String contentId = intent.getStringExtra("contentId");
        String contentTypeId = intent.getStringExtra("contentTypeId");

        addr1 = intent.getStringExtra("addr1");
        addr2 = intent.getStringExtra("addr1");
        title = intent.getStringExtra("title");
        mapX = intent.getStringExtra("mapX");
        mapY = intent.getStringExtra("mapY");
        zipCode = intent.getStringExtra("zipCode");
        tel = intent.getStringExtra("tel");

        titleView.setText(title);
        textView1.setText("우편번호 : " +zipCode);
        textView2.setText("업체명 : " + title);
        textView3.setText("전화 : " + tel);
        if(addr1 == addr2){
            textView4.setText("주소 : " + addr1);
        }else{
            textView4.setText("주소 : " + addr1 + " " + addr2);
        }

        strUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?" +
                "ServiceKey=iC%2Fp2TIG9wLzHtciBfUvbwEjg0AC%2F52LhcSJNI7FGEcMrSZzUpJjVSyo1Wp%2BB" +
                "EvqJ8QGR0GmwOwodXK%2F9jKoZQ%3D%3D&contentTypeId=" + contentTypeId + "&contentId=" +
                contentId + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y" +
                "&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";

        DetailThread detailThread = new DetailThread();
        detailThread.start();

//        Button btnMap = findViewById(R.id.btnMap);
//        btnMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Fragment3 fragment3 = new Fragment3();
//                String fullAddress = addr1 + addr2;
//                Bundle bundle = new Bundle();
//                bundle.putString("address", fullAddress);
//                bundle.putString("title", title);
//                bundle.putString("mapX", mapX);
//                bundle.putString("mapY", mapY);
//                fragment3.setArguments(bundle);
//
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.container, fragment3);
//                fragmentTransaction.commit();
//


//                String fullAddress = addr1 + addr2;
//                Bundle bundle = new Bundle();
//                bundle.putString("address", fullAddress);
//                bundle.putString("title", title);
//                bundle.putString("mapX", mapX);
//                bundle.putString("mapY", mapY);

//                Intent intent = new Intent(getApplicationContext(), Fragment3.class);
//                intent.putExtra("test", "test");
//                startActivity(intent);

    }

    public void btnCloseClicked(View v){
        finish();
    }

    public void btnMapClicked(View v){

//        String fullAddress = addr1 + addr2;
//        Bundle bundle = new Bundle();
//        bundle.putString("address", fullAddress);
//        bundle.putString("title", title);
//        bundle.putString("mapX", mapX);
//        bundle.putString("mapY", mapY);
//        Fragment3 fragment3 = new Fragment3();
//        fragment3.setArguments(bundle);

//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id., fragment3)
//        fragmentTransaction.commit();


//        ((MainActivity)MainActivity.mainContext).viewPager.setCurrentItem(2);


        Intent intent = new Intent(getApplicationContext(), MapCheck.class);
        intent.putExtra("address", addr1);
        intent.putExtra("title", title);
        intent.putExtra("mapX", mapX);
        intent.putExtra("mapY", mapY);
        startActivity(intent);
//
//        ((MainActivity)MainActivity.mainContext).changeToMap(bundle);
//        finish();
//        Log.d(TAG, "btnMap 클릭!" + fullAddress+title);
//        finish();
    }

    class DetailThread extends Thread{
        public void run(){
            TrafficStats.setThreadStatsTag(THREAD_ID3);
            boolean nfirstImage = false, nhomepage = false, noverview = false;
            String firstImage = "", homePage = "", overView = "";

            try {
                URL url = new URL(strUrl);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();
                parser.setInput(url.openStream(), null);
                int parserEvent = parser.getEventType();
                System.out.println("파싱시작");
                System.out.println(strUrl);
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    Message msg = detailHandler.obtainMessage();
                    Bundle bundle = new Bundle();

                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:

                            if (parser.getName().equals("firstimage")) {
                                nfirstImage = true;
                            }
                            if (parser.getName().equals("homepage")) {
                                nhomepage = true;
                            }
                            if (parser.getName().equals("overview")) {
                                noverview = true;
                            }
                            break;
                        case XmlPullParser.TEXT:

                            if (nfirstImage) {
                                firstImage = parser.getText();
                                nfirstImage = false;
                            }
                            if (nhomepage) {
                                homePage = parser.getText();
                                nhomepage = false;
                            }
                            if (noverview) {
                                overView = parser.getText();
                                noverview = false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                Log.d(TAG,"end tag");
                                bundle.putString("firstImage", firstImage);
                                bundle.putString("homePage", homePage);
                                bundle.putString("overView", overView);
                                msg.setData(bundle);
                                detailHandler.sendMessage(msg);
                                Log.d(TAG,"전송");
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

    class DetailHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            final String firstImage = bundle.getString("firstImage");
            String homePage = bundle.getString("homePage");
            String overView = bundle.getString("overView");

            if(firstImage != null){
                Thread mThread = new Thread(new Runnable() {
                    @Override
                    public void run(){
                        try{
                            TrafficStats.setThreadStatsTag(THREAD_ID4);
                            URL url = new URL(firstImage);
                            //Web 에서 이미지를 가져온뒤 Bitmap 만든다.
                            InputStream is = url.openStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(is); //Bitmap 으로 변환
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView1.setImageBitmap(bitmap);
                                }
                            });
                            //viewHolder.imageView1.setImageBitmap(bitmap);
                        }catch (MalformedURLException e){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView1.setImageResource(R.drawable.noimage);
                                }
                            });
                            e.printStackTrace();
                        }catch (IOException e){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView1.setImageResource(R.drawable.noimage);
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
                mThread.start();
            }else if( firstImage == null || firstImage ==""){
                imageView1.setImageResource(R.drawable.noimage);
            }

            if( homePage != null){
                textView5.setText("홈페이지 : " + Html.fromHtml(homePage));
            }else if(homePage == null || homePage == ""){
                textView5.setText("홈페이지 : 홈페이지가 없습니다");
            }

            if( overView != null){
                textView7.setText(Html.fromHtml(overView) );
            }else if(overView == null || overView == ""){
                textView7.setText("내용 없음");
            }

        }
    }
}
