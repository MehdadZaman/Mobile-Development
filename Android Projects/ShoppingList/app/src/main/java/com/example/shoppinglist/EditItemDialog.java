package com.example.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class EditItemDialog extends AppCompatDialogFragment {

    Spinner categoriesSpinner;

    EditText nameInput;
    EditText priceInput;
    EditText descriptionInput;
    CheckBox purchasedInput;

    Button saveButton;

    private EditItemDialogListener editItemDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_item_dialog, null);

        categoriesSpinner = view.findViewById(R.id.spinner_category);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.Categories, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(spinnerAdapter);

        nameInput = view.findViewById(R.id.name_input);
        priceInput = view.findViewById(R.id.cost_input);
        descriptionInput = view.findViewById(R.id.description_input);
        purchasedInput = view.findViewById(R.id.purchased_input);

        builder.setView(view).setTitle("Shopping List");

        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String price = priceInput.getText().toString();
                String description = descriptionInput.getText().toString();
                boolean purchased = purchasedInput.isChecked();

                int category = categoriesSpinner.getSelectedItemPosition();

                if(TextUtils.isEmpty(name))
                {
                    name = "";
                }

                if(TextUtils.isEmpty(price))
                {
                    price = "";
                }

                if(TextUtils.isEmpty(description))
                {
                    description = "";
                }

                editItemDialogListener.applyTexts2(category, name, description, price, purchased);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            editItemDialogListener = (EditItemDialogListener)context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " implement AddItemDialogListener");
        }
    }

    public interface EditItemDialogListener{
        void applyTexts2(int category, String itemName, String itemDescription, String itemCost, boolean itemPurchased);
    }
}