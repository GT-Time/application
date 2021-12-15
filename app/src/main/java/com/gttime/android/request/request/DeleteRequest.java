<<<<<<< HEAD:app/src/main/java/com/gttime/android/request/request/DeleteRequest.java
package com.example.registration.request;
=======
package com.gttime.android.request;
>>>>>>> 251b16ca2afc971eadf5b560844329f909fed034:app/src/main/java/com/gttime/android/request/DeleteRequest.java

import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends ActionRequest {

    final static private String php = "ScheduleDelete.php";
    private Map<String, String> parameters;

//    send parameter values to database by posting method
    public DeleteRequest(String userID, String courseCRN, Response.Listener<String> listener) {
        super(php, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseCRN", courseCRN);
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters;
    }
}

