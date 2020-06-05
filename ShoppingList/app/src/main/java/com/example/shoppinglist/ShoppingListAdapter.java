package com.example.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>{

    private ArrayList<ShoppingItem> mshoppingList;

    private  ShoppingIListDataSource mshoppingIListDataSource;

    private OnNoteListener mOnNoteListener;

    int currentPosition;

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView displayName;
        TextView displayPrice;
        CheckBox displayPurchased;
        Button displayEditButton;
        Button displayDetailsButton;
        Button displayDeleteButton;
        ImageView categoryImage;
        TextView displayDescription;

        OnNoteListener onNoteListener;

        public ShoppingListViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            displayName = itemView.findViewById(R.id.productName);
            displayPrice = itemView.findViewById(R.id.productPrice);
            displayPurchased = itemView.findViewById(R.id.purchasedItem);
            displayEditButton = itemView.findViewById(R.id.editButton);
            displayDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            displayDeleteButton = itemView.findViewById(R.id.deleteItemButton);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            displayDescription = itemView.findViewById(R.id.descriptionText);

            this.onNoteListener = onNoteListener;

            displayEditButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public ShoppingListAdapter(ArrayList<ShoppingItem> shoppingList, OnNoteListener onNoteListener, ShoppingIListDataSource shoppingIListDataSource){
        this.mshoppingList= shoppingList;

        this.mOnNoteListener = onNoteListener;

        this.mshoppingIListDataSource = shoppingIListDataSource;
    }

    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        ShoppingListViewHolder slvh = new ShoppingListViewHolder(view, mOnNoteListener);
        return slvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingListViewHolder holder, final int position) {

        final ShoppingItem currentShoppingItem = mshoppingList.get(position);
        currentPosition = position;

        holder.displayName.setText(currentShoppingItem.getName());
        holder.displayPrice.setText("$" + currentShoppingItem.getPrice());

        if(currentShoppingItem.isPurchased() == 1) {
            holder.displayPurchased.setChecked(true);
        }
        else
        {
            holder.displayPurchased.setChecked(false);
        }

        holder.displayDescription.setText(currentShoppingItem.getDescription());

        holder.displayPurchased.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    currentShoppingItem.setPurchased(1);
                }
                else {
                    currentShoppingItem.setPurchased(0);
                }

                mshoppingIListDataSource.updateShoppingItem(currentShoppingItem);
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

        holder.displayDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mshoppingIListDataSource.deleteShoppingItem(mshoppingList.get(position));

                mshoppingList.remove(position);

                notifyDataSetChanged();
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
}