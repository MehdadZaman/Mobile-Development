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

    EditText calorieInput;
    EditText fatInput;
    EditText fiberInput;
    EditText sodiumInput;
    EditText proteinInput;

    Button saveButton;

    private AddDateValueDialogListener addItemDialogListener;

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

    public interface AddDateValueDialogListener {
        void applyTexts(int calorieInt, int fatInt, int fiberInt, int sodiumInt, int proteinInt);
    }
}