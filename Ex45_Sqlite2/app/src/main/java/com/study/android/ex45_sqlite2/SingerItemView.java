package com.study.android.ex45_sqlite2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {

    TextView textView1;
    TextView textView2;
    TextView textView3;

    public SingerItemView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_view, this, true);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
    }

    public void setName(String name){
        textView1.setText(name);
    }

    public void setAge(int age){
        textView2.setText(String.valueOf(age));
    }

    public void setmobile(String mobile){
        textView3.setText(mobile);
    }
}
