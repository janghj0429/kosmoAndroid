package com.study.android.ex44_sqlite1;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    SQLiteDatabase database;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = findViewById(R.id.textView2);
    }

    public void onBtn1Clicked(View v){
        String dbName = "customer.sqlite";
        try{
            database = openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null);

            printInfo("데이터베이스 만듬: " + dbName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onBtn2Clicked(View v){
        String sql =
                "create table if not exists customer(name text, age integer, mobile text)";
        try{
            database.execSQL(sql);

            printInfo("테이블 만듬: customer");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onBtn3Clicked(View v){
        String sql1 = "insert into customer " +
                " (name, age, mobile) values ('홍길동', 20, '010-1111-2222') ";
        String sql2 = "insert into customer " +
                " (name, age, mobile) values ('강감찬', 25, '010-7777-2222') ";
        try{
            database.execSQL(sql1);
            printInfo("데이터 추가: 1");

            database.execSQL(sql2);
            printInfo("데이터 추가: 2");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onBtn4Clicked(View v){
        String sql = "select name, age, mobile from customer ";
        try{
            Cursor cursor = database.rawQuery(sql, null);

            int count = cursor.getCount();
            printInfo("데이터 갯수: " + count);

            int i = 0;
            while(i<count){
                cursor.moveToNext();

                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                Log.d(TAG, "# " + name + " : " + age + " : " + mobile);
                printInfo("# " + name + " : " + age + " : " + mobile);

                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printInfo(String msg){
        textView2.append(msg + "\n");
    }
}
