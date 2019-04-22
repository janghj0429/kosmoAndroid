package com.study.android.ex20_list6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingerAdapter(this);

        SingerItem item1 = new SingerItem("홍길동", "010-0000-0000", R.drawable.face1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("이순신", "010-1111-1111", R.drawable.face2);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("김유신", "010-2222-2222", R.drawable.face3);
        adapter.addItem(item3);

        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                SingerItem item = (SingerItem)adapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        "selected : " + item.getName(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

//    public void onBtn1Clicked(View v){
//        String inputName = editText1.getText().toString();
//        String inputAge = editText2.getText().toString();
//
//        SingerItem item = new SingerItem(inputName, inputAge, R.drawable.face1);
//        adapter.addItem(item);
//        adapter.notifyDataSetChanged();
//    }
}
