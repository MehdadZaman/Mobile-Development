/***
 * This class is the adapter for the Recyclerview
 * of all of the past daily intakes
 *
 * @author Mehdad Zaman
 * @id 112323211
 * Final Project
 * CSE 390 Section 2
 */
package com.example.nutritionalhelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DailyNutritionalAdapter extends RecyclerView.Adapter<DailyNutritionalAdapter.DailyNutritionalViewHolder>{

    /***
     * ArrayLists of datasource information
     */
    ArrayList<HistoricalDateValues> historicalDateValues;

    private OnNoteListener mOnNoteListener;

    /***
     * Firebase and Firestore access objects
     */
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    /***
     * Class for each of the individual cells
     */
    public static class DailyNutritionalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /***
         * Display components
         */
        TextView dateDisplay;
        Button displayDetailsButton;
        Button displayDeleteButton;

        OnNoteListener onNoteListener;

        /***
         * Constructor that makes the cell
         *
         * @param itemView the view of the cell
         * @param onNoteListener the onClick listen for viewing daily intakes
         */
        public DailyNutritionalViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            dateDisplay = itemView.findViewById(R.id.dateDisplay);
            displayDetailsButton = itemView.findViewById(R.id.viewDetailsButton);
            displayDeleteButton = itemView.findViewById(R.id.deleteItemButton);
            this.onNoteListener = onNoteListener;

            displayDetailsButton.setOnClickListener(this);
        }

        /***
         * Button click that acquires position
         *
         * @param v button click
         */
        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{

        /***
         * Button click at a position
         *
         * @param position position of the cell
         */
        void onNoteClick(int position);
    }

    /***
     * Constructor for adapter
     *
     * @param historicalDateValues datasource arraylist
     * @param onNoteListener onclick listener for viewing daily intakes
     */
    public DailyNutritionalAdapter(ArrayList<HistoricalDateValues> historicalDateValues, OnNoteListener onNoteListener) {
        this.historicalDateValues = historicalDateValues;
        this.mOnNoteListener = onNoteListener;

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /***
     * Creates the cell
     *
     * @param parent Parent recycler view
     * @param viewType type of view
     * @return return of the cell
     */
    @NonNull
    @Override
    public DailyNutritionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historical_date_item, parent, false);
        DailyNutritionalViewHolder dnvh = new DailyNutritionalViewHolder(view, mOnNoteListener);
        return dnvh;
    }

    /***
     * Method that attaches the subviews to the cell
     *
     * @param holder the cell holder
     * @param position the position of the cell in the recyclerview
     */
    @Override
    public void onBindViewHolder(@NonNull final DailyNutritionalViewHolder holder, final int position) {

        holder.dateDisplay.setText(historicalDateValues.get(position).getDate());

        holder.displayDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historicalDateValues.remove(position);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userID = user.getUid();
                DocumentReference df = db.collection("users").document(userID);

                HashMap<String, ArrayList<Long>> historicalRecordsFireBase = new HashMap<>();
                for(int i = 0; i < historicalDateValues.size(); i++)
                {
                    historicalRecordsFireBase.put(historicalDateValues.get(i).getDate(), historicalDateValues.get(i).getDateValues());
                }

                df.update("HistoricalIntakes", historicalRecordsFireBase);
                notifyDataSetChanged();
            }
        });

    }

    /***
     * returns the number of desired cells
     *
     * @return number of desired cells
     */
    @Override
    public int getItemCount() {
        return historicalDateValues.size();
    }
}