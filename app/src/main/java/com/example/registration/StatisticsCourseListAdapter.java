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

public class StatisticsCourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;


    public StatisticsCourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
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
        View v = View.inflate(context, R.layout.statistics, null);
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        TextView courseNumber = (TextView) v.findViewById(R.id.courseNumber);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseStatisticTitle);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView courseTime = (TextView) v.findViewById(R.id.statisticTimeID);

        courseNumber.setText(courseList.get(position).getCourseNumber());
        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseDivide.setText(courseList.get(position).getCourseDivide());
        courseTime.setText(courseList.get(position).getCourseTime());

        v.setTag(courseList.get(position).getCourseID());
        /*
         *  Make(Add course) schedule based on the course added
         */

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                    AlertDialog dialog = alert.setMessage("Course has been deleted from schedule")
                                            .setPositiveButton("OK",null)
                                            .create();
                                    dialog.show();
                                    StatisticsFragment.totalCredit -= courseList.get(position).getCourseCredit();
                                    StatisticsFragment.statCredit.setText(StatisticsFragment.totalCredit + "Credits");
                                    courseList.remove(position);
                                    notifyDataSetChanged();
                                    return;
                                }

                                else
                                {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = alert.setMessage("Course has not been removed from your schedule")
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

                    DeleteRequest deleteRequest = new DeleteRequest(userID, courseList.get(position).getCourseNumber(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(deleteRequest);
                }
        });
        return v;
    }

    /*
     *      Read added course from database
     */


    }

