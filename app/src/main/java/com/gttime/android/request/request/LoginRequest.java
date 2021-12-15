<<<<<<< HEAD:app/src/main/java/com/gttime/android/request/request/LoginRequest.java
package com.example.registration.request;
=======
package com.gttime.android.request;
>>>>>>> 251b16ca2afc971eadf5b560844329f909fed034:app/src/main/java/com/gttime/android/request/LoginRequest.java

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
