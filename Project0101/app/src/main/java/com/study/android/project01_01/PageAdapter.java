package com.study.android.project01_01;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


public class PageAdapter extends FragmentStatePagerAdapter /*implements ViewPager.OnPageChangeListener*/{

    int mNumOfTabs;
    //Fragment3 fragment3 = new Fragment3();
    Bundle bundle;

    public PageAdapter(FragmentManager fm, int NumOfTabs,Bundle bundle){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position){

        switch (position){
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                Fragment3 fragment3 = new Fragment3();
                return fragment3;
            case 3:
                return new Fragment4();
            default:
                return null;
        }
    }


    public Fragment Change(int position, Bundle bundle){

        switch (position){
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                Fragment3 fragment3 = new Fragment3();
                return fragment3;
            case 3:
                return new Fragment4();
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumOfTabs;
    }

//    public Fragment change(Bundle bundle){
//        Fragment3 fragment3 = new Fragment3();
//        fragment3.newInstance(bundle);
//        return fragment3;
//    }

//    @Override
//    public int getItemPosition (Object object){
//        return POSITION_NONE;
//    }

//    @Override
//    public void onPageScrolled(int i, float v, int i1) {
//
//    }
//
//    @Override
//    public void onPageSelected(int i) {
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int i) {
//
//    }
}
