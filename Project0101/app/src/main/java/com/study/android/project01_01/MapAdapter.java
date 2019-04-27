package com.study.android.project01_01;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MapAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<mapItem> items = new ArrayList<>();

    public MapAdapter(Context context){
        this.context = context;
    }

    public void addItem(mapItem item){
        items.add(item);
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
