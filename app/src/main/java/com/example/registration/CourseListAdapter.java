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

import com.example.util.json.JsonReader;
import com.example.util.json.JsonUtil;
import com.example.util.json.JsonWriter;
import com.example.util.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseScheduleList; // list of course from db
    private Fragment parent;
    private String userID = MainActivity.userID;
    private List<Course> userCourseList; // list of course registered for user
    private String semester;
    public static int totalCredit;

    public CourseListAdapter(Context context, List<Course> courseScheduleList, String semester, Fragment parent) {
        this.context = context;
        this.courseScheduleList = courseScheduleList; // courseList in adapter
        this.parent = parent;
        this.userCourseList = new ArrayList<Course>(); // courseList from user dataabase
        this.semester = semester;
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
        View v =  View.inflate(context, R.layout.course,null);
        TextView courseTitle = v.findViewById(R.id.courseTitle);
        TextView courseInstructor = v.findViewById(R.id.courseInstructor);
        TextView courseCredit = v.findViewById(R.id.courseCredit);
        TextView courseTerm = v.findViewById(R.id.courseTerm);
        TextView courseCRN = v.findViewById(R.id.courseCRN);
        TextView courseTime = v.findViewById(R.id.courseTime);
        TextView courseDay = v.findViewById(R.id.courseDay);

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

        new BackgroundTask().execute();

        v.setTag(courseScheduleList.get(position).getCourseCRN());

        Button addSchedule = v.findViewById(R.id.addScheduleButton);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(alreadyIn(userCourseList, courseScheduleList.get(position).getCourseCRN())) {

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

                new Executor() {
                    @Override
                    public void execute(Runnable command) {
                        new Thread(command).start();
                    }
                }.execute(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            boolean success = new JsonWriter().appendCourse(semester, courseScheduleList.get(position), parent.getActivity());

                            if(success)
                            {
                                parent.getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                        AlertDialog dialog = alert.setMessage("Course has been added to your schedule")
                                                .setPositiveButton("OK",null)
                                                .create();
                                        dialog.show();
                                    }
                                });

                                userCourseList.add(courseScheduleList.get(position));

                                int credit = Util.parseInt(courseScheduleList.get(position).getCourseCredit().split(" ")[0]);
                                totalCredit+= credit;
                                return;
                            }

                            else
                            {
                                parent.getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                                        AlertDialog dialog = alert.setMessage("Course has not been added to your schedule")
                                                .setPositiveButton("OK",null)
                                                .create();
                                        dialog.show();
                                    }
                                });

                                return;
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return v;
    }



    class BackgroundTask extends AsyncTask {
        String filename;
        @Override
        protected void onPreExecute() {
            try {
                filename = Util.getFileName(semester);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Object[] objects) {
            return JsonUtil.readJson(parent.getActivity(), filename);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            userCourseList = new JsonReader().fetchCourse((String) o);

            for(int i = 0; i < userCourseList.size(); i++) totalCredit += Util.parseInt(userCourseList.get(i).getCourseCredit());
        }
    }

    public boolean alreadyIn(List<Course> courseList, String item) {
        for(int i = 0; i < courseList.size(); i++) {
            if(courseList.get(i).getCourseCRN().equals(item)) {
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

                // TODO : validate using math max and min
                if (courseList.get(i).getStartTime().getHour() < course.getStartTime().getHour() && courseList.get(i).getEndTime().getHour() > course.getStartTime().getHour()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean exceedAllowedCredit(int total, int credit) {
        return total + credit > 21;
    }
}