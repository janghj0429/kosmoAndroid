
package com.study.android.ex30webview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

public class CustomCircleProgressDialog extends Dialog {
    private TextView progressCntTv;
    private TextView progressTextTv;

    public CustomCircleProgressDialog(@NonNull Context context){

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//No Title
        setContentView(R.layout.custom_circle_progress);
    }
}