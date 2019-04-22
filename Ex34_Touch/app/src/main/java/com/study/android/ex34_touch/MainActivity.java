package com.study.android.ex34_touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    protected class MyView extends View {
        int x = 100, y = 100;

        String str;
        Paint paint;

        public MyView(Context context){
            super(context);
            setBackgroundColor(Color.YELLOW);

            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(50);
        }

        @Override
        protected void onDraw(Canvas canvas){
            canvas.drawCircle(x,y, 100, paint);
            canvas.drawText("액션의 종류: "+str, 0, 100, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            x = (int)event.getX();
            y = (int)event.getY();

            if(event.getAction() == MotionEvent.ACTION_DOWN)
                str = "ACTION_DOWN";
            if(event.getAction() == MotionEvent.ACTION_MOVE)
                str = "ACTION_MOVE";
            if(event.getAction() == MotionEvent.ACTION_UP)
                str = "ACTION_UP";
            invalidate();
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyView w = new MyView(this);
        setContentView(w);
    }
}
