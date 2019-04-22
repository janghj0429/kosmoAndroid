package com.study.android.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private static float num1;
    private static float num2;
    private static float result = 0;

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

        calResult = findViewById(R.id.Result);

        etNum1 = findViewById(R.id.tNum1);
        etNum2 = findViewById(R.id.tNum2);

    }

    public void BtnSumClicked(View v){
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());
        result = num1 + num2;
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnMinClicked(View v){
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());
        result = num1 - num2;
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnMulClicked(View v){
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());
        result = num1 * num2;
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnDivClicked(View v){
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());
        result = num1 / num2;
        calResult.setText("계산 결과 : " + result);
    }

    public void BtnModClicked(View v){
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());
        result = num1 % num2;
        calResult.setText("계산 결과 : " + result);
    }
}
