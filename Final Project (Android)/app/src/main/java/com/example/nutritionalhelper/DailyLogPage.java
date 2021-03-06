/***
 * This class is the barchart for the past daily intakes
 *
 * @author Mehdad Zaman
 * @id 112323211
 * Final Project
 * CSE 390 Section 2
 */
package com.example.nutritionalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DailyLogPage extends AppCompatActivity {

    /***
     * Firebase and Firestore access objects
     */
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    TextView dayTitle;

    /***
     * Intake values
     */
    private double caloriePercentage;
    private double fatPercentage;
    private double fiberPercentage;
    private double sodiumPercentage;
    private double proteinPercentage;
    private BarChart barChart;

    /***
     * Starts the activity on the screen
     * @param savedInstanceState The previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log_page);

        dayTitle = findViewById(R.id.barChartTitle2);
        String dateTitleString = getIntent().getStringExtra("dateString");
        if(dateTitleString != null)
        {
            dayTitle.setText(dateTitleString);
        }

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        setIntakePercentages();
    }

    /***
     * Acquires max intakes from firebase
     */
    public void setIntakePercentages()
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

                    double[] nutritionalPercentages = getIntent().getDoubleArrayExtra("Nutritional Numbers");
                    ArrayList<Long> dailyIntakes = new ArrayList<>();
                    ArrayList<Long> maxIntakes = (ArrayList<Long>) dataMap.get("Max Intakes");
                    for(int i = 0; i < maxIntakes.size(); i++)
                    {
                        dailyIntakes.add(i, (long)(nutritionalPercentages[i]));
                    }
                    setIntakePercentages2(maxIntakes, dailyIntakes);
                }
            }
        });
    }

    /***
     * Calculates percentage values
     * @param maxIntakes Maximum daily intakes
     * @param dailyIntakes Current daily intakes
     */
    public void setIntakePercentages2(ArrayList<Long> maxIntakes, ArrayList<Long> dailyIntakes)
    {
        if(maxIntakes.get(0) !=  0)
        {
            caloriePercentage = (((double)dailyIntakes.get(0))/(double)maxIntakes.get(0))*100.0;
        }
        if(maxIntakes.get(1) !=  0)
        {
            fatPercentage = (((double)dailyIntakes.get(1))/(double)maxIntakes.get(1))*100.0;
        }
        if(maxIntakes.get(2) !=  0)
        {
            fiberPercentage = (((double)dailyIntakes.get(2))/(double)maxIntakes.get(2))*100.0;
        }
        if(maxIntakes.get(3) !=  0)
        {
            sodiumPercentage = (((double)dailyIntakes.get(3))/(double)maxIntakes.get(3))*100.0;
        }
        if(maxIntakes.get(4) !=  0)
        {
            proteinPercentage = (((double)dailyIntakes.get(4))/(double)maxIntakes.get(4))*100.0;
        }

        createBarGraph( caloriePercentage, fatPercentage, fiberPercentage, sodiumPercentage, proteinPercentage);
        showWarningMessage(dailyIntakes, maxIntakes);
    }

    /***
     * Creates the physical bargraph
     *
     * @param calorie calorie percentage
     * @param fat fat percentage
     * @param fiber fiber percentage
     * @param sodium sodium percentage
     * @param protein protein percentage
     */
    public void createBarGraph(double calorie, double fat, double fiber, double sodium, double protein){

        barChart = (BarChart) findViewById(R.id.barChart2);
        Description description = barChart.getDescription();
        description.setEnabled(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawGridBackground(true);
        barChart.animateY(2000);

        XAxis xAxis = barChart.getXAxis();
        String[] calories = new String[]{"Calories","Fat","Fiber","Sodium","Protein"};
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(calories));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisLineWidth(3);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0f, (float) calorie));
        barEntries.add(new BarEntry(1f, (float) fat));
        barEntries.add(new BarEntry(2f, (float) fiber));
        barEntries.add(new BarEntry(3f, (float) sodium));
        barEntries.add(new BarEntry(4f, (float) protein));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Percentage(%)");
        barDataSet.setValueTextSize(10f);

        List<Integer> colors = new  ArrayList<>();
        if(calorie > 100){
            colors.add(Color.RED);
        }
        else{
            colors.add(Color.GREEN);
        }
        if(fat > 100){
            colors.add(Color.RED);
        }
        else{
            colors.add(Color.GREEN);
        }
        if(fiber > 100){
            colors.add(Color.RED);
        }
        else{
            colors.add(Color.GREEN);
        }
        if(sodium > 100){
            colors.add(Color.RED);
        }
        else{
            colors.add(Color.GREEN);
        }
        if(protein > 100){
            colors.add(Color.RED);
        }
        else{
            colors.add(Color.GREEN);
        }
        barDataSet.setColors(colors);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data);
    }

    /***
     * Compares values and shows warning messages if daily max values have been reached
     *
     * @param maxIntakes Maximum daily intakes
     * @param dailyIntakes Current daily intakes
     */
    public void showWarningMessage(ArrayList<Long> dailyIntakes, ArrayList<Long> maxIntakes){
        boolean flag = false;
        for(int i =0; i< dailyIntakes.size(); i++){
            if(dailyIntakes.get(i)>maxIntakes.get(i)){
                flag = true;
                break;
            }
        }
        String[] nutritions = new String[]{"Calories","Fat","Fiber","Sodium","Protein"};
        String[] nutritionalUnits = new String[]{"cal.","kcal","g","mg","g"};
        String s= "";

        if(flag){
            for(int i =0; i< dailyIntakes.size(); i++){
                if(dailyIntakes.get(i)>maxIntakes.get(i)){
                    s+= String.format("Warning: You have exceeded your current limit of %s intake.\n",nutritions[i]);
                }
            }
            TextView t = findViewById(R.id.warningDescription2);
            t.setText(s);
        }

        String s1 = "Consumed:\n"; //String.format("%25s %-20s %-10s\n","","Current","Limit");
        String s2 = "Maximum:\n"; //String.format("%25s %-20s %-10s\n","","Current","Limit");
        TextView t1 = findViewById(R.id.currentNumberIntakes2);
        TextView t2 = findViewById(R.id.dailyNumberIntakes2);
        for(int j =0; j< dailyIntakes.size(); j++){
            s1 += "" + (int)((long)dailyIntakes.get(j)) + nutritionalUnits[j] +"\n";
            s2 += "" + (int)((long)maxIntakes.get(j)) + nutritionalUnits[j] +"\n";
        }
        t1.setText(s1);
        t2.setText(s2);
    }
}
