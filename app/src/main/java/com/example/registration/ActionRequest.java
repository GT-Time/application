package com.example.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/*
 * This abstract defines DNS and transport method (Method.POST)
 */
public abstract class ActionRequest extends StringRequest {
    final static protected String URL = "http://ec2-44-197-174-212.compute-1.amazonaws.com/";

    public ActionRequest(String php, Response.Listener<String> listener, Response.ErrorListener error){
        super(Method.POST, URL + php, listener, error);
    }
}
