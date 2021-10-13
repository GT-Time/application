package com.example.registration;

import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends ActionRequest {
    final static private String php = "UserLogin.php";
    private Map<String, String> parameters;

    //    send parameter values to database by posting method
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener, Response.ErrorListener error) {
        super(php, listener, error);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}
