package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ShoppingIListDataSource {

    private SQLiteDatabase database;
    private ShoppingListDBHelper dbHelper;

    public ShoppingIListDataSource(Context context) {
        dbHelper = new ShoppingListDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertShoppingItem(ShoppingItem si) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("category", si.getCategory());
            initialValues.put("name", si.getName());
            initialValues.put("description", si.getDescription());
            initialValues.put("estimatedPrice", si.getPrice());
            initialValues.put("purchasedStatus", si.isPurchased());

            didSucceed = database.insert("ShoppingList", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateShoppingItem(ShoppingItem si) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) si.getShoppingListID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("category", si.getCategory());
            updateValues.put("name", si.getName());
            updateValues.put("description", si.getDescription());
            updateValues.put("estimatedPrice", si.getPrice());
            updateValues.put("purchasedStatus", si.isPurchased());

            didSucceed = database.update("ShoppingList", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean deleteShoppingItem(ShoppingItem si){
        boolean didSucceed = false;
        try{
            didSucceed = database.delete("ShoppingList", "_id=" + si.getShoppingListID(), null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastShoppingItemId() {
        int lastId;
        try {
            String query = "Select MAX(_id) from contact";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public Cursor getData(){
        String query = "SELECT * FROM " + "shoppingList";
        Cursor data = database.rawQuery(query, null);
        return data;
    }
}
