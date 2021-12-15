<<<<<<< HEAD:app/src/main/java/com/gttime/android/request/request/RegisterRequest.java
package com.example.registration.request;
=======
package com.gttime.android.request;
>>>>>>> 251b16ca2afc971eadf5b560844329f909fed034:app/src/main/java/com/gttime/android/request/RegisterRequest.java

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

