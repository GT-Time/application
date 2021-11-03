package com.gttime.android.request;

import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends ActionRequest {

    final static private String php = "UserRegister.php";
    private Map<String, String> parameters;

//    send parameter values to database by posting method
    public RegisterRequest(String userID, String userPassword, String userEmail, String userGender, String userMajor, Response.Listener<String> listener, Response.ErrorListener error) {
        super(php, listener, error);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userEmail",userEmail);
        parameters.put("userGender",userGender);
        parameters.put("userMajor",userMajor);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}

