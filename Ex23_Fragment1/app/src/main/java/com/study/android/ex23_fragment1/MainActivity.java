package com.study.android.ex23_fragment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment =
                (MainFragment)getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        menuFragment = new MenuFragment();
    }

    public  void onFragmentChange(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, menuFragment).commit();
        }else if(index == 1){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment).commit();
        }
    }
}
