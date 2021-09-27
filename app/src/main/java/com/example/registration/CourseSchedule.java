/*
 *  1 2 3 4 5 .. 14
 * :15
 * :30
 * :45
 */
package com.example.registration;

import androidx.annotation.Nullable;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CourseSchedule extends Schedule {
    private Map<Integer, Integer> parseTimedTime;

    // Additional variables for application
    private String courseTerm;
    private String courseMajor;
    private String courseCRN;
    private String courseArea;
    private String courseSection;
    private String courseClass;
    private String courseUniversity;
    private String courseCredit;
    private String courseAttribute;

    public CourseSchedule() {
        super();
    }

    public CourseSchedule(String courseTerm, char courseDay, String courseMajor, String courseTitle, String courseCRN, String courseArea, String courseSection, String courseClass, String courseTime, String courseLocation, String courseInstructor, String courseUniversity, String courseCredit, String courseAttribute) {
        this.parseTimedTime = new HashMap<Integer, Integer>();
        // inherited setter
        setDay(courseDay);
        setClassTitle(courseTitle);
        setTime(courseTime);
        setClassPlace(courseLocation);
        setProfessorName(courseInstructor);

        // extended setter
        setCourseTerm(courseTerm);
        setCourseMajor(courseMajor);
        setCourseCRN(courseCRN);
        setCourseArea(courseArea);
        setCourseSection(courseSection);
        setCourseClass(courseClass);
        setCourseUniversity(courseUniversity);
        setCredit(courseCredit);
        setCourseAttribute(courseAttribute);
    }

    /******************************************
     * HACK
     */
    private String courseDays;
    public CourseSchedule(String courseTerm, String courseDays, String courseMajor, String courseTitle, String courseCRN, String courseArea, String courseSection, String courseClass, String courseTime, String courseLocation, String courseInstructor, String courseUniversity, String courseCredit, String courseAttribute) {
        this.parseTimedTime = new HashMap<Integer, Integer>();
        // inherited setter
        setClassTitle(courseTitle);
        setTime(courseTime);
        setClassPlace(courseLocation);
        setProfessorName(courseInstructor);

        // extended setter
        setCourseDays(courseDays);
        setCourseTerm(courseTerm);
        setCourseMajor(courseMajor);
        setCourseCRN(courseCRN);
        setCourseArea(courseArea);
        setCourseSection(courseSection);
        setCourseClass(courseClass);
        setCourseUniversity(courseUniversity);
        setCredit(courseCredit);
        setCourseAttribute(courseAttribute);
    }

    public String getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(String courseDays) {
        this.courseDays = courseDays;
    }
    //**************************************

    public void setDay(char day) {
        switch (day) {
            case 'M':
                setDay(0);
                break;

            case 'T':
                setDay(1);
                break;

            case 'W':
                setDay(2);
                break;

            case 'R':
                setDay(3);
                break;

            case 'F':
                setDay(4);
                break;
        }
    }

    public void setTime(String time) {
        String [] timeArr = time.split("-", 2);
        // HACK : handle exception
        if (timeArr.length < 2) return;

        Map.Entry parseTimedStart = this.parseTime(timeArr[0]);
        Map.Entry parseTimedEnd = this.parseTime(timeArr[1]);
        parseTimedTime.put((Integer) parseTimedStart.getKey(), (Integer) parseTimedStart.getValue());
        parseTimedTime.put((Integer) parseTimedEnd.getKey(), (Integer) parseTimedEnd.getValue());

        Set entrySet = parseTimedTime.entrySet();
        Iterator it = entrySet.iterator();

        HashMap.Entry curr = (HashMap.Entry)it.next();
        setStartTime(new Time((int) curr.getKey(), (int)curr.getValue()));

        curr = (HashMap.Entry)it.next();
        setEndTime(new Time((int) curr.getKey(), (int)curr.getValue()));
    }

    /**
     *
     * @param time in String
     *        ex: 6:30 pm
     */
    public Map.Entry parseTime(String time) {
        time = time.trim();
        String[] timeFramePair = time.split(" ", 2);

        // HACK : handle exception
        if (timeFramePair.length < 2) return null;

        String hourMin = timeFramePair[0];
        String timeFrame = timeFramePair[1];

        String[] hourMinPair = hourMin.split(":", 2);
        // HACK :  handle exception
        if (timeFramePair.length < 2) return null;

        int hour = Integer.parseInt(hourMinPair[0]);
        int min = Integer.parseInt(hourMinPair[1]);

        if(time.contains("p")) hour += 12;

        Map.Entry<Integer, Integer> returnVal = new AbstractMap.SimpleEntry<Integer, Integer>(hour, min);
        return returnVal;
    }


    public String parseDay(int day) {
        char parsed = ' ';
        switch(day) {
            case 0 :
                parsed = 'M';
                break;
            case 1 :
                parsed = 'T';
                break;

            case 2 :
                parsed = 'W';
                break;

            case 3 :
                parsed = 'H';
                break;

            case 4 :
                parsed = 'F';
                break;
        }

        return Character.toString(parsed);
    }

    /**
     *  Additional variable setter & getter functions
     */

    public void setCRN(String courseCRN) {
        this.courseCRN = courseCRN;
    }

    public String getCRN() {
        return this.courseCRN;
    }

    public void setCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCredit() {
        return this.courseCredit;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseCRN() {
        return courseCRN;
    }

    public void setCourseCRN(String courseCRN) {
        this.courseCRN = courseCRN;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public String getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(String courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseAttribute() {
        return courseAttribute;
    }

    public void setCourseAttribute(String courseAttribute) {
        this.courseAttribute = courseAttribute;
    }

    public String getCourseTime() {
        return getStartTime() + " : " + getEndTime();
    }
}
