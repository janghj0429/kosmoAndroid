package com.study.android.project01_01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private static final int THREAD_ID = 10000;

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

    Context context;
    Bitmap bitmap;
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
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_info_item_view, viewGroup, false);

        ListInfoItemViewHolder viewHolder = new ListInfoItemViewHolder(view);

        return viewHolder;
    }

    //Adapter의 특정위치 (position)에 있는 데이터를 보여줘야 할때 호출됩니다.
    @Override
    public void onBindViewHolder(@NonNull ListInfoItemViewHolder viewHolder, final int position){
        viewHolder.textView1.setText(items.get(position).getTitle());
        viewHolder.textView2.setText(items.get(position).getTel());
        StrictMode.enableDefaults();
        //Thread 이용 웹에서 받아와서 이미지 지정정
       Thread mThread = new Thread(){
            @Override
            public void run(){
                try{
                    TrafficStats.setThreadStatsTag(THREAD_ID);
                    URL url = new URL(items.get(position).getFirstImage());
                    //Web 에서 이미지를 가져온뒤 Bitmap 만든다.
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true); //서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); //InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); //Bitmap 으로 변환
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        viewHolder.imageView1.setImageBitmap(bitmap);
//        try{//메인 쓰레드는 별도의 작업 쓰레드가 작업을 완료할 때 까지 대기해야한다.
//            //join()를 호출하여 별도의 작업 쓰레드가 종료될때까지 메인 쓰레드가 기다리게 한다.
//            mThread.join();
//
//            //작업 쓰레드에서 이미지를 불러오는 작업을 완료한뒤
//            //UI 작업을 할 수 있는 메인 쓰레드에서 이미지뷰에 이미지를 지정한다.
//            viewHolder.imageView1.setImageBitmap(bitmap);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        //viewHolder.imageView1.setImageResource(items.get(position).getAddr1());

        final int num = position;

        //현재는 사진클릭 전체 클릭으로 바꿔줘야됨!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        viewHolder.imageView1.setOnClickListener(new View.OnClickListener(){
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
