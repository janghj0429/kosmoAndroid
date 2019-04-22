package com.study.android.ex43_telephonyex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//
//
//이건 이미지 없을 때 드로우어블에 잇는걸 비트맵으로 바꾸기
//

public class AddressItemView extends LinearLayout {

    TextView txtName;
    Button btnTelNum;
    ImageView imageView1;

    public AddressItemView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.address_item_view, this, true);

        txtName = findViewById(R.id.txtName);
        btnTelNum = findViewById(R.id.btnTelNum);
        imageView1 = findViewById(R.id.imageView1);
    }

    public void setName(String name){
        txtName.setText(name);
    }

    public void setTel(String telnum){
        btnTelNum.setText(telnum);
    }

    public void setImage(Bitmap photo){
        if(photo == null){
            Bitmap bitmap = BitmapFactory.
        }
        imageView1.setImageBitmap(photo);
        imageView1.setImageBitmap();
    }
}
