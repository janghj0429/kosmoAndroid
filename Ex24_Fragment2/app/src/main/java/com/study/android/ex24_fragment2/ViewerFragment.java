package com.study.android.ex24_fragment2;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class ViewerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String TAG = "lecture";

    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_viewer, container, false);

        imageView = rootView.findViewById(R.id.imageView);

        return  rootView;

    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }

}
