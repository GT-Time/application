package com.gttime.android.ui.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.gttime.android.component.Course;
import com.gttime.android.R;
import com.gttime.android.component.CourseSeat;
import com.gttime.android.ui.activity.MainActivity;
import com.gttime.android.util.IOUtil;
import com.gttime.android.util.IntegerUtil;
import com.gttime.android.util.JSONUtil;
import com.github.tlaabs.timetableview.Time;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CourseListAdapter extends BaseAdapter {
    private Context context;
    private List<CourseSeat> courseSeats; // list of course from db
    private Fragment parent;
    private String userID = MainActivity.userID;
    private List<Course> userCourseList; // list of course registered for user
    private String semester;
    public static int totalCredit;

    public CourseListAdapter(Context context, List<CourseSeat> courseSeats, Fragment parent) {
        this.context = context;
        this.courseSeats = courseSeats; // courseList in adapter
        this.parent = parent;
        this.userCourseList = new ArrayList<Course>(); // courseList from user dataabase
        this.semester = "";
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseSeats.size();
    }

    @Override
    public Object getItem(int position) {
        return courseSeats.get(position);
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
        TextView courseAttribute = v.findViewById(R.id.courseAttribute);
        TextView courseSeatActual = v.findViewById(R.id.seatActual);
        TextView courseSeatWaitlist = v.findViewById(R.id.seatWaitlist);

        courseTitle.setText(courseSeats.get(position).getCourse().getCourseTitle()+"-"+ courseSeats.get(position).getCourse().getCourseSection());
        if(courseSeats.get(position).getCourse().getCourseInstructor().equals("")) {
            courseInstructor.setText("TBA");
        }

        else {
            courseInstructor.setText(courseSeats.get(position).getCourse().getCourseInstructor());
        }
        courseCredit.setText(courseSeats.get(position).getCourse().getCourseCredit());
        courseTerm.setText(courseSeats.get(position).getCourse().getCourseTerm());
        courseCRN.setText(courseSeats.get(position).getCourse().getCourseCRN());
        courseTime.setText(courseSeats.get(position).getCourse().getCourseTime());
        courseDay.setText(courseSeats.get(position).getCourse().getCourseDay());
        courseAttribute.setText(courseSeats.get(position).getCourse().getCourseAttribute());
        courseSeatActual.setText("Actual:" + String.valueOf(courseSeats.get(position).getSeat().getSeatActual()) + '/' + String.valueOf(courseSeats.get(position).getSeat().getSeatCapacity()));
        courseSeatWaitlist.setText("Waitlist:" + String.valueOf(courseSeats.get(position).getSeat().getWaitlistActual()) + '/' + String.valueOf(courseSeats.get(position).getSeat().getWaitlistCapacity()));


        new BackgroundTask().execute();

        v.setTag(courseSeats.get(position).getCourse().getCourseCRN());

        Button addSchedule = v.findViewById(R.id.addScheduleButton);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(alreadyIn(userCourseList, courseSeats.get(position).getCourse().getCourseCRN())) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Course is already registered in your schedule")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();
                    return;

                }

                int credit = IntegerUtil.parseInt(courseSeats.get(position).getCourse().getCourseCredit().split(" ")[0]);

                if(exceedAllowedCredit(totalCredit, credit)) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = alert.setMessage("Registered credit hours can not exceed 21 credits")
                            .setPositiveButton("OK",null)
                            .create();
                    dialog.show();

                    return;

                }


                if(!validate(courseSeats.get(position).getCourse(), userCourseList)) {

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
                            boolean success = JSONUtil.appendCourse(new File(parent.getActivity().getFilesDir(), IOUtil.getFileName(semester)), courseSeats.get(position).getCourse());

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

                                userCourseList.add(courseSeats.get(position).getCourse());

                                int credit = IntegerUtil.parseInt(courseSeats.get(position).getCourse().getCourseCredit().split(" ")[0]);
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
                filename = IOUtil.getFileName(semester);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Object[] objects) {
            return JSONUtil.readJson(new File(parent.getActivity().getFilesDir(), filename));
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            userCourseList = JSONUtil.fetchCourse((String) o);

            for(int i = 0; i < userCourseList.size(); i++) totalCredit += IntegerUtil.parseInt(userCourseList.get(i).getCourseCredit());
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
     * @param course course to be validated
     * @param courseList list of course registered
     * @return boolean whether course can be added
     */
    public boolean validate(Course course, List<Course> courseList) {
        String courseDays = course.getCourseDay();

        // handle exception
        courseDays = courseDays.trim();
        if(courseDays.contains("TBA") || courseDays.equals("")) return true;

        for (int i = 0; i < courseList.size(); i++) { // for each course in courseList
            Course registeredCourse = courseList.get(i);
            String registeredCourseDays = registeredCourse.getCourseDay();

            for(int j = 0; j < courseDays.length(); j++) {
                if (registeredCourseDays.indexOf(courseDays.charAt(j)) <= -1) {
                    continue;
                }

                // TODO : validate using math max and min
                Time registeredCourseStartTime = registeredCourse.getStartTime();
                Time registeredCourseEndTime = registeredCourse.getEndTime();
                Time courseStartTime = course.getStartTime();
                Time courseEndTime = course.getEndTime();

                int registeredCourseStartConverted = registeredCourseStartTime.getHour() * 60 + registeredCourseStartTime.getMinute();
                int registeredCourseEndConverted = registeredCourseEndTime.getHour() * 60 + registeredCourseEndTime.getMinute();
                int courseStartConverted = courseStartTime.getHour() * 60 + courseStartTime.getMinute();
                int courseEndConvereted = courseEndTime.getHour() * 60 + courseEndTime.getMinute();

                boolean overlapped = (Math.max(registeredCourseStartConverted, courseStartConverted) == registeredCourseStartConverted
                                        && Math.min(registeredCourseStartConverted, courseEndConvereted) == registeredCourseStartConverted) ||
                                     (Math.max(registeredCourseStartConverted, courseStartConverted) == courseStartConverted
                                        && Math.min(courseStartConverted, registeredCourseEndConverted) == courseStartConverted);

                if (overlapped) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean exceedAllowedCredit(int total, int credit) {
        return total + credit > 21;
    }

    public void setSemester(String semester) {this.semester = semester;}
}