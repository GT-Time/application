package com.example.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/*
 * This abstract defines DNS and transport method (Method.POST)
 */
public abstract class ActionRequest extends StringRequest {
    final static protected String URL = "http://ec2-3-222-117-117.compute-1.amazonaws.com/";

    public ActionRequest(String php, Response.Listener<String> listener, Response.ErrorListener error){
        super(Method.POST, URL + php, listener, error);
    }
}
