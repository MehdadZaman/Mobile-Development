/***
 * This class is holds information of a historical date
 *
 * @author Mehdad Zaman
 * @id 112323211
 * Final Project
 * CSE 390 Section 2
 */
package com.example.nutritionalhelper;

import java.util.ArrayList;

public class HistoricalDateValues {

    /***
     * Historical date attributes
     */
    private String date;
    private ArrayList<Long> dateValues;

    /***
     * Constructor for creating historical date object
     *
     * @param date string rep. of the date
     * @param dateValues list of five nutritional intakes
     */
    public HistoricalDateValues(String date, ArrayList<Long> dateValues) {
        this.date = date;
        this.dateValues = dateValues;
    }

    /***
     * Returns string rep. of the date
     *
     * @return string rep. of the date
     */
    public String getDate() {
        return date;
    }

    /***
     * returns string rep. of the date
     *
     * @param date string rep. of the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /***
     * returns list of five nutritional intakes
     *
     * @return list of five nutritional intakes
     */
    public ArrayList<Long> getDateValues() {
        return dateValues;
    }

    /***
     * sets list of five nutritional intakes
     *
     * @param dateValues list of five nutritional intakes
     */
    public void setDateValues(ArrayList<Long> dateValues) {
        this.dateValues = dateValues;
    }
}
