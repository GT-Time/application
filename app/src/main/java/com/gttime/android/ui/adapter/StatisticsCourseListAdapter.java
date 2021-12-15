package com.gttime.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.gttime.android.component.Course;
import com.gttime.android.R;
import com.gttime.android.ui.fragment.StatisticsFragment;
import com.gttime.android.util.IOUtil;
import com.gttime.android.util.IntegerUtil;
import com.gttime.android.util.JSONUtil;

import java.io.File;
import java.util.List;

public class StatisticsCourseListAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courseScheduleList;
    private Fragment parent;
    private String semesterID;

    public StatisticsCourseListAdapter(Context context, List<Course> courseScheduleList, String semesterID, Fragment parent) {
        this.context = context;
        this.courseScheduleList = courseScheduleList;
        this.parent = parent;
        this.semesterID = semesterID;
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

                try
                    {
                        boolean success = JSONUtil.deleteCourse(new File(parent.getActivity().getFilesDir(), IOUtil.getFileName(semesterID)), courseScheduleList.get(position));
                        if(success)
                        {
                            //parent - 자신을 불러낸 course Fragment 에서 알림창을 띄워줌
                            AlertDialog.Builder alert = new AlertDialog.Builder(parent.getActivity());
                            AlertDialog dialog = alert.setMessage("Course has been deleted from schedule")
                                    .setPositiveButton("OK",null)
                                    .create();
                            dialog.show();
                            StatisticsFragment.totalCredit -= IntegerUtil.parseInt(courseScheduleList.get(position).getCourseCredit().split(" ")[0]);
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
                    };
                });
        return v;
    }

    public void setSemester(String semesterID) {this.semesterID = semesterID;}
}

