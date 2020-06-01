package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShoppingItem {
    int category;
    String name;
    String description;
    double price;
    boolean purchased;

    LinearLayout mainLinearLayout;
    LinearLayout rl;
    TextView displayName;
    TextView displayPrice;
    CheckBox displayPurchased;
    Button displayEditButton;
    Button displayDetailsButton;
    ImageView categoryImage;
    TextView displayDescription;

    public ShoppingItem(Context context, int selectedCategory, String selectedName, String selectedDescription, double selectedPrice, boolean selectedPurchased)
    {

        this.category = selectedCategory;
        this.name = selectedName;
        this.description = selectedDescription;
        this.price = selectedPrice;
        this.purchased = selectedPurchased;

        rl = new LinearLayout(context);
        ViewGroup.LayoutParams rlParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayout pictureLinearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams pictureLinearLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        pictureLinearLayout.setOrientation(LinearLayout.VERTICAL);

        categoryImage = new ImageView(context);
        if(category == 1)
        {
            categoryImage.setImageResource(R.drawable.ic_laptop_black_24dp);
        }
        else if(category == 2)
        {
            categoryImage.setImageResource(R.drawable.ic_local_airport_black_96dp);
        }
        else
        {
            categoryImage.setImageResource(R.drawable.ic_restaurant_black_96dp);
        }
        RelativeLayout.LayoutParams categoryImageParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        categoryImageParams.setMargins(20, 4,5,4);

        pictureLinearLayout.addView(categoryImage, categoryImageParams);

        rl.addView(pictureLinearLayout, pictureLinearLayoutParams);


        LinearLayout informationLinearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams informationLinearLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        informationLinearLayoutParams.setMargins(1,0,45, 0);
        informationLinearLayout.setOrientation(LinearLayout.VERTICAL);

        displayName = new TextView(context);
        displayName.setText(name);
        displayName.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams displayNameParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        displayNameParams.setMargins(25, 4,5,4);

        displayPrice = new TextView(context);
        displayPrice.setText("$" + price);
        RelativeLayout.LayoutParams displayPriceParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        displayPriceParams.setMargins(25, 4,5,4);

        displayPurchased = new CheckBox(context);
        displayPurchased.setText("Purchased");
        RelativeLayout.LayoutParams displayPurchasedParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        displayPurchasedParams.setMargins(1, 4,5,4);

        informationLinearLayout.addView(displayName, displayNameParams);

        informationLinearLayout.addView(displayPrice, displayPriceParams);

        informationLinearLayout.addView(displayPurchased, displayPurchasedParams);

        rl.addView(informationLinearLayout, informationLinearLayoutParams);


        LinearLayout buttonLinearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams buttonLinearLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        buttonLinearLayout.setOrientation(LinearLayout.VERTICAL);

        displayEditButton = new Button(context);
        displayEditButton.setText("Edit");
        RelativeLayout.LayoutParams displayEditButtonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        displayEditButtonParams.setMargins(1, 4,5,4);

        displayDetailsButton = new Button(context);
        displayDetailsButton.setText("Show Details");
        RelativeLayout.LayoutParams displayDetailsButtonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        displayDetailsButtonParams.setMargins(1, 4,5,4);

        buttonLinearLayout.addView(displayEditButton, displayEditButtonParams);

        buttonLinearLayout.addView(displayDetailsButton, displayDetailsButtonParams);

        rl.addView(buttonLinearLayout, buttonLinearLayoutParams);

        displayDescription = new TextView(context);
        displayDescription.setText(description);
        RelativeLayout.LayoutParams displayDescriptionParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        mainLinearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams mainLinearLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        mainLinearLayoutParams.setMargins(5,5,5,5);

        mainLinearLayout.setLayoutParams(mainLinearLayoutParams);
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);

        mainLinearLayout.addView(rl, rlParams);
        mainLinearLayout.addView(displayDescription, displayDescriptionParams);
        mainLinearLayout.setBackgroundColor(Color.WHITE);
    }
}