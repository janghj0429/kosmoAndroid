package com.study.android.ex25_tabbar;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TabLayout tabLayout;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabMenu);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, fragment1).commit();

        //Set TabSelectedListner
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "POS:"+tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container,fragment1).commit();
                        break;
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container,fragment2).commit();
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container,fragment3).commit();
                        break;
                    case 3:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container,fragment4).commit();
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
