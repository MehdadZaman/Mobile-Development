package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup sortCategory;
    RadioGroup sortOrder;

    Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sortCategory = findViewById(R.id.sorting_category);
        sortOrder = findViewById(R.id.sort_order);

        saveChanges = findViewById(R.id.save_changes_button);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioSortedCategoryID = sortCategory.getCheckedRadioButtonId();

                int radioSortedOrderID = sortOrder.getCheckedRadioButtonId();

                Intent intent = new Intent(SettingsActivity.this, ScrollingActivity.class);

                intent.putExtra("CATEGORY", radioSortedCategoryID);
                intent.putExtra("ORDER", radioSortedOrderID);

                startActivity(intent);
            }
        });
    }
}
