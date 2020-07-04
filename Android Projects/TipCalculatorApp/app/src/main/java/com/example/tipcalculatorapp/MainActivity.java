package com.example.tipcalculatorapp;

/* Name: Mehdad Zaman
    SBU ID: 112323211*/

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText userInput;
    TextView billAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = findViewById(R.id.editTextBill);

        billAmount = findViewById(R.id.priceTextView);
    }

    public void fifteenPercent(View view)
    {
        String inputString = userInput.getText().toString();

        if(TextUtils.isEmpty(inputString))
        {
            billAmount.setText("No bill amount inputted");
            return;
        }

        Double price = Double.parseDouble(inputString);

        billAmount.setText(String.format("Tip: %.2f Total Bill: %.2f", (price * .15), (price * 1.15)));

    }

    public void eighteenPercent(View view)
    {
        String inputString = userInput.getText().toString();

        if(TextUtils.isEmpty(inputString))
        {
            billAmount.setText("No bill amount inputted");
            return;
        }

        Double price = Double.parseDouble(inputString);

        billAmount.setText(String.format("Tip: %.2f Total Bill: %.2f", (price * .18), (price * 1.18)));
    }
}