//This class checks whether submitted userID is available to be registered

package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class ValidateRequest extends ActionRequest {

    final static private String php = "UserValidate.php";
    private Map<String, String> parameters;

    /**
     *  Instantiate upper hierarchy and determine method of request and data
     */
    public ValidateRequest(String userID, Response.Listener<String> listener, Response.ErrorListener error) {
        //super(Method.POST, URL, listener, error);
        super(php, listener, error);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}

