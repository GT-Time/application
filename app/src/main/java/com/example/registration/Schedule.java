/*
 *  1 2 3 4 5 .. 14
 * :15
 * :30
 * :45
 */
package com.example.registration;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Schedule {/*
    private String monday[][] = new String [22][4];
    private String tuesday[][] = new String [22][4];
    private String wednesday[][] = new String [22][4];
    private String thursday[][] = new String [22][4];
    private String friday[][] = new String [22][4];
*/
    private Map<Integer, Integer> monday;
    private Map<Integer, Integer> tuesday;
    private Map<Integer, Integer> wednesday;
    private Map<Integer, Integer> thursday;
    private Map<Integer, Integer> friday;

    private List<Course> timeTable;
    public Schedule() {
        /*
        for (int i = 1; i < 22; ++i) {
            for (int j = 0; j < 4; ++j) {
                monday[i][j] = "";
                tuesday[i][j] = "";
                wednesday[i][j] = "";
                thursday[i][j] = "";
                friday[i][j] = "";
            }
        }
        */

        monday = new HashMap<Integer, Integer>();
        tuesday = new HashMap<Integer, Integer>();
        wednesday = new HashMap<Integer, Integer>();
        thursday = new HashMap<Integer, Integer>();
        friday = new HashMap<Integer, Integer>();

        timeTable = new ArrayList<Course>();
    }
    /*
    public void addSchedule(String courseTime, String courseDay) {
        if (courseTime.contains("TBA") || courseDay.contains("TBA")) return;

        String startTime = "";
        String endTime = "";

        String startPeriod = "";
        String endPeriod = "";

        String[] timeFrame = courseTime.split("-", 2);

        startTime = timeFrame[0].split(" ", 2)[0];
        startPeriod = timeFrame[0].split(" ", 2)[1];

        endTime = timeFrame[1].split(" ", 2)[0];
        endPeriod = timeFrame[1].split(" ", 2)[1];

        int startSec = Integer.parseInt(startTime.split(":",2)[0]) * 60 + Integer.parseInt(startTime.split(":",2)[1]);
        int endSec = Integer.parseInt(endTime.split(":",2)[0]) * 60 + Integer.parseInt(endTime.split(":",2)[1]);

        if (startPeriod.indexOf("p") > -1) {
            startSec += 60 * 12;
        }

        if (endPeriod.indexOf("p") > -1) {
            endSec += 60 * 12;
        }


        if(courseDay.indexOf('M') > -1) {
            monday.put(startSec, endSec);
        }

        if(courseDay.indexOf('T') > -1) {
            tuesday.put(startSec, endSec);
        }

        if(courseDay.indexOf('W') > -1) {
            wednesday.put(startSec, endSec);
        }

        if(courseDay.indexOf('R') > -1) {
            thursday.put(startSec, endSec);
        }

        if(courseDay.indexOf('F') > -1) {
            friday.put(startSec, endSec);
        }
    }
*/
    public boolean validate(String courseTime, String courseDay) {

        if (courseTime.contains("TBA") || courseDay.contains("TBA")) return true;

        String startTime = "";
        String endTime = "";

        String startPeriod = "";
        String endPeriod = "";

        String[] timeFrame = courseTime.split("-", 2);

        timeFrame[0].trim();
        timeFrame[1].trim();

        startTime = timeFrame[0].split(" ", 2)[0];
        startPeriod = timeFrame[0].split(" ", 2)[1];

        endTime = timeFrame[1].split(" ", 2)[0];
        endPeriod = timeFrame[1].split(" ", 2)[1];

        int startSec = Integer.parseInt(startTime.split(":",2)[0]) * 60 + Integer.parseInt(startTime.split(":",2)[1]);
        int endSec = Integer.parseInt(endTime.split(":",2)[0]) * 60 + Integer.parseInt(endTime.split(":",2)[1]);


        if (startPeriod.indexOf("p") > -1) {
            startSec += 60 * 12;
        }

        if (endPeriod.indexOf("p") > -1) {
            endSec += 60 * 12;
        }

        Iterator iterator;
        for(int i = 0; i < courseDay.length(); i++) {
            char day = courseDay.charAt(i);
            if(day == 'M') iterator = monday.entrySet().iterator();
            else if(day == 'T') iterator = tuesday.entrySet().iterator();
            else if(day == 'W') iterator = wednesday.entrySet().iterator();
            else if(day == 'R') iterator = thursday.entrySet().iterator();
            else if(day == 'F') iterator = friday.entrySet().iterator();
            else iterator = null;

            while (iterator.hasNext()) {
                Map.Entry element = (Map.Entry) iterator.next();
                if(startSec > (int) element.getKey() || startSec < (int) element.getValue()) return false;
                if(endSec > (int) element.getKey() || endSec < (int) element.getValue()) return false;
            }
        }

        return true;
    }

    public void addSchedule(Course course) {
        if(validate(course.getCourseTime(), course.getCourseDay())) {
            timeTable.add(course);
        }
    }

    public void deleteSchedule(Course course) {
        timeTable.remove(course);
    }

    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, Context context) {
        /*
        int max_length = 0;
        String maxString = "";
        for(int i = 8; i < 22; i++){
            for(int j = 0; j < 4; j++) {
                if(this.monday[i][j].length()>max_length) {
                    max_length = this.monday[i][j].length();
                    maxString = this.monday[i][j];
                }
                if(this.tuesday[i][j].length()>max_length) {
                    max_length = this.tuesday[i][j].length();
                    maxString = this.tuesday[i][j];
                }
                if(this.wednesday[i][j].length()>max_length) {
                    max_length = this.wednesday[i][j].length();
                    maxString = this.wednesday[i][j];
                }
                if(this.thursday[i][j].length()>max_length) {
                    max_length = this.thursday[i][j].length();
                    maxString = this.thursday[i][j];
                }
                if(this.friday[i][j].length()>max_length) {
                    max_length = this.friday[i][j].length();
                    maxString = this.friday[i][j];
                }
            }
        }

        for (int i = 8; i < 22; ++i) {
            for (int j = 0; j < 4; ++j) {
                if(!this.monday[i][j].equals("") && !this.monday[i][j].equals("Class")) {
                    monday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    monday[i].setText(this.monday[i][j]);
                }
                else {
                    monday[i].setText(maxString);
                }
                if(!this.tuesday[i][j].equals("") && !this.tuesday[i][j].equals("Class")) {
                    tuesday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    tuesday[i].setText(this.tuesday[i][j]);
                }
                else {
                    tuesday[i].setText(maxString);
                }
                if(!this.wednesday[i][j].equals("") && !this.wednesday[i][j].equals("Class")) {
                    wednesday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    wednesday[i].setText(this.wednesday[i][j]);
                }
                else {
                    wednesday[i].setText(maxString);
                }
                if(!this.thursday[i][j].equals("") && !this.thursday[i][j].equals("Class")) {
                    thursday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    thursday[i].setText(this.thursday[i][j]);
                }
                else {
                    thursday[i].setText(maxString);
                }
                if(!this.friday[i][j].equals("") && !this.friday[i][j].equals("Class")) {
                    friday[i].setTextColor(ContextCompat.getColor(context, R.color.colorAnnie));
                    friday[i].setText(this.friday[i][j]);
                }
                else {
                    friday[i].setText(maxString);
                }
                monday[i].resizeText();
                tuesday[i].resizeText();
                wednesday[i].resizeText();
                thursday[i].resizeText();
                friday[i].resizeText();
            }
        }*/
    }
}
