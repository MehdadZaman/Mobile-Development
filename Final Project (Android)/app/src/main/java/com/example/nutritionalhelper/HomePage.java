package com.example.nutritionalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    Button dailyInatakesButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final int REQUEST_CALL = 1;

    private TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        emailView = findViewById(R.id.emailHomePage);
        try {
            emailView.setText(mAuth.getCurrentUser().getEmail().toString());
        }
        catch(Exception e)
        {
            emailView.setText("user not available");
        }

        dailyInatakesButton = findViewById(R.id.dailyIntake);
        checkOverFlow();
    }

    public void setSettingsInfo(View view)
    {
        Intent intent = new Intent(this, SettingsPage.class);
        startActivity(intent);
    }

    public void gotoQRScanner(View view)
    {
        Intent intent = new Intent(this, QRNutritionScanner.class);
        startActivity(intent);
    }

    public void onClickLogout(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
    }

    public void manualPageClick(View v){
        Intent intent = new Intent(this, ManualNutritionAdditionPage.class);
        startActivity(intent);
    }

    public void allNutritionalLogsClick(View v){
        Intent intent = new Intent(this, AllNutritionalLogsPage.class);
        startActivity(intent);
    }

    public void checkOverFlow()
    {
        final DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.SERVER;
        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> dataMap = document.getData();
                    ArrayList<Long> dailyIntakes = (ArrayList<Long>) dataMap.get("Current Daily Intakes");
                    ArrayList<Long> maxIntakes = (ArrayList<Long>) dataMap.get("Max Intakes");
                    compareValues(maxIntakes, dailyIntakes);
                }
            }
        });
    }

    public void compareValues(ArrayList<Long> maxIntakes, ArrayList<Long> dailyIntakes)
    {
        boolean setRed = false;
        for(int i = 0; i < maxIntakes.size(); i++)
        {
            if((maxIntakes.get(i) != 0) && (dailyIntakes.get(i) >= maxIntakes.get(i)))
            {
                setRed = true;
            }
        }

        if(setRed)
        {
            dailyInatakesButton.setBackgroundResource(R.drawable.rounded_corner_button2);
            dailyInatakesButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alert_symbol, 0, R.drawable.alert_symbol, 0);
        }
    }

    public void currentNutritionalIntakeClick(View v){

        Intent intent = new Intent(this, CurrentNutritionalIntakesPage.class);
        startActivity(intent);
    }
}