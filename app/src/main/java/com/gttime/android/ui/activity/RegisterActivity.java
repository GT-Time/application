package com.gttime.android.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.gttime.android.R;
import com.gttime.android.request.RegisterRequest;
import com.gttime.android.request.ValidateRequest;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
{
    private String userValidateID;
    private String userID;
    private String userPassword;
    private String userEmail;
    private String userGender;
    private String userMajor;

    private AlertDialog dialog;
    private boolean validate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Spinner spinner = findViewById(R.id.majorSpinner);
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText validateID = findViewById(R.id.validateID);
        final EditText id = findViewById(R.id.idText);
        final EditText pw = findViewById(R.id.pwText);
        final EditText email = findViewById(R.id.emailText);

        final RadioGroup genderGroup = findViewById(R.id.genderGroup);
        int genderID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderID)).getText().toString();


        //Listeners
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton genderButton = findViewById(checkedId);
                userGender = genderButton.getText().toString();
            }
        });

        final Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userValidateID = validateID.getText().toString();
                if (validate)
                {
                    return;
                }
                if(userValidateID.equals(""))
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = alert.setMessage("username can not be empty space")
                         .setPositiveButton("OK",null)
                         .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    /**
                     * Trigger customized functionality on response from server
                     */
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = alert.setMessage("username is available")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                validate = true;
                                return;
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = alert.setMessage("username is not available")
                                        .setPositiveButton("OK", null)
                                        .create();
                                dialog.show();
                                validate = false;
                                return;
                            }
                        } catch (Exception e) {
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
                        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = alert.setMessage(error.getMessage())
                                .setPositiveButton("OK", null)
                                .create();
                        dialog.show();
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(userValidateID, responseListener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        Button submitButton = findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userID = id.getText().toString();
                userPassword = pw.getText().toString();
                userEmail = email.getText().toString();
                userMajor = spinner.getSelectedItem().toString();
                if(!validate)
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = alert.setMessage("Please validate your username")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    validate = false;
                    return;
                }

                if(userID.equals("")||userPassword.equals("")||userEmail.equals("")||userMajor.equals("")||userGender.equals(""))
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = alert.setMessage("Please fill in the blanks")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;
                }

                if(userID != userValidateID) {
                    validate = false;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            int errorCode = jsonResponse.getInt("errorCode");
                            if(success)
                            {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = alert.setMessage("Account has been created")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }

                            else
                            {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = alert.setMessage("Failed to create an account : " + errorCode)
                                        .setNegativeButton("OK",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch(Exception e)
                        {
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
                        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                        dialog = alert.setMessage("Error : " + error.getMessage())
                                .setPositiveButton("OK", null)
                                .create();
                        dialog.show();
                    }
                };

                // Initiate request and request queue
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userEmail, userGender, userMajor,responseListener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    //ran once request has reached maximum number of trial
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
