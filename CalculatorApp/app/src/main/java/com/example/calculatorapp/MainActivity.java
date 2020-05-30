package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText firstNumText;
    EditText secondNumText;

    TextView answerText;

    double firstNumberValue;
    double secondNumberValue;

    double answerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumText = findViewById(R.id.firstNum);
        secondNumText = findViewById(R.id.secondNum);

        answerText = findViewById(R.id.answer_text);
    }

    public void add(View v)
    {
        try
        {
            if(TextUtils.isEmpty(firstNumText.getText().toString()) || TextUtils.isEmpty(secondNumText.getText().toString()))
            {
                answerText.setText("A number has not been inputted.");
                return;
            }

            firstNumberValue = Double.parseDouble(firstNumText.getText().toString());
            secondNumberValue = Double.parseDouble(secondNumText.getText().toString());

            answerValue = firstNumberValue + secondNumberValue;

            answerText.setText(String.format("Answer: %.2f", answerValue));
        }
        catch(Exception e){}
    }

    public void subtract(View v)
    {
        try
        {
            if(TextUtils.isEmpty(firstNumText.getText().toString()) || TextUtils.isEmpty(secondNumText.getText().toString()))
            {
                answerText.setText("A number has not been inputted.");
                return;
            }

            firstNumberValue = Double.parseDouble(firstNumText.getText().toString());
            secondNumberValue = Double.parseDouble(secondNumText.getText().toString());

            answerValue = firstNumberValue - secondNumberValue;

            answerText.setText(String.format("Answer: %.2f", answerValue));
        }
        catch(Exception e){}
    }

    public void multiply(View v)
    {
        try
        {
            if(TextUtils.isEmpty(firstNumText.getText().toString()) || TextUtils.isEmpty(secondNumText.getText().toString()))
            {
                answerText.setText("A number has not been inputted.");
                return;
            }

            firstNumberValue = Double.parseDouble(firstNumText.getText().toString());
            secondNumberValue = Double.parseDouble(secondNumText.getText().toString());

            answerValue = firstNumberValue * secondNumberValue;

            answerText.setText(String.format("Answer: %.2f", answerValue));
        }
        catch(Exception e){}
    }

    public void divide(View v)
    {
        try
        {
            if(TextUtils.isEmpty(firstNumText.getText().toString()) || TextUtils.isEmpty(secondNumText.getText().toString()))
            {
                answerText.setText("A number has not been inputted.");
                return;
            }

            firstNumberValue = Double.parseDouble(firstNumText.getText().toString());
            secondNumberValue = Double.parseDouble(secondNumText.getText().toString());

            answerValue = firstNumberValue / secondNumberValue;

            answerText.setText(String.format("Answer: %.2f", answerValue));
        }
        catch(Exception e){}
    }
}
