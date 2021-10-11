package com.example.registration;

import android.content.Context;
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
import com.example.util.util.Util;

import org.json.JSONObject;

import java.util.List;

public class StatisticsCourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseScheduleList;
    private Fragment parent;
    private String userID = MainActivity.userID;


    public StatisticsCourseListAdapter(Context context, List<Course> courseScheduleList, Fragment parent) {
        this.context = context;
        this.courseScheduleList = courseScheduleList;
        this.parent = parent;
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
        View v = View.inflate(context, R.layout.statistics, null);
        Button deleteButton = v.findViewById(R.id.deleteButton);
        TextView courseArea = v.findViewById(R.id.courseArea);
        TextView courseCRN = v.findViewById(R.id.courseCRN);
        TextView courseTitle = v.findViewById(R.id.courseStatisticTitle);
        TextView courseSection = v.findViewById(R.id.courseSection);
        TextView courseTime = v.findViewById(R.id.statisticTimeID);

        courseArea.setText(courseScheduleList.get(position).getCourseArea());
        courseCRN.setText(courseScheduleList.get(position).getCourseCRN());
        courseTitle.setText(courseScheduleList.get(position).getCourseTitle());
        courseSection.setText(courseScheduleList.get(position).getCourseSection());
        courseTime.setText(courseScheduleList.get(position).getCourseTime());

        v.setTag(courseScheduleList.get(position).getCourseCRN());

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
                                    StatisticsFragment.totalCredit -= Util.parseInt(courseScheduleList.get(position).getCourseCredit().split(" ")[0]);
                                    StatisticsFragment.statCredit.setText(StatisticsFragment.totalCredit + " Credits");
                                    courseScheduleList.remove(position);
                                    notifyDataSetChanged();
                                }

                                else
                                {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                    AlertDialog dialog = alert.setMessage("Course has not been removed from your schedule")
                                            .setPositiveButton("OK",null)
                                            .create();
                                    dialog.show();
                                }
                                return;
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };

                    DeleteRequest deleteRequest = new DeleteRequest(userID, courseScheduleList.get(position).getCourseCRN(), responseListener);
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

