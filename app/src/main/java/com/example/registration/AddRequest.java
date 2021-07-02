package com.example.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends StringRequest {

    final static private String URL = "http://sch1261315.cafe24.com/CourseAdd.php";
    private Map<String, String> parameters;

//    send parameter values to database by posting method
    public AddRequest(String userID, String courseNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("courseNumber",courseNumber);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}

