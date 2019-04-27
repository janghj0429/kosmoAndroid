package com.study.android.project01_01;

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

public class ListInfoAdapter extends RecyclerView.Adapter<ListInfoAdapter.ListInfoItemViewHolder> {
    private static final int THREAD_ID = 10001;
    private static final String TAG = "lecture";

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

    ArrayList<ListInfoItem> items = new ArrayList<>();


    public class ListInfoItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView1;
        protected TextView textView2;
        protected TextView textView3;
        protected ImageView imageView1;

        public ListInfoItemViewHolder(View view){

            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            textView3 = view.findViewById(R.id.textView3);
            imageView1 = view.findViewById(R.id.imageView1);
        }
    }

    public ListInfoAdapter(Context context){

        this.context = context;
    }

    public void addItem(ListInfoItem item){
        items.add(item);
    }

    //RecyclerView 에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야할때 호출된다
    @Override
    public ListInfoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Log.d(TAG,"adapter1");
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_info_item_view, viewGroup, false);

        ListInfoItemViewHolder viewHolder = new ListInfoItemViewHolder(view);

        return viewHolder;
    }

    //Adapter의 특정위치 (position)에 있는 데이터를 보여줘야 할때 호출됩니다.
    @Override
    public void onBindViewHolder(@NonNull final ListInfoItemViewHolder viewHolder, final int position){
        viewHolder.textView1.setText(items.get(position).getTitle());
        viewHolder.textView2.setText("Tel : "+items.get(position).getTel());
        viewHolder.textView3.setText("조회수 : "+items.get(position).getReadCount());
        StrictMode.enableDefaults();
        Log.d(TAG,"adapter2" + items.get(position).getTitle() + items.get(position).getTel());
        Log.d(TAG,"발리관광호텔:"+items.get(position).getFirstImage());


        if( items.get(position).getFirstImage() != null){
            Thread mThread = new Thread(new Runnable() {
                @Override
                public void run(){
                    try{
                        TrafficStats.setThreadStatsTag(THREAD_ID);
                        URL url = new URL(items.get(position).getFirstImage());
                        Log.d(TAG,"adapterthread:"+items.get(position).getFirstImage());
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
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.imageView1.setImageResource(R.drawable.noimage);
                            }
                        });
                        e.printStackTrace();
                    }catch (IOException e){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.imageView1.setImageResource(R.drawable.noimage);
                            }
                        });
                        e.printStackTrace();
                    }
                }
            });
            mThread.start();
        }else if(items.get(position).getFirstImage() == null || items.get(position).getFirstImage() ==""){
            viewHolder.imageView1.setImageResource(R.drawable.noimage);
        }

        Log.d(TAG,"adapter3" );

        final int num = position;

        //현재는 사진클릭 전체 클릭으로 바꿔줘야됨!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
        return items.size();
        //return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }
}
