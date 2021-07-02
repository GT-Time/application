package com.example.registration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    private AutoResizeTextView monday[] = new AutoResizeTextView[22];
    private AutoResizeTextView tuesday[] = new AutoResizeTextView[22];
    private AutoResizeTextView wednesday[] = new AutoResizeTextView[22];
    private AutoResizeTextView thursday[] = new AutoResizeTextView[22];
    private AutoResizeTextView friday[] = new AutoResizeTextView[22];
    private Schedule schedule = new Schedule();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        monday[8] = (AutoResizeTextView)getView().findViewById(R.id.monday8);
        monday[9] = (AutoResizeTextView)getView().findViewById(R.id.monday9);
        monday[10] = (AutoResizeTextView)getView().findViewById(R.id.monday10);
        monday[11] = (AutoResizeTextView)getView().findViewById(R.id.monday11);
        monday[12] = (AutoResizeTextView)getView().findViewById(R.id.monday12);
        monday[13] = (AutoResizeTextView)getView().findViewById(R.id.monday13);
        monday[14] = (AutoResizeTextView)getView().findViewById(R.id.monday14);
        monday[15] = (AutoResizeTextView)getView().findViewById(R.id.monday15);
        monday[16] = (AutoResizeTextView)getView().findViewById(R.id.monday16);
        monday[17] = (AutoResizeTextView)getView().findViewById(R.id.monday17);
        monday[18] = (AutoResizeTextView)getView().findViewById(R.id.monday18);
        monday[19] = (AutoResizeTextView)getView().findViewById(R.id.monday19);
        monday[20] = (AutoResizeTextView)getView().findViewById(R.id.monday20);
        monday[21] = (AutoResizeTextView)getView().findViewById(R.id.monday21);

        tuesday[8] = (AutoResizeTextView)getView().findViewById(R.id.tuesday8);
        tuesday[9] = (AutoResizeTextView)getView().findViewById(R.id.tuesday9);
        tuesday[10] = (AutoResizeTextView)getView().findViewById(R.id.tuesday10);
        tuesday[11] = (AutoResizeTextView)getView().findViewById(R.id.tuesday11);
        tuesday[12] = (AutoResizeTextView)getView().findViewById(R.id.tuesday12);
        tuesday[13] = (AutoResizeTextView)getView().findViewById(R.id.tuesday13);
        tuesday[14] = (AutoResizeTextView)getView().findViewById(R.id.tuesday14);
        tuesday[15] = (AutoResizeTextView)getView().findViewById(R.id.tuesday15);
        tuesday[16] = (AutoResizeTextView)getView().findViewById(R.id.tuesday16);
        tuesday[17] = (AutoResizeTextView)getView().findViewById(R.id.tuesday17);
        tuesday[18] = (AutoResizeTextView)getView().findViewById(R.id.tuesday18);
        tuesday[19] = (AutoResizeTextView)getView().findViewById(R.id.tuesday19);
        tuesday[20] = (AutoResizeTextView)getView().findViewById(R.id.tuesday20);
        tuesday[21] = (AutoResizeTextView)getView().findViewById(R.id.tuesday21);

        wednesday[8] = (AutoResizeTextView)getView().findViewById(R.id.wednesday8);
        wednesday[9] = (AutoResizeTextView)getView().findViewById(R.id.wednesday9);
        wednesday[10] = (AutoResizeTextView)getView().findViewById(R.id.wednesday10);
        wednesday[11] = (AutoResizeTextView)getView().findViewById(R.id.wednesday11);
        wednesday[12] = (AutoResizeTextView)getView().findViewById(R.id.wednesday12);
        wednesday[13] = (AutoResizeTextView)getView().findViewById(R.id.wednesday13);
        wednesday[14] = (AutoResizeTextView)getView().findViewById(R.id.wednesday14);
        wednesday[15] = (AutoResizeTextView)getView().findViewById(R.id.wednesday15);
        wednesday[16] = (AutoResizeTextView)getView().findViewById(R.id.wednesday16);
        wednesday[17] = (AutoResizeTextView)getView().findViewById(R.id.wednesday17);
        wednesday[18] = (AutoResizeTextView)getView().findViewById(R.id.wednesday18);
        wednesday[19] = (AutoResizeTextView)getView().findViewById(R.id.wednesday19);
        wednesday[20] = (AutoResizeTextView)getView().findViewById(R.id.wednesday20);
        wednesday[21] = (AutoResizeTextView)getView().findViewById(R.id.wednesday21);

        thursday[8] = (AutoResizeTextView)getView().findViewById(R.id.thursday8);
        thursday[9] = (AutoResizeTextView)getView().findViewById(R.id.thursday9);
        thursday[10] = (AutoResizeTextView)getView().findViewById(R.id.thursday10);
        thursday[11] = (AutoResizeTextView)getView().findViewById(R.id.thursday11);
        thursday[12] = (AutoResizeTextView)getView().findViewById(R.id.thursday12);
        thursday[13] = (AutoResizeTextView)getView().findViewById(R.id.thursday13);
        thursday[14] = (AutoResizeTextView)getView().findViewById(R.id.thursday14);
        thursday[15] = (AutoResizeTextView)getView().findViewById(R.id.thursday15);
        thursday[16] = (AutoResizeTextView)getView().findViewById(R.id.thursday16);
        thursday[17] = (AutoResizeTextView)getView().findViewById(R.id.thursday17);
        thursday[18] = (AutoResizeTextView)getView().findViewById(R.id.thursday18);
        thursday[19] = (AutoResizeTextView)getView().findViewById(R.id.thursday19);
        thursday[20] = (AutoResizeTextView)getView().findViewById(R.id.thursday20);
        thursday[21] = (AutoResizeTextView)getView().findViewById(R.id.thursday21);

        friday[8] = (AutoResizeTextView)getView().findViewById(R.id.friday8);
        friday[9] = (AutoResizeTextView)getView().findViewById(R.id.friday9);
        friday[10] = (AutoResizeTextView)getView().findViewById(R.id.friday10);
        friday[11] = (AutoResizeTextView)getView().findViewById(R.id.friday11);
        friday[12] = (AutoResizeTextView)getView().findViewById(R.id.friday12);
        friday[13] = (AutoResizeTextView)getView().findViewById(R.id.friday13);
        friday[14] = (AutoResizeTextView)getView().findViewById(R.id.friday14);
        friday[15] = (AutoResizeTextView)getView().findViewById(R.id.friday15);
        friday[16] = (AutoResizeTextView)getView().findViewById(R.id.friday16);
        friday[17] = (AutoResizeTextView)getView().findViewById(R.id.friday17);
        friday[18] = (AutoResizeTextView)getView().findViewById(R.id.friday18);
        friday[19] = (AutoResizeTextView)getView().findViewById(R.id.friday19);
        friday[20] = (AutoResizeTextView)getView().findViewById(R.id.friday20);
        friday[21] = (AutoResizeTextView)getView().findViewById(R.id.friday21);

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask {
        String address;
        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            try {
                address = "http://sch1261315.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
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

                int index = 0;
                String courseProfessor;
                String courseTime;
                String courseNumber;
                String courseTitle;
                String courseLocation;
                while(index < jsonResponse.length()) {
                    JSONObject object = jsonResponse.getJSONObject(index);
                    courseProfessor = object.getString("courseProfessor");
                    courseTitle = object.getString("courseTitle");
                    courseTime = object.getString("courseTime");
                    courseNumber = object.getString("courseNumber");
                    courseLocation = object.getString("courseLocation");
                    schedule.addSchedule(courseTime,courseTitle + "\n" + courseLocation,courseProfessor);
                    ++index;
                }
                dialog.dismiss();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            schedule.setting(monday,tuesday,wednesday,thursday,friday,getContext());
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
