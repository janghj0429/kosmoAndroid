package com.study.android.rectest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerItemViewHolder> {
    private static final int THREAD_ID = 10002;
    private static final String TAG = "lecture";
    //--------------------------------------------------------------
    //아이템 클릭시 실행 함수
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ItemClick itemClick;

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    //--------------------------------------------------------------
    Handler handler = new Handler();

    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();

    public class SingerItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView1;
        protected TextView textView2;
        protected ImageView imageView1;

        public SingerItemViewHolder(View view){
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            imageView1 = view.findViewById(R.id.imageView1);
        }
    }

    public SingerAdapter(Context context){
        this.context = context;
    }

    public void addItem(SingerItem item){
        items.add(item);
    }

    //RecyclerView 에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야할때 호출된다
    @Override
    public SingerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.singer_item_view, viewGroup, false);

        SingerItemViewHolder viewHolder = new SingerItemViewHolder(view);

        return viewHolder;
    }

    //Adapter의 특정위치 (position)에 있는 데이터를 보여줘야 할때 호출됩니다.
//    class mThread extends Thread{
//        public void run(){
//            try{
//                    TrafficStats.setThreadStatsTag(THREAD_ID);
//                    URL url = new URL(items.get(position).getStr());
//                    Log.d(TAG,"adapterthread:"+items.get(position).getStr());
//                    //Web 에서 이미지를 가져온뒤 Bitmap 만든다.
//                    InputStream is = url.openStream();
//                    bitmap = BitmapFactory.decodeStream(is); //Bitmap 으로 변환
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            viewHolder.imageView1.setImageBitmap(bitmap);
//                        }
//                    });
//                    //viewHolder.imageView1.setImageBitmap(bitmap);
//                }catch (MalformedURLException e){
//                    e.printStackTrace();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//        }
//    }
    @Override
    public void onBindViewHolder(@NonNull final SingerItemViewHolder viewHolder, final int position){
        viewHolder.textView1.setText(items.get(position).getName());
        viewHolder.textView2.setText(items.get(position).getAge());
        StrictMode.enableDefaults();

//        Thread mThread = new Thread();
//        mThread.start();
        //Thread 이용 웹에서 받아와서 이미지 지정
        Thread mThread = new Thread(new Runnable() {

            @Override
            public void run(){
                try{
                    TrafficStats.setThreadStatsTag(THREAD_ID);
                    URL url = new URL(items.get(position).getStr());
                    Log.d(TAG,"adapterthread:"+items.get(position).getStr());
                    //Web 에서 이미지를 가져온뒤 Bitmap 만든다.
                    InputStream is = url.openStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is); //Bitmap 으로 변환
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.imageView1.setImageBitmap(bitmap);
                        }
                    });
                    //viewHolder.imageView1.setImageBitmap(bitmap);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
        Log.d(TAG,"adapter3");
       // viewHolder.imageView1.setImageBitmap(bitmap);
//        viewHolder.imageView1.setImageResource(items.get(position).getStr());

        final int num = position;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(itemClick != null){
                    itemClick.onClick(v, num);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }
}


