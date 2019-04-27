package com.study.android.rectest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SingerAdapter adapter;
    RecyclerView mRecyclerView;
    int nCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adapter = new MyAdapter();
        adapter = new SingerAdapter(this);

        SingerItem item1 = new SingerItem("홍길동1", "20", "http://tong.visitkorea.or.kr/cms/resource/05/2434905_image2_1.JPG");
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("홍길동2", "40", "http://tong.visitkorea.or.kr/cms/resource/48/2024248_image3_1.jpg");
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("홍길동3", "60", "http://tong.visitkorea.or.kr/cms/resource/00/2042500_image3_1.jpg");
        adapter.addItem(item3);

        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setItemClick(new SingerAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                String age = item.getAge();
                String name = item.getName();
                Intent intent = new Intent(MainActivity.this, Popup.class);
                intent.putExtra("age",age);
                intent.putExtra("name", name);
                startActivityForResult(intent,1);
            }
        });

        nCount = 1;
    }

    public void onBtnClicked(View v) {
        nCount++;

        //SingerItem item = new SingerItem("홍길동" + nCount, "20", R.drawable.face1);
        //adapter.addItem(item);

        adapter.notifyDataSetChanged();
    }
}