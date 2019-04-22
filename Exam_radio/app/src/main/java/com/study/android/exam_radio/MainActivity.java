package com.study.android.exam_radio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    MediaPlayer mp = null;
    int playbackPosition = 0;

    TextView textView1;
    EditText inputMessage;
    TextView textView2;
    int i;
    private ImageButton[] numButtons = new ImageButton[10];
    private Integer[] numBtnIDs = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    String sNum = "0000";
    String playNum = "a0001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        for(i=0;i<numBtnIDs.length;i++){
            numButtons[i] = (ImageButton)findViewById(numBtnIDs[i]);

        }
//        for(i=0;i<numBtnIDs.length;i++){
//            final int index;
//            index=i;
//        }
        for(i=0;i<numBtnIDs.length;i++){
            final int index;
            index = i;

            numButtons[index].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    sNum = textView1.getText().toString() + String.valueOf(index);
                    if(sNum.length()>4){
                        sNum = String.valueOf(index);
                    }
                    textView1.setText(sNum);
                }
            });

        }


        inputMessage = findViewById(R.id.etMessage);

        //inputMessage.setInputType(InputType.TYPE_CLASS_NUMBER); //숫자만입력
        inputMessage.addTextChangedListener(watcher);
    }



    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence str, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void onBtnPlayClicked(View v){
        mp = MediaPlayer.create(this, playNum);
        mp.seekTo(0);
        mp.start();
    }
}
