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
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private Schedule schedule;
    private List<String> courseNumberList;
    public static int totalCredit = 0;

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
        schedule = new Schedule();
        courseNumberList = new ArrayList<String>();
        new BackgroundTask().execute();
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewgroup) {
        View v =  View.inflate(context,R.layout.course,null);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseTerm = (TextView) v.findViewById(R.id.courseTerm);
        TextView courseID = (TextView) v.findViewById(R.id.courseID);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        courseTitle.setText(courseList.get(position).getCourseTitle()+"-"+courseList.get(position).getCourseDivide());
        if(courseList.get(position).getCourseProfessor().equals("")) {
            courseProfessor.setText("TBA");
        }

        else {
            courseProfessor.setText("Professor " + courseList.get(position).getCourseProfessor());
        }
        courseCredit.setText(courseList.get(position).getCourseCredit() + " Credit");
        courseTerm.setText(courseList.get(position).getCourseTerm() + " semester");
        courseID.setText(courseList.get(position).getCourseID());
        courseTime.setText(courseList.get(position).getCourseTime());

        v.setTag(courseList.get(position).getCourseID());

            /*
             *  Make(Add course) schedule based on the course added
            */

        Button addSchedule = (Button) v.findViewById(R.id.addScheduleButton);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = false;
                validate = schedule.validate(courseList.get(position).getCourseTime());

                //if course is already registered
                if(!alreadyIn(courseNumberList, courseList.get(position).getCourseNumber())) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Course is already registered in your schedule")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;

                }
                else if(totalCredit + courseList.get(position).getCourseCredit() > 21) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Registered credit hours can not exceed 21 credits")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;
                }
                //if course registered time duplicates
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
                                    //parent - 자신을 불러낸 course Fragment 에서 알림창을 띄워줌
                                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = alert.setMessage("Course has been added to your schedule")
                                            .setPositiveButton("OK",null)
                                            .create();
                                    dialog.show();
                                    courseNumberList.add(courseList.get(position).getCourseNumber());
                                    schedule.addSchedule(courseList.get(position).getCourseTime());
                                    totalCredit+= courseList.get(position).getCourseCredit();
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

                    AddRequest addRequest = new AddRequest(userID, courseList.get(position).getCourseNumber() +" ", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }

            }
        });
        return v;
    }

    /*
     *      Read added course from database
     */

    class BackgroundTask extends AsyncTask {
        String address;
        @Override
        protected void onPreExecute() {
            try {
                address = "http://sch1261315.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
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
                String courseProfessor;
                String courseTime;
                String courseNumber;
                totalCredit = 0;
                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseNumber = object.getString("courseNumber");
                    totalCredit+= Integer.parseInt(object.getString("courseCredit").split("TO")[0]);
                    courseNumberList.add(courseNumber);
                    schedule.addSchedule(courseTime);
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
