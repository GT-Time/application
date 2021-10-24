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
    public boolean appendCourse(File file, Course course) {
        try {
//            String filename = Util.getFileName(file);
 //           String json = JsonUtil.readJson(new File(context.getFilesDir(), filename));
            String json = Util.fetchLocalFile(file);
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
                JsonUtil.clearJson(file);
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

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    // TODO: complete delete functionality
    public boolean deleteCourse(File file, Course course) {
        try {
            String response = Util.fetchLocalFile(file);

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
                JsonUtil.clearJson(file);
            }

            // TODO : remove course from json

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
