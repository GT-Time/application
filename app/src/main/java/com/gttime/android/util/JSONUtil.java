package com.gttime.android.util;

import com.gttime.android.component.Course;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JSONUtil {
    public static List<Course> fetchCourse(java.lang.String json) {
        List<Course> list = new ArrayList<Course>();

        if (json.isEmpty()) return list;

        try {
            java.lang.String result = (java.lang.String) json;
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonResponse;

            try {
                jsonResponse = jsonObject.getJSONArray("courses");
            } catch (Exception e) {
                return new ArrayList<Course>();
            }

            int index = 0;
            java.lang.String courseTerm;
            java.lang.String courseTitle;
            java.lang.String courseTime;
            java.lang.String courseDay;
            java.lang.String courseLocation;
            java.lang.String courseInstructor;
            java.lang.String courseCRN;
            java.lang.String courseCredit;
            java.lang.String courseMajor;
            java.lang.String courseArea;
            java.lang.String courseSection;
            java.lang.String courseClass;
            java.lang.String courseUniversity;
            java.lang.String courseAttribute;
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

    public static java.lang.String readJson(File file) {
        try {
            file.createNewFile(); // if file already exists will do nothing
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            java.lang.String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            java.lang.String response = stringBuilder.toString();
            return response;
        }
        catch(IOException io) {
            io.printStackTrace();
        }

        return "";
    }

    public static void clearJson(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean appendCourse(File file, Course course) {
        try {
            java.lang.String json = readJson(file);
            java.lang.String response = (java.lang.String) json;

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
                clearJson(file);
            }

            JSONObject jsonObject = new JSONObject();

            java.lang.String courseTerm = course.getCourseTerm();
            java.lang.String courseTitle = course.getCourseTitle();
            java.lang.String courseTime = course.getCourseTime();
            java.lang.String courseDay = course.getCourseDay();
            java.lang.String courseLocation = course.getCourseLocation();
            java.lang.String courseInstructor = course.getCourseInstructor();
            java.lang.String courseCRN = course.getCourseCRN();
            java.lang.String courseCredit = course.getCourseCredit();
            java.lang.String courseMajor = course.getCourseMajor();
            java.lang.String courseArea = course.getCourseArea();
            java.lang.String courseSection = course.getCourseSection();
            java.lang.String courseClass = course.getCourseClass();
            java.lang.String courseUniversity = course.getCourseUniversity();
            java.lang.String courseAttribute = course.getCourseAttribute();

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

            java.lang.String jsonString = updatedJson.toString();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean deleteCourse(File file, Course course) {
        try {
            java.lang.String response = readJson(file);

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
                clearJson(file);
            }

            // TODO : remove course from json

            java.lang.String courseTerm = course.getCourseTerm();
            java.lang.String courseTitle = course.getCourseTitle();
            java.lang.String courseTime = course.getCourseTime();
            java.lang.String courseDay = course.getCourseDay();
            java.lang.String courseLocation = course.getCourseLocation();
            java.lang.String courseInstructor = course.getCourseInstructor();
            java.lang.String courseCRN = course.getCourseCRN();
            java.lang.String courseCredit = course.getCourseCredit();
            java.lang.String courseMajor = course.getCourseMajor();
            java.lang.String courseArea = course.getCourseArea();
            java.lang.String courseSection = course.getCourseSection();
            java.lang.String courseClass = course.getCourseClass();
            java.lang.String courseUniversity = course.getCourseUniversity();
            java.lang.String courseAttribute = course.getCourseAttribute();

            JSONArray updatedJsonArray = new JSONArray();
            for (int i =0; i<jsonArray.length(); i++) {
                java.lang.String elem = jsonArray.getString(i);

                if (elem.contains(courseCRN)) continue;
                updatedJsonArray.put(jsonArray.get(i));
            }

            JSONObject updatedJson = new JSONObject();
            updatedJson.put("courses", updatedJsonArray);

            java.lang.String jsonString = updatedJson.toString();

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
