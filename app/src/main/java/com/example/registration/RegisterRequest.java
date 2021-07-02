package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://ec2-3-237-63-241.compute-1.amazonaws.com/UserRegister.php";
    private Map<String, String> parameters;

//    send parameter values to database by posting method
    public RegisterRequest(String userID, String userPassword, String userEmail, String userGender, String userMajor, Response.Listener<String> listener, Response.ErrorListener error) {
        super(Method.POST, URL, listener, error);
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

