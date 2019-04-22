package com.study.android.ex18_list4;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

//    String[] names = {"홍길동", "강감찬", "을지문덕", "양만춘", "유관순"};
//    String[] ages = {"20", "25", "30", "35", "40"};
//    int[] images = {R.drawable.face1, R.drawable.face2, R.drawable.face3,
//            R.drawable.face1, R.drawable.face2};

    MyAdapter adapter;
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyAdapter();

        SingerItem item1 = new SingerItem("홍길동", "20", R.drawable.face1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("이순신", "25", R.drawable.face2);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("김유신", "30", R.drawable.face3);
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

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
    }

    public void onBtn1Clicked(View v){
        String inputName = editText1.getText().toString();
        String inputAge = editText2.getText().toString();

        SingerItem item = new SingerItem(inputName, inputAge, R.drawable.face1);
        adapter.addItem(item);
        adapter.notifyDataSetChanged();
    }

    public void onBtn2Clicked(View v){
        //알럿 띄우고 맨 마지막 리스트 제거
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("삭제하시겠습니까?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("알림")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        adapter.deleteItem();
    }

    public void onKeydownClicked(View v){
        InputMethodManager mgr =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    class MyAdapter extends BaseAdapter{

        ArrayList<SingerItem> items = new ArrayList<>();

        public void addItem(SingerItem item){
            items.add(item);
        }

        public void deleteItem(){
            items.remove(getCount());
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

            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setAge(item.getAge());;
            view.setImage(item.getResId());

            return view;
        }
    }
}
