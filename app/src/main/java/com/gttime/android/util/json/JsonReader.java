package com.gttime.android.util.json;

import com.gttime.android.component.Course;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public List<Course> fetchCourse(String json) {
        List<Course> list = new ArrayList<Course>();

        if (json.isEmpty()) return list;

        try {
            String result = (String) json;
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonResponse;

            try {
                jsonResponse = jsonObject.getJSONArray("courses");
            } catch (Exception e) {
                return new ArrayList<Course>();
            }

            int index = 0;
            String courseTerm;
            String courseTitle;
            String courseTime;
            String courseDay;
            String courseLocation;
            String courseInstructor;
            String courseCRN;
            String courseCredit;
            String courseMajor;
            String courseArea;
            String courseSection;
            String courseClass;
            String courseUniversity;
            String courseAttribute;
            while(index < jsonResponse.length()) {
                JSONObject object = jsonResponse.getJSONObject(index);
                courseTerm = object.getString("courseTerm");
                courseTitle = object.getString("courseTitle");
                courseTime = object.getString("courseTime");
                courseDay = object.getString("courseDay");
                courseLocation = object.getString("courseLocation");
                courseInstructor = object.getString("courseInstructor");
                courseCRN = object.getString("courseCRN");
                courseCredit = object.getString("courseCredit");
                courseMajor = object.getString("courseMajor");
                courseArea = object.getString("courseArea");
                courseSection = object.getString("courseSection");
                courseClass = object.getString("courseClass");
                courseUniversity = object.getString("courseUniversity");
                courseAttribute = object.getString("courseAttribute");
                list.add(new Course(courseTerm, courseDay, courseMajor, courseTitle, courseCRN, courseArea, courseSection, courseClass, courseTime, courseLocation, courseInstructor, courseUniversity, courseCredit, courseAttribute));
                ++index;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
