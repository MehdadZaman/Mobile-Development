/***
 * This class is used as the Alert Dialog for the user to enter
 * their nutritional intakes for a a past day.
 *
 * @author Mehdad Zaman
 * @id 112323211
 * Final Project
 * CSE 390 Section 2
 */
package com.example.nutritionalhelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AddDateValuesDialog extends AppCompatDialogFragment {

    /***
     * Physical layout components on the page
     */
    EditText calorieInput;
    EditText fatInput;
    EditText fiberInput;
    EditText sodiumInput;
    EditText proteinInput;
    Button saveButton;

    /***
     * The listener which will tell the original activity about an update
     */
    private AddDateValueDialogListener addItemDialogListener;

    /***
     * The method that creates the Dialog
     *
     * @param savedInstanceState the current state
     * @return an instance of the Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_date_values_dialog, null);

        calorieInput = view.findViewById(R.id.calorie_input);
        fatInput = view.findViewById(R.id.fat_input);
        fiberInput = view.findViewById(R.id.fiber_input);
        sodiumInput = view.findViewById(R.id.sodium_input);
        proteinInput = view.findViewById(R.id.protein_input);

        builder.setView(view).setTitle("Add Nutritional Values");

        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String calorieString;
                String fatString;
                String fiberString;
                String sodiumString;
                String proteinString;

                calorieString = calorieInput.getText().toString();
                fatString = fatInput.getText().toString();
                fiberString = fiberInput.getText().toString();
                sodiumString = sodiumInput.getText().toString();
                proteinString = proteinInput.getText().toString();

                if(TextUtils.isEmpty(calorieString))
                {
                    Toast.makeText(getContext(), "Calorie Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(fatString))
                {
                    Toast.makeText(getContext(), "Fat Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(fiberString))
                {
                    Toast.makeText(getContext(), "Fiber Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(sodiumString))
                {
                    Toast.makeText(getContext(), "Sodium Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(proteinString))
                {
                    Toast.makeText(getContext(), "Protein Field is Empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int calorieInt = Integer.parseInt(calorieString);
                    int fatInt = Integer.parseInt(fatString);
                    int fiberInt = Integer.parseInt(fiberString);
                    int sodiumInt = Integer.parseInt(sodiumString);
                    int proteinInt = Integer.parseInt(proteinString);

                    addItemDialogListener.applyTexts(calorieInt, fatInt, fiberInt, sodiumInt, proteinInt);
                }
            }
        });

        return builder.create();
    }

    /***
     * Displays the Dialog in the current context
     *
     * @param context The context of the view that is starting this Dialog
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            addItemDialogListener = (AddDateValueDialogListener)context;
        }
        catch(ClassCastException e)
        {}
    }

    /***
     * The interface for classes that are listening for the results from the
     * Dialog
     */
    public interface AddDateValueDialogListener {
        /***
         * Method that sends the information from the Dialog over to the activity
         *
         * @param calorieInt Calorie input value
         * @param fatInt Fat input value
         * @param fiberInt Fiber input value
         * @param sodiumInt Sodium input value
         * @param proteinInt Protein input value
         */
        void applyTexts(int calorieInt, int fatInt, int fiberInt, int sodiumInt, int proteinInt);
    }
}