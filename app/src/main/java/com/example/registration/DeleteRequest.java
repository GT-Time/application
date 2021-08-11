package com.example.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends ActionRequest {

    final static private String php = "ScheduleDelete.php";
    private Map<String, String> parameters;

//    send parameter values to database by posting method
    public DeleteRequest(String userID, String courseNumber, Response.Listener<String> listener) {
        super(php, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("courseNumber",courseNumber);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}

