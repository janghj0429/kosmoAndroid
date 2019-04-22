package com.study.android.ex05_edittext1;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private static String num1="";
    private static String num2="";
    private static long result = 0;

    TextView textView1;

    EditText etId;
    EditText etPwd;
    EditText etYear;
    EditText etMonth;
    EditText etDay;

    String sId;
    String sPwd;

    //////////////////////////////////////////////////////////////////

    EditText etNum1;
    EditText etNum2;

    TextView calResult;

    Button btnSum;
    Button btnMin;
    Button btnMul;
    Button btnDiv;
    Button btnMod;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        etYear = findViewById(R.id.etYear);
        etMonth = findViewById(R.id.etMonth);
        etDay = findViewById(R.id.etDay);

        //패스워드 입력시 글자수 체크
        etPwd.addTextChangedListener(watcher);

        ///////////////////////////////////////////////////////////

        calResult = findViewById(R.id.calResult);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);

        btnSum = findViewById(R.id.btnSum);
        btnMin = findViewById(R.id.btnMin);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        btnMod = findViewById(R.id.btnMod);

    }

    public void BtnSumClicked(View v){
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        result = (long)Integer.parseInt(num1) + (long)Integer.parseInt(num2);
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnMinClicked(View v){
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        result = (long)Integer.parseInt(num1) - (long)Integer.parseInt(num2);
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnMulClicked(View v){
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        result = (long)Integer.parseInt(num1) * (long)Integer.parseInt(num2);
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnDivClicked(View v){
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        result = (long)Integer.parseInt(num1) / (long)Integer.parseInt(num2);
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnModClicked(View v){
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        result = (long)Integer.parseInt(num1) % (long)Integer.parseInt(num2);
        calResult.setText("계산 결과 : " + result);
    }



    //inputType 과 maxLength가 EditText에 지정되어 있으면 키보드에 [리턴]이 [다음]으로 됨
    //맨 마지막 입력칸은 자동으로 [완료]가 됨
    //입력칸이 키보드에 가려지면 자동으로 올라감

    //키보드 내리기 버튼
    public void onKeydownClicked(View v){
        //IME Hide
        InputMethodManager mgr =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //로그인버튼
    public void onLoginClicked(View v){
        sId = etId.getText().toString();
        sPwd = etPwd.getText().toString();

        if(sId.length() < 3){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("알림")
                    .setMessage("아이디를 입력해 주세요")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("닫기", null)
                    .show();
            etId.requestFocus();
            return;
        }else if(sPwd.length() < 5){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("알림")
                    .setMessage("비밀번호를 정확히 입력해 주세요")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("닫기", null)
                    .show();
            etPwd.requestFocus();
            return;
        }
    }

    TextWatcher watcher = new TextWatcher() {
        String beforeText;

        public void onTextChanged(CharSequence str, int start, int before, int count) {
            byte[] bytes = null;
            try{
                //bytes = str.toString().getBytes("KSC5601");
                bytes = str.toString().getBytes("8859_1");
                int strCount = bytes.length;
                textView1.setText(strCount + " / 8 바이트");;
            }catch (UnsupportedEncodingException ex){
                ex.printStackTrace();
            }
        }


        public void beforeTextChanged(CharSequence str, int start, int count, int after) {
            beforeText = str.toString();
            Log.d(TAG, "beforeTextChanged : " + beforeText);
        }

        public void afterTextChanged(Editable strEditable) {
            String str = strEditable.toString();
            Log.d(TAG, "afterTextChanged : " + str);
            try{
                byte[] strBytes = str.getBytes("8859_1");
                if (strBytes.length > 8) {
                    etPwd.setText(beforeText);
                    etPwd.setSelection(beforeText.length()-1, beforeText.length()-1);
                    //strEditable.delete(strEditable.length()-2,strEditable.length()-1);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    };
}
