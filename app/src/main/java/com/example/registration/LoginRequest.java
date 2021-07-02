package com.example.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://ec2-3-237-63-241.compute-1.amazonaws.com/UserLogin.php";
    private Map<String, String> parameters;

    //    send parameter values to database by posting method
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener, Response.ErrorListener error) {
        super(Method.POST, URL, listener, error);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}
