package com.example.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ScrollingActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener, ShoppingListAdapter.OnNoteListener, EditItemDialog.EditItemDialogListener {

   ArrayList<ShoppingItem> shoppingList;

   private RecyclerView recyclerView;
   private RecyclerView.Adapter adapter;
   private RecyclerView.LayoutManager layoutManager;

    AddItemDialog addItemDialog;

    ShoppingIListDataSource shoppingListDataSource;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = new ArrayList<>();

        shoppingListDataSource = new ShoppingIListDataSource(this);

        shoppingListDataSource.open();
        populateShoppingList();
        shoppingListDataSource.close();

        int sortCategory = getIntent().getIntExtra("CATEGORY", -1);
        int sortOrder = getIntent().getIntExtra("ORDER", -1);

        if(sortCategory == R.id.name_sort_category)
        {
            shoppingList.sort(new NameComparator());
        }
        else if(sortCategory == R.id.price_sort_category)
        {
            shoppingList.sort(new PriceComparator());
        }
        else if(sortCategory == R.id.purchase_status_sort_category)
        {
            shoppingList.sort(new PurchasedStatusComparator());
        }

        if(sortOrder == R.id.sort_descending)
        {
            Collections.reverse(shoppingList);
        }

        recyclerView = findViewById(R.id.shoppingList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ShoppingListAdapter(shoppingList, this, shoppingListDataSource);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemDialog = new AddItemDialog();
                addItemDialog.show(getSupportFragmentManager(), "Add Shopping List Item");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(ScrollingActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateShoppingList()
    {
        Cursor data = shoppingListDataSource.getData();
        while(data.moveToNext()){
            ShoppingItem tempShoppingItem = new ShoppingItem(data.getInt(1), data.getString(2), data.getString(3), data.getString(4), data.getInt(5));
            tempShoppingItem.setShoppingListID(data.getInt(0));
            shoppingList.add(tempShoppingItem);
        }
    }

    @Override
    public void applyTexts(int category, String itemName, String itemDescription, String itemCost, boolean itemPurchased) {
        addItemDialog.dismiss();

        ShoppingItem currentShoppingItem;

        if(itemPurchased == true) {
            currentShoppingItem = new ShoppingItem(category, itemName, itemDescription, itemCost, 1);
        }
        else {
            currentShoppingItem = new ShoppingItem(category, itemName, itemDescription, itemCost, 0);
        }

        shoppingList.add(currentShoppingItem);

        shoppingListDataSource.open();
        shoppingListDataSource.insertShoppingItem(currentShoppingItem);

        currentShoppingItem.setShoppingListID(shoppingListDataSource.getLastShoppingItemId());
        shoppingListDataSource.close();

        adapter.notifyDataSetChanged();
    }

    EditItemDialog editItemDialog;
    int currentEditPosition;

    @Override
    public void onNoteClick(int position) {
        editItemDialog = new EditItemDialog();
        editItemDialog.show(getSupportFragmentManager(), "Edit Shopping List Item");
        currentEditPosition = position;
    }

    @Override
    public void applyTexts2(int category, String itemName, String itemDescription, String itemCost, boolean itemPurchased) {
        editItemDialog.dismiss();

        shoppingList.get(currentEditPosition).setCategory(category);

        if(!TextUtils.isEmpty(itemName))
        {
            shoppingList.get(currentEditPosition).setName(itemName);
        }

        if(!TextUtils.isEmpty(itemDescription))
        {
            shoppingList.get(currentEditPosition).setDescription(itemDescription);
        }

        if(!TextUtils.isEmpty(itemCost))
        {
            shoppingList.get(currentEditPosition).setPrice(itemCost);
        }

        if(itemPurchased == true) {
            shoppingList.get(currentEditPosition).setPurchased(1);
        }
        else {
            shoppingList.get(currentEditPosition).setPurchased(0);
        }

        shoppingListDataSource.open();
        shoppingListDataSource.updateShoppingItem(shoppingList.get(currentEditPosition));
        shoppingListDataSource.close();

        adapter.notifyDataSetChanged();
    }

    static class NameComparator implements Comparator<ShoppingItem> {
        @Override
        public int compare(ShoppingItem o1, ShoppingItem o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    static class PriceComparator implements Comparator<ShoppingItem> {

        @Override
        public int compare(ShoppingItem o1, ShoppingItem o2) {
            if(Double.parseDouble(o1.getPrice()) >  Double.parseDouble(o2.getPrice()))
            {
                return 1;
            }
            else if(Double.parseDouble(o1.getPrice()) <  Double.parseDouble(o2.getPrice()))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }

    static class PurchasedStatusComparator implements Comparator<ShoppingItem> {

        @Override
        public int compare(ShoppingItem o1, ShoppingItem o2) {
            return (o1.isPurchased() -  o2.isPurchased());
        }
    }
}