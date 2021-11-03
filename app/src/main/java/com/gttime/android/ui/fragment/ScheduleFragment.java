package com.gttime.android.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gttime.android.component.Course;
import com.gttime.android.component.CourseSchedule;
import com.gttime.android.R;
import com.gttime.android.util.json.JsonReader;
import com.gttime.android.util.json.JsonUtil;
import com.gttime.android.util.util.Util;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
    private ArrayList<Schedule> schedules;
    private TimetableView timeTable;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    private ChipGroup chipGroup;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        schedules = new ArrayList<Schedule>();
        timeTable = getView().findViewById(R.id.timetable);

        chipGroup = getView().findViewById(R.id.semesterGroup);

        String[] text = getResources().getStringArray(R.array.semesterText);
        String[] id = getResources().getStringArray(R.array.semesterID);


        int chipLimit = 3;
        int chipNum = Math.min(chipLimit, text.length);
        for (int i = 0; i < chipNum; i++) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip, chipGroup, false);
            chip.setText(text[i]);
            chip.setId(Util.parseInt(id[i]));
            chipGroup.addView(chip);
        }
        chipGroup.setSingleSelection(true);
        chipGroup.check(Util.parseInt(id[0]));
        chipGroup.setSelectionRequired(true);
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                timeTable.removeAll();
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading");
                progressDialog.show();
                new BackgroundTask().execute();
                progressDialog.dismiss();
                progressDialog.hide();
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        new BackgroundTask().execute();
        progressDialog.dismiss();
        progressDialog.hide();

        timeTable.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {

            }
        });
    }


    class BackgroundTask extends AsyncTask {
        String filename;
        @Override
        protected void onPreExecute() {
            try {
                filename = Util.getFileName(String.valueOf(chipGroup.getCheckedChipId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Object[] objects) {
            return JsonUtil.readJson(new File(getActivity().getFilesDir(), filename));
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            List<Course> registeredCourses = new JsonReader().fetchCourse((String) o);
            for(int i = 0; i < registeredCourses.size(); i++) {
                schedules.clear();
                int days = registeredCourses.get(i).getCourseDay().length();

                String courseInstructor = registeredCourses.get(i).getCourseInstructor();
                String courseTitle = registeredCourses.get(i).getCourseTitle();
                String courseLocation = registeredCourses.get(i).getCourseLocation();
                String courseDay = registeredCourses.get(i).getCourseDay();
                String courseTime = registeredCourses.get(i).getCourseTime();
                for(int j = 0; j < days; j++) schedules.add(new CourseSchedule(courseTitle, courseInstructor, courseLocation, courseTime, courseDay.charAt(j)));
                timeTable.add(schedules);
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
