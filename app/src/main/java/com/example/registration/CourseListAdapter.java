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
import com.github.tlaabs.timetableview.Time;

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
    private List<Course> courseScheduleList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private List<Course> userCourseList;
    private List<String> courseCRNList;
    public static int totalCredit;

    public CourseListAdapter(Context context, List<Course> courseScheduleList, Fragment parent) {
        this.context = context;
        this.courseScheduleList = courseScheduleList; // courseList in adapter
        this.parent = parent;
        this.userCourseList = new ArrayList<Course>(); // courseList from user dataabase
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

        courseTitle.setText(courseScheduleList.get(position).getCourseTitle()+"-"+ courseScheduleList.get(position).getCourseSection());
        if(courseScheduleList.get(position).getCourseInstructor().equals("")) {
            courseInstructor.setText("TBA");
        }

        else {
            courseInstructor.setText("Professor " + courseScheduleList.get(position).getCourseInstructor());
        }
        courseCredit.setText(courseScheduleList.get(position).getCourseCredit());
        courseTerm.setText(courseScheduleList.get(position).getCourseTerm());
        courseCRN.setText(courseScheduleList.get(position).getCourseCRN());
        courseTime.setText(courseScheduleList.get(position).getCourseTime());
        courseDay.setText(courseScheduleList.get(position).getCourseDay());

        v.setTag(courseScheduleList.get(position).getCourseCRN());

        Button addSchedule = (Button) v.findViewById(R.id.addScheduleButton);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(alreadyIn(courseCRNList, courseScheduleList.get(position).getCourseCRN())) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Course is already registered in your schedule")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;

                }

                int credit = Util.parseInt(courseScheduleList.get(position).getCourseCredit().split(" ")[0]);

                if(exceedAllowedCredit(totalCredit, credit)) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Registered credit hours can not exceed 21 credits")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();

                    return;

                }


                if(!validate(courseScheduleList.get(position), userCourseList)) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Time duplicates with course registered")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();

                    return;

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

                            if(success)
                            {
                                AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = alert.setMessage("Course has been added to your schedule")
                                        .setPositiveButton("OK",null)
                                        .create();
                                dialog.show();
                                courseCRNList.add(courseScheduleList.get(position).getCourseCRN());
                                userCourseList.add(courseScheduleList.get(position));
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
        });
        return v;
    }


    class BackgroundTask extends AsyncTask {
        String address;
        @Override
        protected void onPreExecute() {
            try {
                address = "http://ec2-3-222-117-117.compute-1.amazonaws.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
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
                    userCourseList.add(new Course("", courseDay, "", "", courseCRN, "", "", "", courseTime, "", courseInstructor, "", courseCredit, ""));
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
                return true;
            }
        }
        return false;
    }

    /**
     * Validate course time and course day validation.
     *
     * @param course course to be added to list
     * @param courseList list of course
     * @return boolean whether course can be added
     */
    public boolean validate(Course course, List<Course> courseList) {
        String courseDays = course.getCourseDay();

        // handle exception
        courseDays = courseDays.trim();
        if(courseDays.contains("TBA") || courseDays.equals("")) return true;

        for (int i = 0; i < courseList.size(); i++) { // for each course in courseList
            for(int j = 0; j < course.getCourseDay().length(); j++) { // for each courseDay in course
                if (courseList.get(i).getCourseDay().indexOf(course.getCourseDay().charAt(j)) <= -1) { // course day does not overlap
                    continue;
                }

                if (courseList.get(i).getStartTime().getHour() < course.getStartTime().getHour() && courseList.get(i).getEndTime().getHour() > course.getStartTime().getHour()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean exceedAllowedCredit(int total, int credit) {
        if(total + credit > 21) return true;
        return false;
    }
}
