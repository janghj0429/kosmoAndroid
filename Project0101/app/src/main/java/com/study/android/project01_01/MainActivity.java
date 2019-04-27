package com.study.android.project01_01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "lecture";
    public static Context mainContext;

    ViewPager viewPager;
    TabLayout tabLayout;

    FragmentManager fragmentManager;
//    Fragment2 fragment2;
//    Fragment3 fragment3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = this;

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabMenu);

//        fragment3 = new Fragment3();

        PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), null);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Log.d(TAg, "POS:"+tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if(intent.getStringExtra("address") != null) {
            Bundle bundle = new Bundle();
            bundle.putString("address", intent.getStringExtra("address"));
            bundle.putString("title", intent.getStringExtra("title"));
            bundle.putString("mapX", intent.getStringExtra("mapX"));
            bundle.putString("mapY", intent.getStringExtra("mapY"));
            changeToMap(bundle);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeToMap(Bundle bundle){
        Log.d(TAG, "메인");
        //fragment3.onDestroy();

        viewPager.setCurrentItem(2);


//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.container, Fragment3.newInstance()).commit();
        //viewPager.setCurrentItem(2);
//        Fragment.instantiate(this, String.valueOf(Fragment3.class), bundle);

//        Fragment3 a = new Fragment3();
//        a.onCreate(bundle);
//        Fragment3 fragmen10 = new Fragment3();
//        fragmen10.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmen10)
//                .addToBackStack(Popup.class.getSimpleName()).commit();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, fragmen10)
//                .addToBackStack(Fragment2.class.getSimpleName())
//                .commit();


//         fragment3.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment3).commit();
//
//
//        Fragment3.newInstance(bundle);
//        PageAdapter adapter = new PageAdapter
//                (getSupportFragmentManager(), 2, bundle);
//        adapter.Change(2, bundle);
//
//        viewPager.setCurrentItem(2);
       // viewPager.setAdapter(adapter.change(Fragment3.newInstance(bundle)));
    //    viewPager.setCurrentItem(2);
//        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), bundle);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(
//                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //Log.d(TAg, "POS:"+tab.getPosition());
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//
        Log.d(TAG,"메인 끝");
    }
}
