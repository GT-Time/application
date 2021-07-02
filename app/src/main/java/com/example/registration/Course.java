package com.example.registration;

public class Course {
    public Course(String courseNumber, String courseUniversity, int courseYear, String courseTerm, String courseArea, String courseMajor, String courseTitle, int courseCredit,  String courseProfessor, String courseTime, String courseLocation, String courseID, String courseDivide) {
        this.courseNumber = courseNumber;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajor = courseMajor;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseLocation = courseLocation;
        this.courseID = courseID;
        this.courseDivide = courseDivide;
    }

    public Course(String courseNumber, String courseTitle, String courseDivide, int courseCredit, String courseTime) {
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.courseDivide = courseDivide;
        this.courseCredit = courseCredit;
        this.courseTime = courseTime;
    }

    private String courseNumber;
    private String courseUniversity;
    private int courseYear;
    private String courseTerm;
    private String courseArea;
    private String courseMajor;
    private String courseTitle;
    private int courseCredit;
    private String courseProfessor;
    private String courseTime;
    private String courseLocation;
    private String courseID;
    private String courseDivide;

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(String courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
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

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseDivide() {
        return courseDivide;
    }

    public void setCourseDivide(String courseDivide) {
        this.courseDivide = courseDivide;
    }
}

