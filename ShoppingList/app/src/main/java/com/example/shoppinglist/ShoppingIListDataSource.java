package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public boolean insertContact(ShoppingItem si) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("category", si.getCategory());
            initialValues.put("name", si.getDescription());
            initialValues.put("description", si.getName());
            initialValues.put("estimatedPrice", si.getPrice());
            initialValues.put("purchasedStatus",si.isPurchased());

            didSucceed = database.insert("shoppingItem", null, initialValues) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateContact(ShoppingItem si) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) si.getShoppingListID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("category", si.getCategory());
            updateValues.put("name", si.getDescription());
            updateValues.put("description", si.getName());
            updateValues.put("estimatedPrice", si.getPrice());
            updateValues.put("purchasedStatus",si.isPurchased());

            didSucceed = database.update("ShoppingList", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastContactId() {
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
}
