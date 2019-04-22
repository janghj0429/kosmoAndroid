package com.study.android.ex24_fragment2;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String TAG = "lecture";

    String[] values = {"첫번째 이미지", "두번째 이미지", "세번째 이미지"};

//    public static interface ImageSelectionCallback{
//        public void onImageSelected(int position);
//    } 밖으로 빼지 않고 스태틱으로 만들면 리스트프레그먼트에서만 사용가능

    public ImageSelectionCallback callback;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof ImageSelectionCallback){
            callback = (ImageSelectionCallback)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(callback != null){
                    callback.onImageSelected(position);
                }
            }
        });
        return rootView;
    }

}
