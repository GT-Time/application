
package com.gttime.android.request;

import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends ActionRequest {

    final static private String php = "CourseAdd.php";
    private Map<String, String> parameters;

//    send parameter values to database by posting method
    public AddRequest(String userID, String courseCRN, Response.Listener<String> listener, Response.ErrorListener error) {
        super(php, listener, error);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("courseCRN",courseCRN);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}

