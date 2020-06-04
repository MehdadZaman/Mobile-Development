package com.example.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>/* implements AddItemDialog.AddItemDialogListener*/{

    private ArrayList<ShoppingItem> mshoppingList;

    int currentPosition;

    AddItemDialog editItemDialog;

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder{

        TextView displayName;
        TextView displayPrice;
        CheckBox displayPurchased;
        Button displayEditButton;
        Button displayDetailsButton;
        ImageView categoryImage;
        TextView displayDescription;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);

            displayName = itemView.findViewById(R.id.productName);
            displayPrice = itemView.findViewById(R.id.productPrice);
            displayPurchased = itemView.findViewById(R.id.purchasedItem);
            displayEditButton = itemView.findViewById(R.id.editButton);
            displayDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            displayDescription = itemView.findViewById(R.id.descriptionText);
        }
    }

    public ShoppingListAdapter(ArrayList<ShoppingItem> shoppingList){
        this.mshoppingList= shoppingList;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        ShoppingListViewHolder slvh = new ShoppingListViewHolder(view);
        return slvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingListViewHolder holder, int position) {

        ShoppingItem currentShoppingItem = mshoppingList.get(position);
        currentPosition = position;

        holder.displayName.setText(currentShoppingItem.getName());
        holder.displayPrice.setText("$" + currentShoppingItem.getPrice());
        holder.displayPurchased.setChecked(currentShoppingItem.isPurchased());

        holder.displayDescription.setText(currentShoppingItem.getDescription());

       holder.displayEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItemDialog = new AddItemDialog();

                /*FragmentActivity fa = new FragmentActivity();
                editItemDialog.show(fa.getSupportFragmentManager(), "Edit Shopping List Item");*/

                /*Activity activity = (Activity) v.getContext();

                editItemDialog.show((((FragmentActivity)activity).getSupportFragmentManager()), "Add Shopping List Item");*/
            }
        });

        holder.displayDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.displayDetailsButton.getText().equals("SHOW DETAILS"))
                {
                    holder.displayDetailsButton.setText("HIDE DETAILS");
                    holder.displayDescription.setVisibility(View.VISIBLE);
                }
                else if(holder.displayDetailsButton.getText().equals("HIDE DETAILS"))
                {
                    holder.displayDetailsButton.setText("SHOW DETAILS");
                    holder.displayDescription.setVisibility(View.INVISIBLE);
                }
            }
        });

        if(currentShoppingItem.getCategory() == 0)
        {
            holder.categoryImage.setImageResource(R.drawable.ic_laptop_black_24dp);
        }
        else if(currentShoppingItem.getCategory() == 1)
        {
            holder.categoryImage.setImageResource(R.drawable.ic_local_airport_black_96dp);
        }
        if(currentShoppingItem.getCategory() == 2)
        {
            holder.categoryImage.setImageResource(R.drawable.ic_restaurant_black_96dp);
        }

        holder.displayDescription.setText(currentShoppingItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return mshoppingList.size();
    }

    /*@Override
    public void applyTexts(int category, String itemName, String itemCost, String itemDescription, boolean itemPurchased) {

        editItemDialog.dismiss();

        *//*mshoppingList.get(currentPosition).setCategory(category);
        mshoppingList.get(currentPosition).setName(itemName);
        mshoppingList.get(currentPosition).setPrice(itemCost);
        mshoppingList.get(currentPosition).setDescription(itemDescription);
        mshoppingList.get(currentPosition).setPurchased(itemPurchased);*//*

        *//*notifyDataSetChanged();*//*

        *//*editItemDialog.dismiss();*//*
    }*/
}
