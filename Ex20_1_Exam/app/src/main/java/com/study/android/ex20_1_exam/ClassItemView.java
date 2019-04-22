package com.study.android.ex20_1_exam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassItemView extends LinearLayout {
    private static final String TAG = "lecture";

    TextView textView1;
    TextView textView2;
    ImageView imageView1;

    LayoutInflater inflater;



    public ClassItemView(Context context, String gender){
        super(context);
        inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(gender.equals("male")){
            inflater.inflate(R.layout.man, this, true);
            textView1 = findViewById(R.id.textView1);
            textView2 = findViewById(R.id.textView2);
            imageView1 = findViewById(R.id.imageView1);
        }else{
            inflater.inflate(R.layout.woman, this, true);
            textView1 = findViewById(R.id.textView1);
            textView2 = findViewById(R.id.textView2);
            imageView1 = findViewById(R.id.imageView1);
        }

    }

    public void setName(String name){
        textView1.setText(name);
    }

    public void setTel(String telnum){
        textView2.setText(telnum);
    }

    public void setImage(int imgNum){
        Log.d(TAG, "imgNum : " + imgNum);
        if(imgNum == 0){
            imgNum = R.drawable.strawberry;
        }
        imageView1.setImageResource(imgNum);
    }

}
