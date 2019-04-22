package com.study.android.ex54_recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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

        SingerItem item4 = new SingerItem("홍길동1", "20", R.drawable.face1);
        adapter.addItem(item4);

        SingerItem item5 = new SingerItem("이순신2", "25", R.drawable.face2);
        adapter.addItem(item5);

        SingerItem item6 = new SingerItem("김유신3", "30", R.drawable.face3);
        adapter.addItem(item6);

        mRecyclerView = findViewById(R.id.recyclerView1);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setItemClick(new SingerAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        "selected : " + item.getName() + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        nCount = 1;
    }

    public void onBtnClicked(View v) {
        nCount++;

        SingerItem item = new SingerItem("홍길동" + nCount, "20", R.drawable.face1);
        adapter.addItem(item);

        adapter.notifyDataSetChanged();
    }
}