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
        setStartTime(new CourseTime(Util.split(courseTime,"-",2)[0]));
        setEndTime(new CourseTime(Util.split(courseTime,"-",2)[1]));
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
