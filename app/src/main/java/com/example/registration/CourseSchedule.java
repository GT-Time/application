/*
 *  1 2 3 4 5 .. 14
 * :15
 * :30
 * :45
 */
package com.example.registration;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CourseSchedule extends Schedule {
    private Map<Integer, Integer> parsedTime;

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
        this.parsedTime = new HashMap<Integer, Integer>();
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



    public void setDay(char day) {
        switch(day) {
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

        Map.Entry parsedStart = this.parse(timeArr[0]);
        Map.Entry parsedEnd = this.parse(timeArr[1]);
        parsedTime.put((Integer) parsedStart.getKey(), (Integer) parsedStart.getValue());
        parsedTime.put((Integer) parsedEnd.getKey(), (Integer) parsedEnd.getValue());

        Set entrySet = parsedTime.entrySet();
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
    public Map.Entry parse(String time) {
        time = time.trim();
        String hourMin = time.split(" ")[0];
        String timeFrame = time.split(" ")[1];

        String[] pair = hourMin.split(":", 2);
        int hour = Integer.parseInt(pair[0]);
        int min = Integer.parseInt(pair[1]);

        if(time.contains("p")) hour += 12;

        Map.Entry<Integer, Integer> returnVal = new AbstractMap.SimpleEntry<Integer, Integer>(hour, min);
        return returnVal;
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
