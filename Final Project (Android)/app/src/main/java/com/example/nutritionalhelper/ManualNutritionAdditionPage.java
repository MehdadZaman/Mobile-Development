/***
 * This class is the activity for manual meal nutrition entry
 *
 * @author Mehdad Zaman
 * @id 112323211
 * Final Project
 * CSE 390 Section 2
 */
package com.example.nutritionalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Map;

public class ManualNutritionAdditionPage extends AppCompatActivity {

    /***
     * View components
     */
    private EditText calorieText;
    private EditText fatText;
    private EditText fiberText;
    private EditText sodiumText;
    private EditText proteinText;

    /***
     * numerical intake values
     */
    private int calorie;
    private int fat;
    private int fiber;
    private int sodium;
    private int protein;

    /***
     * Firebase and Firestore access objects
     */
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    /***
     * nutritional intake values
     */
    int[] nutritionIntegers;

    /***
     * Starts the activity on the screen
     * @param savedInstanceState The previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_nutrition_addition_page);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        calorieText = findViewById(R.id.intakeCalories);
        fatText = findViewById(R.id.intakeFat);
        fiberText = findViewById(R.id.intakeFiber);
        sodiumText = findViewById(R.id.intakeSodium);
        proteinText = findViewById(R.id.intakeProtein);
    }

    /***
     * Adds a meals nutrition
     * @param v button click
     */
    public void addMealClick(View v)
    {
        try {calorie = Integer.parseInt(calorieText.getText().toString());} catch (Exception e){e.printStackTrace();}
        try {fat = Integer.parseInt(fatText.getText().toString());} catch (Exception e){e.printStackTrace();}
        try {fiber = Integer.parseInt(fiberText.getText().toString());} catch (Exception e){e.printStackTrace();}
        try {sodium = Integer.parseInt(sodiumText.getText().toString());} catch (Exception e){e.printStackTrace();}
        try {protein = Integer.parseInt(proteinText.getText().toString());} catch (Exception e){e.printStackTrace();}
        processIngredients();
    }

    /***
     * Puts nutritional intake on firebase
     */
    public void processIngredients()
    {
        //Weird cases below
        //String[] ingredientList = ingredients.split("[^a-zA-Z \n]");
        nutritionIntegers = new int[5];
        nutritionIntegers[0] = calorie;
        nutritionIntegers[1] = fat;
        nutritionIntegers[2] = fiber;
        nutritionIntegers[3] = sodium;
        nutritionIntegers[4] = protein;

        FirebaseUser user = firebaseAuth.getCurrentUser();
        final DocumentReference docRef = db.collection("users").document(user.getUid());
        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.SERVER;
        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> dataMap = document.getData();
                    ArrayList<Long> numbers = (ArrayList<Long>)document.get("Current Daily Intakes");
                    ArrayList<Long> maxNums = (ArrayList<Long>)document.get("Max Intakes");
                    processIngredients2(numbers, maxNums);
                }
            }
        });
    }

    /***
     * Calculates inatke values
     * @param nutNums Maximum daily intakes
     * @param maxNums Current daily intakes
     */
    public void processIngredients2(ArrayList<Long> nutNums,ArrayList<Long> maxNums)
    {
        for(int i = 0; i < nutritionIntegers.length; i++)
        {
            nutNums.set(i, (nutNums.get(i) + (int)((long)nutritionIntegers[i])));
        }

        DocumentReference df = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        df.update("Current Daily Intakes", nutNums);
        showDialogueNutritionAdded();

        PopNotificationWarning(nutNums, maxNums);
    }

    /***
     * Shows Alert dialog that item has been added
     */
    public void showDialogueNutritionAdded()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManualNutritionAdditionPage.this);
        builder.setMessage("Nutritional Information Added")
                .setPositiveButton("Add Another Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ManualNutritionAdditionPage.this, ManualNutritionAdditionPage.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Return Home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ManualNutritionAdditionPage.this, HomePage.class);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    /***
     * Shows notification if maximum daily limit has been reached
     *
     * @param nutNums current nutritional numbers
     * @param maxIntakes maximum daily intakes
     */
    public void PopNotificationWarning(ArrayList<Long> nutNums, ArrayList<Long> maxIntakes){
        String s="";
        boolean flag= false;
        for(int i =0; i< nutNums.size(); i++){
            if(nutNums.get(i)>maxIntakes.get(i)){
                s ="You have exceeded one of your nutrition's current maximum limit.";
                flag = true;
                break;
            }
        }

        if(flag == true){
            NotificationCompat.Builder builder;

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                String CHANNEL_ID = "my_channel_01";
                CharSequence name = getString(R.string.common_google_play_services_notification_channel_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,name,importance);
                manager.createNotificationChannel(mChannel);
                builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            }
            else{
                builder = new NotificationCompat.Builder(this, "my_channel_01");
            }
            builder.setSmallIcon(R.drawable.alert_symbol)
                    .setContentTitle("Nutrition Helper")
                    .setContentText(s)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(s));
            Intent notificationIntent = new Intent(this, CurrentNutritionalIntakesPage.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            manager.notify(0, builder.build());
        }
    }
}