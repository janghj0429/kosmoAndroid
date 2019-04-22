package com.study.android.ex20_1_exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ClassAdapter(this);

        ClassItem item1 = new ClassItem("female", "권다연", "010-6739-3977", R.drawable.kwondayeon);
        adapter.addItem(item1);
        ClassItem item2 = new ClassItem("male", "백지선", "010-5387-5728", R.drawable.baekjisun);
        adapter.addItem(item2);
        ClassItem item3 = new ClassItem("male", "박박박", "010-1111-1111", 0);
        adapter.addItem(item3);

        ClassItem item4 = new ClassItem("male", "황병운", "010-6739-3977", R.drawable.hwangbyoungwoon);
        adapter.addItem(item4);

        ClassItem item5 = new ClassItem("male", "지동원", "010-6739-3977", R.drawable.jidongwon);
        adapter.addItem(item5);

        ClassItem item6 = new ClassItem("male", "조준근", "010-6739-3977", R.drawable.jojoonkun);
        adapter.addItem(item6);

        ClassItem item7 = new ClassItem("male", "정의만", "010-6739-3977", R.drawable.jungwuiman);
        adapter.addItem(item7);
        ClassItem item8 = new ClassItem("female", "정수인", "010-6739-3977", R.drawable.jungsooin);
        adapter.addItem(item8);
        ClassItem item9 = new ClassItem("male", "장희준", "010-6739-3977", R.drawable.jangheejun);
        adapter.addItem(item9);
        ClassItem item10 = new ClassItem("male", "이철연", "010-6739-3977", R.drawable.leechulyun);
        adapter.addItem(item10);
        ClassItem item11 = new ClassItem("male", "이재환", "010-6739-3977", R.drawable.leejaehwan);
        adapter.addItem(item11);
        ClassItem item12 = new ClassItem("male", "이인회", "010-6739-3977", R.drawable.leeinhoi);
        adapter.addItem(item12);
        ClassItem item13 = new ClassItem("female", "이서현", "010-6739-3977", R.drawable.leeseohyun);
        adapter.addItem(item13);
        ClassItem item14 = new ClassItem("male", "이병헌", "010-6739-3977", R.drawable.leeinhoi);
        adapter.addItem(item14);
        ClassItem item15 = new ClassItem("male", "이인회", "010-6739-3977", R.drawable.leeinhoi);
        adapter.addItem(item15);
        ClassItem item16 = new ClassItem("male", "이인회", "010-6739-3977", R.drawable.leeinhoi);
        adapter.addItem(item16);




        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                ClassItem item = (ClassItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        "selected : " + item.getName(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
