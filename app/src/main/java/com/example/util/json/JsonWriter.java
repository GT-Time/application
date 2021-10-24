package com.example.util.json;

import android.content.Context;

import com.example.registration.Course;
import com.example.util.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class JsonWriter {
    public boolean appendCourse(String file, Course course, Context context) {
        try {
            String filename = Util.getFileName(file);
            String json = JsonUtil.readJson(context, filename);
            String response = (String) json;

            // HACK
            JSONObject jsonFile;
            if (response.isEmpty()) jsonFile = new JSONObject();
            else {
                jsonFile = new JSONObject(response);
            }

            JSONArray jsonArray;
            try {
                jsonArray = jsonFile.getJSONArray("courses");
            } catch (Exception e) {
                jsonArray = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("courses", jsonArray);
                // TODO: chnnge it to received filename
                JsonUtil.clearJson(new File(context.getFilesDir(), filename));
            }

            JSONObject jsonObject = new JSONObject();

            String courseTerm = course.getCourseTerm();
            String courseTitle = course.getCourseTitle();
            String courseTime = course.getCourseTime();
            String courseDay = course.getCourseDay();
            String courseLocation = course.getCourseLocation();
            String courseInstructor = course.getCourseInstructor();
            String courseCRN = course.getCourseCRN();
            String courseCredit = course.getCourseCredit();
            String courseMajor = course.getCourseMajor();
            String courseArea = course.getCourseArea();
            String courseSection = course.getCourseSection();
            String courseClass = course.getCourseClass();
            String courseUniversity = course.getCourseUniversity();
            String courseAttribute = course.getCourseAttribute();

            jsonObject.put("courseTerm", courseTerm);
            jsonObject.put("courseTitle", courseTitle);
            jsonObject.put("courseTime", courseTime);
            jsonObject.put("courseDay", courseDay);
            jsonObject.put("courseLocation", courseLocation);
            jsonObject.put("courseInstructor", courseInstructor);
            jsonObject.put("courseCRN", courseCRN);
            jsonObject.put("courseCredit", courseCredit);
            jsonObject.put("courseMajor", courseMajor);
            jsonObject.put("courseArea", courseArea);
            jsonObject.put("courseSection", courseSection);
            jsonObject.put("courseClass", courseClass);
            jsonObject.put("courseUniversity", courseUniversity);
            jsonObject.put("courseAttribute", courseAttribute);

            jsonArray.put(jsonObject);

            JSONObject updatedJson = new JSONObject();
            updatedJson.put("courses", jsonArray);

            String jsonString = updatedJson.toString();

            File f = new File(context.getFilesDir(), filename);
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean deleteCourse(String json, Course course, Context context) {
        try {
            String response = (String) json;

            // HACK
            JSONObject jsonFile;
            if (response.isEmpty()) jsonFile = new JSONObject();
            else {
                jsonFile = new JSONObject(response);
            }

            JSONArray jsonArray;
            try {
                jsonArray = jsonFile.getJSONArray("courses");
            } catch (Exception e) {
                jsonArray = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("courses", jsonArray);
                JsonUtil.clearJson(new File(context.getFilesDir(), "ScheduleList.json"));
            }

            // TODO : remove course from json

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
