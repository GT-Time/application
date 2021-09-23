package com.example.registration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.TimetableView;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        schedules = new ArrayList<Schedule>();
        timeTable = new TimetableView(this.getContext());
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask {
        String address;
        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            try {
                address = "http://ec2-44-197-174-212.compute-1.amazonaws.com/ScheduleList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
                dialog.setMessage("Loading");
                dialog.show();
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

                // TODO : Fix scheduleList.php to return following (updated) variables
                int index = 0;
                String courseInstructor;
                String courseTitle;
                String courseLocation;
                String courseDay;
                String courseTime;
                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    courseInstructor = object.getString("courseInstructor");
                    courseTitle = object.getString("courseTitle");
                    courseLocation = object.getString("courseLocation");
                    courseDay = object.getString("courseDay");
                    courseTime = object.getString("courseTime");

                    for(int i = 0; i < courseDay.length(); i++) schedules.add(new RegistrationSchedule(courseInstructor, courseTitle, courseLocation, courseDay.charAt(i), courseTime));
                    ++index;
                }
                dialog.dismiss();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            timeTable.add(schedules);
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
