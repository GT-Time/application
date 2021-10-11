package com.example.util.json;

import com.example.registration.Course;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
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
                list.add(new Course(courseTerm, courseDay, "", courseTitle, courseCRN, "", "", "", courseTime, courseLocation, courseInstructor, "", courseCredit, ""));
                ++index;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
