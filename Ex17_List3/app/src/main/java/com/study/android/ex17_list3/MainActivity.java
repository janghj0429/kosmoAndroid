package com.study.android.ex17_list3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    String[] names = {"홍길동", "강감찬", "을지문덕", "양만춘", "유관순"};
    String[] ages = {"20", "25", "30", "35", "40"};
    int[] images = {R.drawable.face1, R.drawable.face2, R.drawable.face3,
                    R.drawable.face1, R.drawable.face2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //3단계
        final MyAdapter adapter = new MyAdapter();

        ListView listView1 = findViewById(R.id.listView1);

        listView1.setAdapter(adapter);

        //4단계
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getApplicationContext(),
                        "selected : " + names[position],
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //1단계
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount(){
            return names.length;
        }

        @Override
        public Object getItem(int position){
            return names[position];
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //1-1단계
//            TextView view1 = new TextView(getApplicationContext());
//            view1.setText(names[position]);
//            view1.setTextSize(40.0f);
//            view1.setTextColor(Color.BLUE);
//
//           layout.addView(view1);
//
//            //3단계
//            LinearLayout layout = new LinearLayout(getApplicationContext());
//            layout.setOrientation(LinearLayout.VERTICAL);
//
//            layout.addView(view1);
//
//            TextView view2 = new TextView(getApplicationContext());
//            view2.setText(ages[position]);
//            view2.setTextSize(40.0f);
//            view2.setTextColor(Color.RED);
//
//            layout.addView(view2);
//
//            return layout;

            //2단계
            SingerItemView view = new SingerItemView(getApplicationContext());
            view.setName(names[position]);
            view.setAge(ages[position]);
            view.setImage(images[position]);

            return view;
        }
    }
}
