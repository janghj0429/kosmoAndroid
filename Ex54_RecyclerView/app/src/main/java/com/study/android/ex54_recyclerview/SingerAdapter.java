package com.study.android.ex54_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerItemViewHolder> {

    //--------------------------------------------------------------
    //아이템 클릭시 실행 함수
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ItemClick itemClick;

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    //--------------------------------------------------------------


    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();

    public class SingerItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView textView1;
        protected TextView textView2;
        protected ImageView imageView1;

        public SingerItemViewHolder(View view){
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            imageView1 = view.findViewById(R.id.imageView1);
        }
    }

    public SingerAdapter(Context context){
        this.context = context;
    }

    public void addItem(SingerItem item){
        items.add(item);
    }

    //RecyclerView 에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야할때 호출된다
    @Override
    public SingerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.singer_item_view, viewGroup, false);

        SingerItemViewHolder viewHolder = new SingerItemViewHolder(view);

        return viewHolder;
    }

    //Adapter의 특정위치 (position)에 있는 데이터를 보여줘야 할때 호출됩니다.
    @Override
    public void onBindViewHolder(@NonNull SingerItemViewHolder viewHolder,int position){
        viewHolder.textView1.setText(items.get(position).getName());
        viewHolder.textView2.setText(items.get(position).getAge());
        viewHolder.imageView1.setImageResource(items.get(position).getResId());

        final int num = position;

        viewHolder.imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(itemClick != null){
                    itemClick.onClick(v, num);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }
}


