package com.example.registration.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.registration.request.LoginRequest;
import com.example.registration.Pop;
import com.example.registration.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        instantiation
        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.pwText);
        final TextView registerButton = findViewById(R.id.registerButton);
        final Button loginButton = findViewById(R.id.loginButton);
        final TextView informationButton = findViewById(R.id.information);
//        listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            int errorCode = jsonResponse.getInt("errorCode");
                            String receivedUserID = jsonResponse.getString("userID");

                            if(success) {
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("userID",userID);
                                startActivity(intent);
                                return;
                            }

                            else {
                                AlertDialog.Builder message = new AlertDialog.Builder(LoginActivity.this);
                                dialog = message.setMessage("Login failed : " + errorCode)
                                                .setPositiveButton("OK",null)
                                                .create();
                                dialog.show();
                                return;
                            }
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener()
                {
                    /**
                     * Display error message on dialoy
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                        dialog = alert.setMessage("Error : " + error.getMessage())
                                .setPositiveButton("OK", null)
                                .create();
                        dialog.show();
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Pop.class));
            }
        });
    }

}
