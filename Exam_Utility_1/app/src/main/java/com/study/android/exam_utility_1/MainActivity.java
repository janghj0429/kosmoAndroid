package com.study.android.exam_utility_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    boolean fNumComplete = false;
    boolean sNumComplete = false;
    boolean dot = false;

    TextView textView1;
    TextView textView2;
    String fNum = "";
    String calword = "";
    String sNum = "";
    String result ="";
    String sentence = "";
    //int or String result = 0; 만들어서 나중에 = 누르면 백에서 계산해서 뷰에 추가
    Button[] numButtons = new Button[10];
    Integer[] numBtnIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

    Button btnDot;
    Button btnEqual;
    Button btnDel;
    Button btnDiv;
    Button btnMul;
    Button btnMin;
    Button btnSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);



        for(int i=0; i<numBtnIds.length; i++){
            numButtons[i] = (Button)findViewById(numBtnIds[i]);
        }

        for(int i=0;i<numBtnIds.length;i++){
            final int index;
            index = i;

            numButtons[index].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(fNumComplete == false){
                        if(fNum.equals("0")){
                            fNum = String.valueOf(index);
                        }else{
                            fNum = fNum + String.valueOf(index);
                        }
                    }else{
                        if(sNum.equals("0")){
                            sNum = String.valueOf(index);
                        }else{
                            sNum = sNum + String.valueOf(index);
                        }
                    }

                    textView1.setText(sentenceView());
                }
            });

        }
    }

    public String sentenceView(){
        sentence = fNum + calword + sNum;
        return sentence;
    }


//    public void onBtnDotClicked(View v){
//        if(dot == 1){
//            Toast.makeText(getApplicationContext(), calword+"를 중복사용할 수 없습니다.",
//                    Toast.LENGTH_LONG).show();
//        }else if()
//        else{
//            btnDot = findViewById(R.id.buttonDot);
//            calword = btnDot.getText().toString();
//            Log.d(TAG,"문자 : " + calword);
//            textView1.setText(fNum + btnDot.getText().toString());
//        }
//    }
    public void onBtnDelClicked(View v){
        Log.d(TAG, "fN : " + fNumComplete + "  sN : " + sNumComplete);
        if(fNumComplete == true){
            if(sNum.length() == 0){
                return;
            }else{
                String str = sNum;
                str = str.substring(0,str.length()-1);
                sNum = str;
                textView1.setText(sentenceView());
            }
        }else{
            if(fNum.length() == 0){
                return;
            }else{
                String str = textView1.getText().toString();
                str = str.substring(0,str.length()-1);
                fNum = str;
                textView1.setText(sentenceView());
            }
        }
    }

    public void onBtnDivClicked(View v){
        if(fNumComplete == false){
            calword = "/";
            fNumComplete = true;
        }else{
            return;
        }
        textView1.setText(sentenceView());
    }

    public void onBtnMulClicked(View v){
        if(fNumComplete == false){
            calword = "*";
            fNumComplete = true;
        }else{
            return;
        }
        textView1.setText(sentenceView());
    }
    public void onBtnMinClicked(View v){
        if(fNumComplete == false){
            calword = "-";
            fNumComplete = true;
        }else{
            return;
        }
        textView1.setText(sentenceView());
    }
    public void onBtnSumClicked(View v){
        if(fNumComplete == false){
            calword = "+";
            fNumComplete = true;
        }else{
            return;
        }
        textView1.setText(sentenceView());
    }

    public void onBtnEqualClicked(View v){
        if(fNum.equals("")||sNum.equals("")||calword.equals("")){return;}
        if(fNum.substring(fNum.length()-1).equals(".")){
            fNum = fNum.substring(0,fNum.length()-1);
        }
        if(sNum.substring(sNum.length()-1).equals(".")){
            sNum = sNum.substring(0,sNum.length()-1);
        }

        if(calword.equals("+")){
            result = String.valueOf( Integer.parseInt(fNum) + Integer.parseInt(sNum) );
        }else if(calword.equals("-")){
            result = String.valueOf( Integer.parseInt(fNum) - Integer.parseInt(sNum) );
        }else if(calword.equals("*")){
            result = String.valueOf( Integer.parseInt(fNum) * Integer.parseInt(sNum) );
        }else if(calword.equals("/")){
            if(sNum.equals("0")){
                return;
            }
            result = String.valueOf( Integer.parseInt(fNum) / Integer.parseInt(sNum) );
        }else{
            return;
        }
        fNum = result;
        fNumComplete = false;
        sNum = "";
        sNumComplete = false;
        calword = "";
        textView1.setText(sentenceView());
        textView2.setText(result);
    }


}
