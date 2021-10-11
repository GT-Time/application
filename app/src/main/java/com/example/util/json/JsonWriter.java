package com.example.util.json;

import android.content.Context;

import com.example.registration.Course;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class JsonWriter {
    public boolean appendCourse(String json, Course course, Context context) {
        try{
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
            } catch(Exception e) {
                jsonArray = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("courses", jsonArray);
                JsonUtil.clearJson(new File(context.getFilesDir(), "ScheduleList.json"));
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

            jsonObject.put("courseTerm", courseTerm);
            jsonObject.put("courseTitle", courseTitle);
            jsonObject.put("courseTime", courseTime);
            jsonObject.put("courseDay", courseDay);
            jsonObject.put("courseLocation", courseLocation);
            jsonObject.put("courseInstructor", courseInstructor);
            jsonObject.put("courseCRN", courseCRN);
            jsonObject.put("courseCredit", courseCredit);

            jsonArray.put(jsonObject);

            JSONObject updatedJson = new JSONObject();
            updatedJson.put("courses", jsonArray);

            String jsonString = updatedJson.toString();

            File file = new File(context.getFilesDir(), "ScheduleList.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
