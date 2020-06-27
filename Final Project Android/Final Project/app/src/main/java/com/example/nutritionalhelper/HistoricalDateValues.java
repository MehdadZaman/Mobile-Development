package com.example.nutritionalhelper;

import java.util.ArrayList;

public class HistoricalDateValues {

    private String date;
    private ArrayList<Long> dateValues;

    public HistoricalDateValues(String date, ArrayList<Long> dateValues) {
        this.date = date;
        this.dateValues = dateValues;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Long> getDateValues() {
        return dateValues;
    }

    public void setDateValues(ArrayList<Long> dateValues) {
        this.dateValues = dateValues;
    }
}
