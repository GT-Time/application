package com.example.registration;

public class Course {
    public Course(String courseTerm, String courseMajor, String courseTitle, String courseCRN, String courseArea, String courseSection, String courseClass, String courseTime, String courseDay, String courseLocation , String courseInstructor, String courseUniversity, String courseCredit, String courseAttribute) {
        this.courseTerm = courseTerm;
        this.courseMajor = courseMajor;
        this.courseTitle = courseTitle;
        this.courseCRN = courseCRN;
        this.courseArea = courseArea;
        this.courseSection = courseSection;
        this.courseClass = courseClass;
        this.courseTime = courseTime;
        this.courseDay = courseDay;
        this.courseLocation = courseLocation;
        this.courseInstructor = courseInstructor;
        this.courseUniversity = courseUniversity;
        this.courseCredit = courseCredit;
        this.courseAttribute = courseAttribute;
    }

    public Course(String courseCRN, String courseTitle, String courseSection, String courseCredit, String courseTime, String courseDay) {
        this.courseCRN = courseCRN;
        this.courseTitle = courseTitle;
        this.courseSection = courseSection;
        this.courseCredit = courseCredit;
        this.courseTime = courseTime;
        this.courseDay = courseDay;
    }

    private String courseTerm;
    private String courseMajor;
    private String courseTitle;
    private String courseCRN;
    private String courseArea;
    private String courseSection;
    private String courseClass;
    private String courseTime;
    private String courseDay;
    private String courseLocation;
    private String courseInstructor;
    private String courseUniversity;
    private String courseCredit;
    private String courseAttribute;

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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(String courseDay) {
        this.courseDay = courseDay;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
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

}

