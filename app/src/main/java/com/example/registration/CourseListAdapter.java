package com.example.registration;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<CourseSchedule> courseScheduleList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private ArrayList<CourseSchedule> userScheduleList;
    private List<String> courseCRNList;
    public static int totalCredit = 0;

    public CourseListAdapter(Context context, List<CourseSchedule> courseScheduleList, Fragment parent) {
        this.context = context;
        this.courseScheduleList = courseScheduleList; // courseList in adapter
        this.parent = parent;
        this.userScheduleList = new ArrayList<CourseSchedule>(); // courseList from user dataabase
        this.courseCRNList = new ArrayList<String>();
        new BackgroundTask().execute();
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseScheduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseScheduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewgroup) {
        View v =  View.inflate(context,R.layout.course,null);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseInstructor = (TextView) v.findViewById(R.id.courseInstructor);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseTerm = (TextView) v.findViewById(R.id.courseTerm);
        TextView courseCRN = (TextView) v.findViewById(R.id.courseCRN);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);
        TextView courseDay = (TextView) v.findViewById(R.id.courseDay);

        courseTitle.setText(courseScheduleList.get(position).getClassTitle()+"-"+ courseScheduleList.get(position).getCourseSection());
        if(courseScheduleList.get(position).getProfessorName().equals("")) {
            courseInstructor.setText("TBA");
        }

        else {
            courseInstructor.setText("Professor " + courseScheduleList.get(position).getProfessorName());
        }
        courseCredit.setText(courseScheduleList.get(position).getCourseCredit());
        courseTerm.setText(courseScheduleList.get(position).getCourseTerm());
        courseCRN.setText(courseScheduleList.get(position).getCourseCRN());
        courseTime.setText(courseScheduleList.get(position).getCourseTime());
        courseDay.setText(courseScheduleList.get(position).getDay());

        v.setTag(courseScheduleList.get(position).getCourseCRN());

        Button addSchedule = (Button) v.findViewById(R.id.addScheduleButton);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = false;
                validate = userScheduleList.contains(courseScheduleList.get(position).getCourseTime(), courseScheduleList.get(position).getDay());

                if(!alreadyIn(courseCRNList, courseScheduleList.get(position).getCourseCRN())) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Course is already registered in your schedule")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;

                }

                else if(totalCredit + Integer.parseInt(courseScheduleList.get(position).getCourseCredit()) > 21) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Registered credit hours can not exceed 21 credits")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;
                }

                else if(validate == false) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Time duplicates with course registered")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response)
                        {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success)
                                {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = alert.setMessage("Course has been added to your schedule")
                                            .setPositiveButton("OK",null)
                                            .create();
                                    dialog.show();
                                    courseCRNList.add(courseScheduleList.get(position).getCourseCRN());
                                    userScheduleList.add(courseScheduleList.get(position));
                                    totalCredit+= Integer.parseInt(courseScheduleList.get(position).getCourseCredit());
                                    return;
                                }

                                else
                                {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = alert.setMessage("Course has not been added to your schedule")
                                            .setPositiveButton("OK",null)
                                            .create();
                                    dialog.show();
                                    return;
                                }
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };

                    AddRequest addRequest = new AddRequest(userID, courseScheduleList.get(position).getCourseCRN() +" ", responseListener, null);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }

            }
        });
        return v;
    }


    class BackgroundTask extends AsyncTask {
        String address;
        @Override
        protected void onPreExecute() {
            try {
                address = "http://ec2-44-197-174-212.compute-1.amazonaws.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Object[] objects) {
            try {
                URL url = new URL(address);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int status = httpURLConnection.getResponseCode();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while((temp = buffer.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                buffer.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                String result = (String) o;
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonResponse = jsonObject.getJSONArray("response");

                int index = 0;
                String courseInstructor;
                String courseTime;
                String courseDay;
                String courseCRN;
                String courseCredit;
                totalCredit = 0;
                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    courseInstructor = object.getString("courseInstructor");
                    courseTime = object.getString("courseTime");
                    courseDay = object.getString("courseDay");
                    courseCRN = object.getString("courseCRN");
                    courseCredit = object.getString("courseCredit");

                    totalCredit+= Integer.parseInt(courseCredit);
                    courseCRNList.add(courseCRN);
                    for(int i = 0; i < courseDay.length(); i++) userScheduleList.add(new CourseSchedule("", courseDay.charAt(i), "", "", courseCRN, "", "", "", courseTime, "", courseInstructor, "", courseCredit, ""));
                    ++index;
                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean alreadyIn(List<String> courseIDList, String item) {
        for(int i = 0; i < courseIDList.size(); i++) {
            if(courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }

}
