package com.study.android.ex43_telephonyex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<AddressItem> items = new ArrayList<>();

    public AddressAdapter(Context context){
        this.context = context;
    }

    public void addItem(AddressItem item){
        items.add(item);
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        AddressItemView view = null;
        if(convertView == null)
        {
            view = new AddressItemView(context);
        }else{
            view = (AddressItemView)convertView;
        }

        final AddressItem item = items.get(position);
        view.setName(item.getName());
        view.setTel(item.getTelnum());
        view.setImage(item.getResId());

        //***********************************************************
        //리스트뷰안의 버튼 클릭 ㅇㅣ벤트 처리
        Button button1 = view.findViewById(R.id.btnTelNum);
        button1.setFocusable(false);
        button1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                String str = "tel:" + item.getTelnum();

                Log.d(TAG,str);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                context.startActivity(intent);
            }
        });
        //*********************************************************
        return view;
    }
}
