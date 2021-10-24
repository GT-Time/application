package com.example.util.json;

import android.content.Context;

import com.example.registration.Course;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsonUtil {

    /*
     * Clear local json
     * @param File json filename to be fetched
     * @return String fetched json content
     */
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

    /*
     * Filter json based on received parameters
     * @param String json content
     * @param Map<String,String> parameters to filter
     * @return String string containing fltered json
     */
    public static JSONObject filterCourse(String json, Map<String, String> params) {

        JSONObject response = new JSONObject();
        try {
            response.put("response", new JsonArray());
        } catch (Exception e) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray;
            try {
                jsonArray = jsonObject.getJSONArray("courses");
            } catch (Exception e) {
                return response;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);


                for (Map.Entry<String, String> it: params.entrySet()) {
                    if(object.get(it.getKey()) != it.getValue()) continue;

                    response.accumulate("response", object);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
