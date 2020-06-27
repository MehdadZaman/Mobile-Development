package com.example.nutritionalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AllNutritionalLogsPage extends AppCompatActivity implements DailyNutritionalAdapter.OnNoteListener, AddDateValuesDialog.AddDateValueDialogListener {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    ArrayList<String> dates;
    ArrayList<ArrayList<Long>> dateValues;

    ArrayList<HistoricalDateValues> historicalDateValues;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    AddDateValuesDialog addDateValuesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_nutritional_logs_page);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        historicalDateValues = new ArrayList<>();

        setHistoricalArrayList();
        setupRecycler();
    }

    public void setupRecycler() {
        recyclerView = findViewById(R.id.dateList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        adapter = new DailyNutritionalAdapter(historicalDateValues,  this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void setHistoricalArrayList()
    {
        final DocumentReference docRef = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        // Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.SERVER;
        // Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> dataMap = document.getData();

                    HashMap<String, ArrayList<Long>> historicalIntakes = (HashMap<String, ArrayList<Long>>) dataMap.get("HistoricalIntakes");

                    dates = new ArrayList<>(historicalIntakes.keySet());
                    dateValues = new ArrayList<>(historicalIntakes.values());

                    for (int i = 0; i < dates.size(); i++) {
                        historicalDateValues.add(new HistoricalDateValues(dates.get(i), dateValues.get(i)));
                    }

                    Collections.sort(historicalDateValues, new HistoricalDateComparator());
                    Collections.reverse(historicalDateValues);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, DailyLogPage.class);
        ArrayList<Long> currentDateValues = historicalDateValues.get(position).getDateValues();
        intent.putExtra("Nutritional Numbers", new double[]{currentDateValues.get(0), currentDateValues.get(1),
                                                                    currentDateValues.get(2), currentDateValues.get(3),
                                                                    currentDateValues.get(4)});
        startActivity(intent);
    }

    public void backHomeLogs(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    String currentDateString = "";

    public void addItemLogs(View view) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                currentDateString = month + "/" + day + "/" + year;
                addDateValuesDialog = new AddDateValuesDialog();
                addDateValuesDialog.show(getSupportFragmentManager(), "Add Shopping List Item");
            }
        }, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void applyTexts(int calorieInt, int fatInt, int fiberInt, int sodiumInt, int proteinInt) {

        for(int i = 0; i < historicalDateValues.size(); i++)
        {
            if(historicalDateValues.get(i).getDate().equals(currentDateString))
            {
                historicalDateValues.remove(i);
            }
        }

        ArrayList<Long> historicIntakes = new ArrayList<>();
        historicIntakes.add((long)calorieInt);
        historicIntakes.add((long)fatInt);
        historicIntakes.add((long)fiberInt);
        historicIntakes.add((long)sodiumInt);
        historicIntakes.add((long)proteinInt);

        historicalDateValues.add(new HistoricalDateValues(currentDateString, historicIntakes));

        Collections.sort(historicalDateValues, new HistoricalDateComparator());
        Collections.reverse(historicalDateValues);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getUid();
        DocumentReference df = db.collection("users").document(userID);

        HashMap<String, ArrayList<Long>> historicalRecordsFireBase = new HashMap<>();
        for(int i = 0; i < historicalDateValues.size(); i++)
        {
            historicalRecordsFireBase.put(historicalDateValues.get(i).getDate(), historicalDateValues.get(i).getDateValues());
        }

        df.update("HistoricalIntakes", historicalRecordsFireBase);
        adapter.notifyDataSetChanged();

        addDateValuesDialog.dismiss();
    }

    static class HistoricalDateComparator implements Comparator<HistoricalDateValues> {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        public int compare(HistoricalDateValues t1, HistoricalDateValues t2) {
            try {
                return dateFormat.parse(t1.getDate()).compareTo(dateFormat.parse(t2.getDate()));
            }
            catch (Exception e) {
                return 0;
            }
        }
    }
}